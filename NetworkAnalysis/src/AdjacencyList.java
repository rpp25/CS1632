public class AdjacencyList {
	Node root;  		// A pointer to the root Node in the list
	
	public AdjacencyList()
	{
		root = new Node();
	}
	
	public void add(int vertex, String material, int bandwith, int length, double latency)
	{
		Node temp = new Node(vertex, material, bandwith, length, latency);
		temp.next = root;
		root = temp;
		
	}	
	
	public Node getRoot()
	{
		return root;
	}
}
