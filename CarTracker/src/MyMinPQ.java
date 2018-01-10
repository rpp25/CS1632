import java.util.HashMap;
import java.util.Arrays;

public class MyMinPQ{
	public static HashMap<String, Integer> priceHash;
	public static HashMap<String, Integer> mileageHash;
	public static Car[] pricePQ;
	public static Car[] mileagePQ;
	public static int defaultSize = 16; //default size
	public static int size;	

	public MyMinPQ(){
		priceHash = new HashMap<String, Integer>(defaultSize);
		mileageHash = new HashMap<String, Integer>(defaultSize);
		pricePQ = new Car[defaultSize];
		mileagePQ = new Car[defaultSize];
		size = 0;		
	}
	

	 //Inserts a new Car object into the PQ

	public void insert(Car car){
		pricePQ[size] = car;
		mileagePQ[size] = car;
		priceHash.put(car.VIN, size);
		mileageHash.put(car.VIN, size);
		priceSwim(size); 
		mileageSwim(size++);
		if(arrayIsFull()){
			resize();
		}
	}
	
	// Removes a specific Car from the PQ based on VIN
	public void remove(String VIN){
		boolean contains = priceHash.containsKey(VIN);
		if(contains) {
			//remove from mileage priority queue and HashMap
			int index = priceHash.get(VIN).intValue();
			size--;
			exch(index, size , pricePQ, priceHash);
			pricePQ[size] = null;
			priceHash.remove(VIN);
			
			//reposition Car that was swapped
			priceSwim(index);
			priceSink(index);
			
			//remove from mileage priority queue and HashMap
			index = mileageHash.get(VIN).intValue();
			exch(index, size, mileagePQ, mileageHash);
			mileagePQ[size] = null;
			mileageHash.remove(VIN);
			
			//reposition Car that was swapped
			mileageSwim(index);
			mileageSink(index);
		}
	}
	
	//Returns the Car object with the lowest price
	public Car getLowestPrice(){
		return pricePQ[0];
	}
	
	//Returns the Car object with the minimum mileage
	public Car getLowestMileage(){
		return mileagePQ[0];
	}	
	
	//Returns the Car object with the lowest price by make and model
	public Car getLowestPriceMM(Car[] carPQ, String make, String model){
		return searchPriceMM(carPQ, 0, make, model);
	}
	
	//Returns the Car object with the minimum mileage by make and model
	public Car getLowestMileageMM(Car[] carPQ, String make, String model){
		return searchMileageMM(carPQ, 0, make, model);
	}
	
	//Search for the lowest priced car by make and model
	public static Car searchPriceMM(Car[] carPQ, int parent, String make, String model){
		if((parent < size) && carPQ[parent].make != null && carPQ[parent].model != null){
			//System.out.println(parent + " " + carPQ[parent].make + " " + carPQ[parent].model);
	        /*if (make.equals(carPQ[parent].make) && model.equals(carPQ[parent].model)){
				System.out.println("FOUND");
				
			}*/
		}
		if(parent >= size){
			//System.out.println("BLAH " + parent);
			return null;
		}
		if(carPQ[parent] == null){
			//System.out.println("BLEH");
			return null;
		}
		
		else if(make.equals(carPQ[parent].make) && model.equals(carPQ[parent].model)){
			return carPQ[parent];
		}
		else {
			Car left = searchPriceMM(carPQ, (parent*2) + 1, make, model);
			Car right = searchPriceMM(carPQ, (parent*2) + 2, make, model);
			if (left == null && right == null){
				return null;
			}
			else if (right == null){
				return left;
			}
			else if (left == null){
				return right;
			}
			else{
				if(left.price < right.price){
					return left;
				}
				else{
					return right;
				}
			}
		}
	}
	
	//Searches for the lowest mileage car by make and model
	public static Car searchMileageMM(Car[] carPQ, int parent, String make, String model){
		
		if((parent < size) && carPQ[parent].make != null && carPQ[parent].model != null){
			//System.out.println(parent + " " + carPQ[parent].make + " " + carPQ[parent].model);
			/*if (make.equals(carPQ[parent].make) && model.equals(carPQ[parent].model)){
				System.out.println("FOUND");
			}*/
		}
		if(parent >= size){
			//System.out.println("BLAH " + parent);
			return null;
		}
		if(carPQ[parent] == null){
			//System.out.println("BLEH");
			return null;
		}
		
		else if(make.equals(carPQ[parent].make) && model.equals(carPQ[parent].model)){
			return carPQ[parent];
		}
		else {
			Car left = searchMileageMM(carPQ, (parent*2) + 1, make, model);
			Car right = searchMileageMM(carPQ, (parent*2) + 2, make, model);
			if (left == null && right == null){
				return null;
			}
			else if (right == null){
				return left;
			}
			else if (left == null){
				return right;
			}
			else{
				if(left.mileage < right.mileage){
					return left;
				}
				else{
					return right;
				}
			}
		}
	}
	
