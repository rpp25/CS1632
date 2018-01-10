//author rpp25
import java.util.Scanner;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class CarTracker{
	public static Scanner input = new Scanner(System.in);
	public static void main(String[] args){
		MyMinPQ cars = new MyMinPQ();
		mainMenu(cars);
		input.close();
	}

	public static void mainMenu(MyMinPQ cars){
		int choice = 0;
		System.out.println("WELCOME!\n"
			+ "1)  Add a car\n"
			+ "2)  Update a car\n"
			+ "3)  Remove a car\n"
			+ "4)  Find the lowest priced car\n"
			+ "5)  Find the car with the lowest mileage\n"
			+ "6)  Find the lowest priced car by make and model\n"
			+ "7)  Find the car with the lowest mileage by make and model\n"
			+ "0)  Exit\n"
			+ "Please enter the number of your selection: ");
		try {
			choice = Integer.parseInt(input.nextLine());
		} catch (IllegalArgumentException iae) {
			System.out.println("Invalid input");
		}
		switch(choice){
			case 0: System.exit(0);
			case 1: addCar(cars);
			case 2: updateCar(cars);
			case 3: removeCar(cars);
			case 4: lowestPrice(cars);
			case 5: lowestMileage(cars);
			case 6: lowestPriceMM(cars);
			case 7: lowestMileageMM(cars);
			default: System.exit(0);
		}
	}
	

	//Guides the user to add a car to the PQ
	public static void addCar(MyMinPQ cars){
		double price = 0;
		double mileage = 0;
		while (true){
			System.out.println("");
			System.out.println("ADD CAR");
			
			System.out.println("Please enter VIN: ");
			String VIN = input.nextLine();
			while (cars.priceHash.containsKey(VIN)) {
				System.out.println("This VIN is in use. Please enter a valid VIN");
				VIN = input.nextLine();
			}
			System.out.println("Please enter make: ");
			String make = input.nextLine();
			System.out.println("Please enter model: ");
			String model = input.nextLine();
			System.out.println("Please enter price: ");
			price = Double.parseDouble(input.nextLine());
			System.out.println("Please enter mileage: ");			
			mileage = Double.parseDouble(input.nextLine());
			System.out.println("Enter color: ");
			String color = input.nextLine();
			Car newCar = new Car(VIN, make, model, color, price, mileage);
			cars.insert(newCar);
			
			System.out.println("Car with VIN " + VIN + " has been added");
			System.out.println("Would you like to add another car? (y/n)");
			String answer = input.nextLine().toLowerCase();
			if (answer.equals("y")) {
				
			}
			else{
				break;
			}
		}
		
		/*Car taurus = new Car("AAAAAAAAAAAAAAAA1", "Ford", "Taurus", "Blue", 2000, 30000);
		Car taurus2 = new Car("AAAAAAAAAAAAAAAA2", "Ford", "Taurus", "Red", 4000, 20000);
		Car honda = new Car("AAAAAAAAAAAAAAAA3", "Honda", "Accord", "Teal", 22000, 15432);
		Car honda2 = new Car("AAAAAAAAAAAAAAAA4", "Honda", "Accord", "Black", 22999, 30000);
		Car audi = new Car("AAAAAAAAAAAAAAAA5", "Audi", "R8", "Black", 2000000, 50);
		Car audi2 = new Car("AAAAAAAAAAAAAAAA6", "Audi", "R8", "Orange", 3100000, 80);
		Car impala = new Car("AAAAAAAAAAAAAAAA7", "Chevy", "Impala", "Yellow", 9600, 20000);
		Car impala2 = new Car("AAAAAAAAAAAAAAAA8", "Chevy", "Impala", "Red", 4800, 40000);
		cars.insert(taurus);
		cars.insert(taurus2);
		cars.insert(impala);
		cars.insert(impala2);
		cars.insert(audi);
		cars.insert(audi2);
		cars.insert(honda);	
		cars.insert(honda2);*/
		mainMenu(cars);
	}
	
	//Guides the user to remove a car from the PQ
	public static void removeCar(MyMinPQ cars){
		System.out.println("");
		System.out.println("REMOVE CAR");
		System.out.println("Enter VIN: ");
		String VIN = input.nextLine();
		Car car = cars.getVIN(VIN);
		if (car == null){
			System.out.println("No cars with VIN: " + VIN);
		}
		else{
			System.out.println("Removing:\n" + car);
			cars.remove(VIN);
		}
		mainMenu(cars);
	}
	
	//Guides the user to update a car in the PQ
	public static void updateCar(MyMinPQ cars){
		System.out.println("");
		System.out.println("UPDATE CAR");
		System.out.println("Please enter VIN of the car to update: ");
		String VIN = input.nextLine().toUpperCase();
		updateCarOptions(VIN, cars);
	}
	
	//Guides the user to choose an attribute of a car to update
	public static void updateCarOptions(String VIN, MyMinPQ cars){
		int choice = 0;
		Car current = cars.getVIN(VIN);
		if(current == null) { 
			System.out.println("No cars with VIN " + VIN + "\n");
			mainMenu(cars);
		}
		else{
			System.out.println(current);
			System.out.println("0)  Main Menu\n"
				+ "1)  Update price\n"
				+ "2)  Update mileage\n"
				+ "3)  Update color\n"
				+ "Please enter: ");	
			try{
				choice = Integer.parseInt(input.nextLine());
			} catch (IllegalArgumentException iae) {
				System.out.println("Invalid choice");
			}
			update(choice, current, cars);
		}
	}
	
	//Takes user input and allows the user to update that attribute
	public static void update(int choice, Car car, MyMinPQ cars){
		if (choice == 0){
			mainMenu(cars);
		}
		else if (choice == 1) {
			NumberFormat dollars = new DecimalFormat("#0.00");
			System.out.println("VIN: " + car.VIN + "Current Price: $" + dollars.format(car.price));
			System.out.println("Please enter new price: ");
			double newPrice = 0;
			try{
				newPrice = Double.parseDouble(input.nextLine());
			} catch (IllegalArgumentException iae) {
				newPrice = car.price;
			}
			cars.updatePrice(car.VIN, newPrice);
		}
		
		else if (choice == 2) {
			System.out.println("VIN: " + car.VIN + "Current Mileage: " + car.mileage);
			System.out.println("Please enter new mileage: ");
			double newMileage = 0;
			try{
				newMileage = Double.parseDouble(input.nextLine());
			} catch (IllegalArgumentException iae) {
				newMileage = car.mileage;
			}
			cars.updateMileage(car.VIN, newMileage);
		}
		
		else if (choice == 3) {
			System.out.println("VIN: " + car.VIN + "Current Color: " + car.color);
			System.out.println("Please enter new color: ");
			String newColor = input.nextLine();
			cars.updateColor(car.VIN, newColor);
		}
		
		else{
			mainMenu(cars);
		}
		
		updateCarOptions(car.VIN, cars);
	}
	
	//Displays the lowest priced car
	public static void lowestPrice(MyMinPQ cars){
		System.out.println("");
		System.out.println("LOWEST PRICED CAR");
		Car car = cars.getLowestPrice();
		if (car == null){
			System.out.println("There are no cars in the system.\n");
		}
		else{
			System.out.println(car);
		}
		mainMenu(cars);
	}
	
	//Displays the car with the lowest mileage
	public static void lowestMileage(MyMinPQ cars){
		System.out.println("");
		System.out.println("LOWEST MILEAGE CAR");
		Car car = cars.getLowestMileage();
		if (car == null){
			System.out.println("There are no cars in the system.");
		}
		else{
			System.out.println(car);
		}
		mainMenu(cars);
	}
	
	//Displays the lowest priced car by make and model
	public static void lowestPriceMM(MyMinPQ cars){
		System.out.println("");
		System.out.println("LOWEST PRICE BY MAKE AND MODEL");
		while(true){
			System.out.println("Please enter the make: ");
			String make = input.nextLine();
			System.out.println("Please enter the model: ");
			String model = input.nextLine();
			Car car = cars.getLowestPriceMM(cars.pricePQ, make, model);
			if (car == null){
				System.out.println("No " + make + " " + model + " cars found.");
			}
			else{
				System.out.println(car);
			}
			System.out.println();
			System.out.println("Search by another make and model? (y/n) ");
			String answer = input.nextLine().toLowerCase();
			if (answer.equals("y")) {
				
			}
			else{
				break;
			}
		}
		mainMenu(cars);
	}
	
	//Displays the car with the lowest mileage by make and model
	public static void lowestMileageMM(MyMinPQ cars){
		System.out.println("");
		System.out.println("LOWEST MILEAGE BY MAKE AND MODEL");
		while(true){
			System.out.println("Please enter the make: ");
			String make = input.nextLine();
			System.out.println("Please enter the model: ");
			String model = input.nextLine();
			Car car = cars.getLowestMileageMM(cars.mileagePQ, make, model);
			if (car == null){
				System.out.println("No " + make + " " + model + " cars found.");
			}
			else{
				System.out.println(car);
			}
			System.out.println();
			System.out.println("Search by another make and model? (y/n) ");
			String answer = input.nextLine().toLowerCase();
			if (answer.equals("y")) {
				
			}
			else{
				break;
			}
		}
		mainMenu(cars);
	}
}