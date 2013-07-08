
import java.text.*;
import java.util.*;
import java.io.*;

public class VendingMachine {

	private ArrayList<Dispenser> dispensers;
	private double moneyInput;
	private String name;
	private String startTime;
	private String invFile;
	private String salesFile;
	private boolean on;
	private int selection;	//selection determines dispenser chosen
	private Scanner fInput;
	private PrintWriter fOutput;
	
	//constructor
	public VendingMachine(String name,  String path){
		on = false;
		this.name = name;
		DateFormat df = new SimpleDateFormat("MM_dd_yy_hh_mm_ss");
		this.startTime = df.format(new Date());
		
		invFile = path + name + "Inventory.txt";
		
		//The name of the sales file will be named by using current date and time and including machine name
		salesFile = path + name + "Sold" + startTime + ".txt";
		
		turnOn();
	}
	
	//setters/getters
	public ArrayList<Dispenser> getDispensers() {
		return dispensers;
	}

	public void setDispensers(ArrayList<Dispenser> dispensers) {
		this.dispensers = dispensers;
	}

	public double getMoneyInput() {
		return moneyInput;
	}

	public void setMoneyInput(double moneyInput) {
		this.moneyInput = moneyInput;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getInvFile() {
		return invFile;
	}

	public void setInvFile(String invFile) {
		this.invFile = invFile;
	}

	public String getSalesFile() {
		return salesFile;
	}

	public void setSalesFile(String salesFile) {
		this.salesFile = salesFile;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}
	
	public boolean isOn(){
		return on;
	}

	
	public void createInventory(String fname){
		if (on){
			//attempt to load from file
			File f =  new File(fname);
			dispensers =  new ArrayList<Dispenser>();
				//attempt to read in the file
			try {
				fInput = new Scanner(f);
				while(fInput.hasNextLine()){
					String s = fInput.nextLine();
					String[] strings = s.split("\\t");
					//System.out.println(strings[0] + "\t" + strings[1] + "\t" + strings[2] + "\t" + strings[3]);
					dispensers.add(new Dispenser(new FoodInformation(strings[0], Double.parseDouble(strings[1])
												, Integer.parseInt(strings[2])), Integer.parseInt(strings[3])));
					
				}
				fInput.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void displayInventory(){
		if (on){
			printMessage(name + " Inventory:");
			//loop through dispensers, show cost, amount remaining and foodinfo
			for(Dispenser d : dispensers){
				//display dispenser
				printMessage(d.toString());
			}
		}
	}
	
	public void saveInventory(String fname){
		if (on){
			//write inventory to file
			File f = new File(fname);
			if (!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				fOutput = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//print to file the name, cost, calories, and quantity separated by tabs, 1 dispenser per line
			for(Dispenser d : dispensers){
				fOutput.println(d.getFoodInfo().getName() + "\t" + d.getFoodInfo().getCost() + "\t" 
								+ d.getFoodInfo().getCalories() + "\t" + d.getInventory() + "\t");
			}
			fOutput.close();
		}
	}
	
	public void saveSales(String fname){
		if (on){
			//write sales to file
			File f = new File(fname);
			if (!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				fOutput = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//calculate total sales
			double total = 0;
			for (Dispenser d : dispensers){
				total += d.getSold() * d.getFoodInfo().getCost();
			}
			//print date to file
			fOutput.println("Date: " + startTime);
			//print total to file
			fOutput.println("Total sales: $" + total);
			//print to file the name, number sold, and sales subtotal separated by tabs, 1 dispenser per line
			fOutput.println("Name\tSold\tSubtotal");
			for (Dispenser d : dispensers){
				fOutput.println(d.getFoodInfo().getName() + "\t" + d.getSold() + "\t" + d.getSold() * d.getFoodInfo().getCost() + "\t");
			}
			fOutput.close();
		}
	}
	
	public void printReceipt(Dispenser d){
		if (on){
			//Display or Print a receipt after each purchase including name and price of the purchased item, entered money amount and change
			printMessage("Item: " + d.getFoodInfo().getName() +
						"\nEntered Amount: " + moneyInput +
						"\nChange: " + (moneyInput - d.getFoodInfo().getCost()));
		}
	}

	public double attemptTransaction(int sel){
		if (on){
			Dispenser d = dispensers.get(sel);
			//attempts a purchase, handles exceptions
			//A client must first enter enough money to make a purchase 
			//If the client's choice has run out or the entered money is not enough, the purchase won't continue
			try {
				if (d.getFoodInfo().getCost() > moneyInput)
					throw new NotEnoughMoneyException();
				if (d.getInventory() <= 0)
					throw new OutOfSelectionException();
			}
			catch (NotEnoughMoneyException e){
				printMessage("Not enought money for selection.");
				printMessage("Please input more money or push coin return.");
				return 0;
			}
			catch (OutOfSelectionException e){
				printMessage("Out of selection.");
				printMessage("Please make new selection or push coin return.");
				return 0;
			}
			printReceipt(d);
			moneyInput -= d.getFoodInfo().getCost();
			d.setInventory(d.getInventory() - 1);
			d.setSold(d.getSold() + 1);
			printMessage("Enjoy!");
			return returnMoney();
		}
		return -1;
	}
	
	public double returnMoney(){
		if (on){
			//set moneyInput to 0 and return money
			double change = moneyInput;
			moneyInput = 0;
			return change;
		}
		return -1;
	}
	
	public void printMessage(String s){
		if (on){
			//displays a message to the user, useful later if gui is implemented
			System.out.println(s);
		}
	}
	
	public void turnOn(){
		if (!on){
			//attempt to initialize with data from a file
			on = true;
			//Read the inventory from the file when the machine is on 
			createInventory(invFile);
			printMessage(name +  "turned on.");
		}
	}
	
	public void turnOff(){
		if (on){
			returnMoney();
			//Write the inventory to file only when the machine turns off
			saveInventory(invFile);
			//Write the sales total (for all items), subtotal and quantity of each item to file only when the machine turns off
			saveSales(salesFile);
			printMessage(name + " turned off.");
			on = false;
		}
	}
}

/*TODO:
	gui
	main
*/