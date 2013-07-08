
public class FoodInformation {
	


	private String name;
	private double cost;
	private int calories;
	
	//constructor
	public FoodInformation(String name, double cost, int calories){
		this.name = name;
		this.cost = cost;
		this.calories =  calories;
	}
	
	//setters/getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}
}
