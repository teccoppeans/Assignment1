
public class Dispenser {
		
	private FoodInformation foodInfo;
	private int inventory;
	private int sold;

	//constructor
	public Dispenser(FoodInformation fInfo, int inv){
		this.foodInfo = fInfo;
		this.inventory = inv;
	}
	//setters/getters
	public FoodInformation getFoodInfo() {
		return foodInfo;
	}

	public void setFoodInfo(FoodInformation foodInfo) {
		this.foodInfo = foodInfo;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	public int getSold() {
		return sold;
	}
	
	public void setSold(int sold) {
		this.sold = sold;
	}
	
	public String toString(){
	//modified toString() for use with displayInventory
		return "Name: " + foodInfo.getName() + " Cost: " + foodInfo.getCost() +  
				" Calories: " + foodInfo.getCalories() + " Quantity: " + inventory;
	}
	
	public Object[] toTableFormat(){
		//returns an array containing the name, calories, and cost
		Object[] data =  new Object[3];
		data[0] = foodInfo.getName();
		data[1] = foodInfo.getCalories();
		data[2] = foodInfo.getCost();
		return data;
	}

}
