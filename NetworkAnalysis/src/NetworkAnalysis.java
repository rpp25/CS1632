import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NetworkAnalysis {

	public static void main(String[] args) throws IOException {

		Scanner input = new Scanner(System.in);									//Scanner for user input
		Scanner info;															//Scanner for file info
		
		if (args[0] == null){
			System.out.println("Enter a proper file");
			System.exit(0);
		}

		File file= new File(args[0]);

		if(!file.exists())
		{
			System.out.println("INPUT ERROR: File name entered does not exist. Please enter a file that does exists:   ");
			System.exit(0);
		}

		info = new Scanner (file);
		int size = Integer.parseInt(info.nextLine()); //number of vertices
		String[] vertices = new String[size];

		AdjacencyList[] adjListArray = new AdjacencyList[size];
		for (int i = 0; i < size; i++){
			adjListArray[i] = new AdjacencyList();
			vertices[i] = Integer.toString(i);
		}
		EdgeWeightedGraph latGraph = new EdgeWeightedGraph(size);			
		Edge graphEdge;	
		EdgeWeightedDigraph latencyDigraph = new EdgeWeightedDigraph(size);	
		EdgeWeightedDigraph bwDigraph = new EdgeWeightedDigraph(size);	
		EdgeWeightedDigraph lengthDigraph = new EdgeWeightedDigraph(size);	
		FlowNetwork bwNetwork = new FlowNetwork(size);
		FlowEdge fe;
		DirectedEdge digraphEdge;
		Graph DFSgraph = new Graph(size);									
		

		String words;
		String[] line;
		while (info.hasNext()){
			words=info.nextLine();
			if (words.length()>0){
			line= (words).split(" ");
			int v1 = Integer.parseInt(line[0]);
			int v2 = Integer.parseInt(line[1]);
			String material = line[2];
			int bandwidth = Integer.parseInt(line[3]);
			int length = Integer.parseInt(line[4]);
			double latency = ((double)length / (double) 230000000) * 1000000000;
			adjListArray[v1].add(v2, 		// Add vertex to the list
						material,			// Add material
						bandwidth,       	// Add bandwidth
						length,				// Add length
						latency);			// Add latency
			adjListArray[v2].add(v1,
						material,
						bandwidth,     
						length,
						latency);		

			graphEdge = new Edge(v1,v2, latency);
			latGraph.addEdge(graphEdge);
			
			digraphEdge = new DirectedEdge(v1,v2, latency);
			latencyDigraph.addEdge(digraphEdge);
			digraphEdge = new DirectedEdge(v2,v1, latency);
			latencyDigraph.addEdge(digraphEdge);
			
			digraphEdge = new DirectedEdge(v1,v2, (double) bandwidth);
			bwDigraph.addEdge(digraphEdge);
			digraphEdge = new DirectedEdge(v2,v1, (double) bandwidth);
			bwDigraph.addEdge(digraphEdge);
			
			fe = new FlowEdge(v1, v2, (double) bandwidth);
			bwNetwork.addEdge(fe);
			fe = new FlowEdge(v2, v1, (double) bandwidth);
			bwNetwork.addEdge(fe);
			
			
			digraphEdge = new DirectedEdge(v1,v2, length);
			lengthDigraph.addEdge(digraphEdge);
			digraphEdge = new DirectedEdge(v2,v1, length);
			lengthDigraph.addEdge(digraphEdge);
			
			
			DFSgraph.addEdge(v1,v2);
			DFSgraph.addEdge(v2,v1);
			}

		}
		DepthFirstPaths DFSinitial = new DepthFirstPaths(DFSgraph, 0);
		System.out.println("Vertices: " + DFSinitial.getCounter());
		int choice = 0;
		
		
		while (choice!=6){
			Menu();
			choice=input.nextInt();
			if (choice==1){
				System.out.println("Lowest Average Latency Spanning Tree:");
				double total = 0;
				double numEdges = 0;
				LazyPrimMST mst = new LazyPrimMST(latGraph);
			
				for(Edge e : mst.edges())
				{	
					System.out.println(vertices[e.either()] + " to " + vertices[e.other(e.either())] + " : Latency = " + e.weight());
					total += e.weight();
					numEdges++;
					
				}
				total = total/numEdges;
				System.out.println("Average latency of this path is: " + total);
			}
			if (choice==2){  //find lowest latency path between any two vertices

				input.nextLine(); 		// Consume new line character
				
				System.out.println("Please enter a vertex:  ");
				String vs1 = input.nextLine();
				int v1 = Integer.parseInt(vs1);
				int startIndex = Arrays.asList(vertices).indexOf(vs1);
				while (startIndex < 0)
				{
					System.out.println("The vertex you entered does not exist in the data. Try again.");
					vs1 = input.nextLine();
					startIndex = Arrays.asList(vertices).indexOf(vs1);
				}
				
				System.out.println("Please enter another vertex:  ");
				String vs2 = input.nextLine();
				int v2 = Integer.parseInt(vs2);
				int endIndex = Arrays.asList(vertices).indexOf(vs2);
				while(endIndex < 0)
				{
					System.out.println("The vertex you entered does not exist in the data. Try again.");
					vs2 = input.nextLine();
					endIndex = Arrays.asList(vertices).indexOf(vs2);
				}
				
				
				DijkstraSP MSPLowestLatency = new DijkstraSP(latencyDigraph, startIndex);
				if (MSPLowestLatency.hasPathTo(endIndex)) 
				{

					System.out.println("Lowest latency from " + v1+ " to " + v2 + " is " + MSPLowestLatency.distTo(endIndex));
					System.out.println("Path with edges (in reverse order):");
					
					for (DirectedEdge e : MSPLowestLatency.pathTo(endIndex)) 
	                {
						System.out.println(vertices[e.to()] + " to " + vertices[e.from()] + " latency: " + e.weight() + "   ");
	                }
					System.out.println();
	            }
	            else 
	            {
	            	System.out.println("There is no path from " + v1 + " to " + v2);
	            }
				
			}

			if (choice==3){
				input.nextLine(); 		// Max Bandwidth
				
				System.out.println("Please enter a vertex:  ");
				String vs1 = input.nextLine();
				int v1 = Integer.parseInt(vs1);
				int startIndex = Arrays.asList(vertices).indexOf(vs1);
				while (startIndex < 0)
				{
					System.out.println("The vertex you entered does not exist in the data. Try again.");
					vs1 = input.nextLine();
					startIndex = Arrays.asList(vertices).indexOf(vs1);
				}
				
				System.out.println("Please enter another vertex:  ");
				String vs2 = input.nextLine();
				int v2 = Integer.parseInt(vs2);
				int endIndex = Arrays.asList(vertices).indexOf(vs2);
				while(endIndex < 0)
				{
					System.out.println("The vertex you entered does not exist in the data. Try again.");
					vs2 = input.nextLine();
					endIndex = Arrays.asList(vertices).indexOf(vs2);
				}

				FordFulkerson ff = new FordFulkerson(bwNetwork, startIndex, endIndex);
				System.out.println("The maximum amount of data that can be sent across this path is: " + ff.value());

			}

			if (choice==4){ //copper only
				Graph temp = new Graph(DFSgraph.V());
				
				info = new Scanner(file);
				info.nextLine();
				int v1 = 0;
				int v2 = 0;
				int initial = 0;
				while (info.hasNext()){
					words=info.nextLine();
					if (words.length()>0){
					
						line= (words).split(" ");
						v1 = Integer.parseInt(line[0]);
						v2 = Integer.parseInt(line[1]);
						String material = line[2];
						if (!material.equalsIgnoreCase("optical")){
							temp.addEdge(v1,v2);
							temp.addEdge(v2,v1);
							initial = v2;
						}
					}
				}
				
				DepthFirstPaths DFScheck = new DepthFirstPaths(temp, initial);
				System.out.println("Number of vertices found in new graph: " + DFScheck.getCounter());
				System.out.println("Vertices that should be in the graph: " + temp.V());
				if (DFScheck.getCounter() == temp.V()){
					System.out.println("This graph is intact and copper connected only.");
				}
				else{
					System.out.println("This graph is not intact");
				}
			}
			
	
			if (choice==5){

				input.nextLine(); 		// Consume new line character
				
				for (int x = 0; x < (DFSgraph.V() - 1); x++){
					for(int y = x + 1; y < DFSgraph.V(); y++){
						Graph temp = new Graph(DFSgraph.V());
						
						info = new Scanner(file);
						info.nextLine();
						int v1 = 0;
						int v2 = 0;
						for(int i = 0; i < size; i = i + 1)
						{
							adjListArray[i] = new AdjacencyList();
							vertices[i] = Integer.toString(i);
						}
						while (info.hasNext()){
							words=info.nextLine();
							if (words.length()>0){
							
								line= (words).split(" ");
								v1 = Integer.parseInt(line[0]);
								v2 = Integer.parseInt(line[1]);
								if (v1 != x && v2 != x && v1 != y && v2 != y){
									temp.addEdge(v1,v2);
									temp.addEdge(v2,v1);
								}
							}
						}
						int numVertices = 0;
						if(x == 0){
							if (x == 1 || y == 1){
								numVertices = 2;
							}
							else numVertices = 1;								
						}
						
						DepthFirstPaths DFScheck = new DepthFirstPaths(temp, numVertices);
						//graph can only be intact if the vertices remove were at the end of the graph
						//so the total number of vertices should be 2 less than before
						int intact = DFSinitial.getCounter() - 2; 
						if (DFScheck.getCounter() == intact){
							System.out.println("Remove " + x + " and " + y + ": Graph is intact");
						}
						else {
							System.out.println("Remove " + x + " and " + y + ": Graph is broken");
						}
					}
				}
				

				info.close();
			}
			if (choice == 6){
				input.close();
				info.close();
				System.exit(0);
			}
		}
	}

	
	public static void Menu(){
		System.out.println("\nSelect an option: ");
		//System.out.println("\t0) Display edges");
		System.out.println("1) Display lowest average latency spanning tree");
		System.out.println("2) Display lowest latency path between 2 vertices");
		System.out.println("3) Display max bandwidth between any two vertices");
		System.out.println("4) Check if copper-only connected is still one graph");
		System.out.println("5) See if graph is intact after removing an edge");
		System.out.println("6) Save and Quit");
	} 
}