	// Exchanges the object between two indexes
	public static void exch(int i, int j, Car[] heap, HashMap<String, Integer> index){
		Car temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
		index.replace(heap[i].VIN, Integer.valueOf(i));
		index.replace(heap[j].VIN, Integer.valueOf(j));
	}
	
	// Brings a Car object up the heap based on price
	public static void priceSwim(int index){
		while(index > 0 && lowerPrice(index, (index - 1)/2)){
			exch(index, (index - 1)/2, pricePQ, priceHash);
			index = (index - 1)/2;
		}
	}
	
	//Brings a Car object up the heap based on mileage
	public static void mileageSwim(int index){
		while(index > 0 && lowerMileage(index, (index - 1)/2)){
			exch(index, (index - 1)/2, mileagePQ, mileageHash);
			index = (index - 1)/2;
		}
	}
	
	//Brings a Car object down the heap based on price
	public static void priceSink(int index){
		while((index*2) + 1 < size){			
			int child = (index*2) + 1;
			if (child < size && lowerPrice(child + 1, child)) child++;
			if (!lowerPrice(child, index)) break;
			exch(index, child, pricePQ, priceHash);
			index = child;
		}
	}
	
	//Brings a Car object down the heap based on mileage
	public static void mileageSink(int index){
		while((index*2) + 1 < size){			
			int child = (index*2) + 1;
			if (child < size && lowerMileage(child + 1, child)) child++;
			if (!lowerMileage(child, index)) break;
			exch(index, child, mileagePQ, mileageHash);
			index = child;
		}
	}
	
	//Checks if a Car at one index has a lower price than that of another
	public static boolean lowerPrice(int i, int j){
		if(pricePQ[i] == null || pricePQ[j] == null) return false;
		return pricePQ[i].price < pricePQ[j].price;
	}
	
	//Checks if a Car at one index has a lower mileage than that of another
	public static boolean lowerMileage(int i, int j){
		if(mileagePQ[i] == null || mileagePQ[j] == null) return false;
		return mileagePQ[i].mileage < mileagePQ[j].mileage;
	}
	
	//gets Car based on VIN
	public Car getVIN(String VIN){
		Integer intIndex = priceHash.get(VIN);
		int index = -1;
		if (intIndex != null){
			index = intIndex.intValue();
		}
		if(index != -1){
			return pricePQ[index];
		}
		else return null;
	}
	
	//update price of the Car in respective priority queue
	public void updatePrice(String VIN, double price){
		Integer intIndex = priceHash.get(VIN);
		int index = -1;
		if (intIndex != null){
			index = intIndex.intValue();
		}
		if(index != -1){
			pricePQ[index].setPrice(price);
			priceSwim(index);
			priceSink(index);
		}
	}
	
	//update mileage of the Car in respective priority queue
	public void updateMileage(String VIN, double mileage){
		Integer intIndex = mileageHash.get(VIN);
		int index = -1;
		if (intIndex != null){
			index = intIndex.intValue();
		}
		if(index != -1){
			mileagePQ[index].setMileage(mileage);
			mileageSwim(index);
			mileageSink(index);
		}
	}
	
	//Updates the color of a car
	public void updateColor(String VIN, String color){
		Integer intIndex = priceHash.get(VIN);
		int index = -1;
		if (intIndex != null){
			index = intIndex.intValue();
		}
		if(index != -1){
			pricePQ[index].setColor(color);
		}
	}
	
	//Doubles the available space of the heap
	public static void resize(){
		pricePQ = Arrays.copyOf(pricePQ, pricePQ.length*2);
		mileagePQ = Arrays.copyOf(mileagePQ, mileagePQ.length*2);
	}
	
	//Check if the heap is full
	public static boolean arrayIsFull(){
		return size == pricePQ.length;
	}

}