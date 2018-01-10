public class Node{
	public Node next;
	public int vertex;         // Points to next node in list
	public int length;		    // Holds an integer representation of the name of the city
	public String material;		// Holds the distance to get to this city
	public int bandwidth;		// megabits per second
	public double latency;     //bandwidth * length
			
	// Constructor
	public Node()				
	{
		vertex = 0;
		length = 0;
		material = "";
		bandwidth = 0;
		latency = 0;
	}
	
	public Node(int vertex, String material, int bandwith, int length, double latency){
		this.vertex = vertex;
		this.material = material;
		this.bandwidth = bandwith;
		this.length = length;
		this.latency = latency;
	}
}
