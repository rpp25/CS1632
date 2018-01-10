import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Car {
	String VIN, make, model, color;
	double price, mileage;
	
	public Car(String vin, String make, String model, String color, double price, double mileage){
		this.VIN = vin;
		this.make = make;
		this.model = model;
		this.color = color;
		this.price = price;
		this.mileage = mileage;
	}
	
	public void setMake(String make){
		this.make = make;
	}

	public void setModel(String model){
		this.model = model;
	}

	public void setPrice(double price){
		if(price < 0){
			this.price = 0;
		}
		else{
			this.price = price;
		}
	}

	public void setMileage(double mileage){
		if (mileage < 0){
			this.mileage = 0;
		}
		else{
			this.mileage = mileage;
		}
	}
	
	public void setColor(String color){
		this.color = color;
	}
	
	public String toString(){
		NumberFormat dollarFormat = new DecimalFormat("#0.00");
		StringBuilder sb = new StringBuilder();
		sb.append("VIN: " + VIN + "\n");
		sb.append("Make: " + make + "\n");
		sb.append("Model: " + model + "\n");
		sb.append("Color: " + color + "\n");
		sb.append("Mileage: " + mileage + "\n");
		sb.append("Price: $" + dollarFormat.format(price));
		return sb.toString();
	}
}
