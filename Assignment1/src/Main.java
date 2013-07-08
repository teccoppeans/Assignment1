
public class Main {
	//path for sales log and inventory log files
	final static String path = "src/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		VendingMachine vMachine1 = new VendingMachine("vMachine1", path);
		VendingMachine vMachine2 = new VendingMachine("vMachine2", path);
		//generate random time to shut off by getting the current time and adding 5 - 10 seconds to it
		//Use a random number generator to determine when the machines will be turned off
		long shutoff1 = System.currentTimeMillis() + (int)(Math.random() * 5000 + 5000);	//shut off 5-10 seconds later
		long shutoff2 = System.currentTimeMillis() + (int)(Math.random() * 5000 + 7000);	//shut off 7-12 seconds later
		
		int clients = 0;
		
		while (vMachine1.isOn() || vMachine2.isOn()){
			
			//1 in 3 chance to generate 1 - 3 clients (Use a random number generator to determine client arrival)
			clients = ((int)(Math.random() * 3) > 1) ? (int)(Math.random() * 3 + 1) : 0;
			
			if (vMachine1.isOn())
				vMachine1.displayInventory();
			if (vMachine2.isOn())
				vMachine2.displayInventory();
			
			for (int i = 0; i < clients; ++i){
				//Use a random number generator to determine which vending machine the client is using
				VendingMachine chosen = ((int)(Math.random() * 2) > 0) ? vMachine1 : vMachine2;
				//input a random amount of money between $0.00 and $1.95
				chosen.setMoneyInput((int)(Math.random() * 2) + 0.05 * (int)(Math.random() * 20));
				//choose a random item
				chosen.attemptTransaction((int)(Math.random() * 10));
				try {
					//each customer takes 1 - 3 seconds
					Thread.sleep(1000 * (int)(Math.random() * 3 + 1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (System.currentTimeMillis() > shutoff1)
				vMachine1.turnOff();
			if (System.currentTimeMillis() > shutoff2)
				vMachine2.turnOff();
		}
	}
}

