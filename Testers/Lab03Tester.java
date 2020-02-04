package Testers;

import Graphs.*;
import java.util.Scanner;
import java.util.Arrays;

public class Lab03Tester{

	private UGraph graph;
	
	public Lab03Tester(){
		int input = 0;
		String v1;
		Scanner scan = new Scanner(System.in);
		Scanner scanText = new Scanner(System.in);
		System.out.print("Insert graph size: ");
		do{
			input = 0;
			try{
				input = scan.nextInt();
			}catch(Exception e){scan.next();};
		}while(input<1);
		graph = new UGraph(input);

		do{
			input = 0;
			System.out.print("\n\nCHOOSE(-1 to exit)\n\t1.Number of vertices\n\t2.Number of edges\n\t3.Check list of adjacent vertices\n\t4.Adjacency test\n\t5.Check Connectivity\n\t6.Add Vertex\n\t7.Add Edge\n\t8.Remove vertex\n\t9.Remove Edge\n\t10.DFS\n\t11.BFS\n\t12.SSUPL\nChoice:");
			try{
				input = scan.nextInt();
			}catch(Exception f){scan.next();};

			switch(input){
				case 1: System.out.println("Number of vertices: "+graph.vertices);break; //vertexCount
				case 2: System.out.println("Number of edges: "+graph.edges+"\n"); break; //edgeCount
				case 3: System.out.print("Enter vertex: "); //neghbourlist
						String vertex = scanText.nextLine();
						graph.printNeighbours(vertex);
						break;
				case 4:	System.out.print("Enter two vertices separated by comma ',': "); //adjacency test
						v1 = scanText.nextLine();
						int b = graph.isAdjacent(v1);
						if(b==1)
							System.out.println(v1+" are adjacent");
						else if(b==0)
							System.out.println(v1+" are not adjacent");
						else
							System.out.println("Edge/vertex not valid!");
						break;
				case 5: System.out.print("Enter two vertices separated by comma ',: "); //connectivity test
						v1 = scanText.nextLine();
						if(graph.isConnected(v1))
							System.out.println(v1+" are connected");
						else
							System.out.println(v1+" are not connected");
						break;
				case 6:	System.out.println("You can enter multiple vertices. Use space for separating each vertex."); //adding vertex
						System.out.print("Enter a new name of vertex: ");
						v1 = scanText.nextLine();
						graph.addVertex(v1);
						break;
				case 7: System.out.println("\t-Enter vertices as follows: Vertex1,Vertex2,Weight (e.g. S,A,10)\n\t-Weight is optional\n\t-You can enter multiple edges. Use space for separating each edge."); //adding edge
						System.out.print("Enter a new edge (vertex pair): ");
						v1 = scanText.nextLine();
						graph.addEdge(v1);
						break;
				case 8: System.out.print("Enter vertex name to be removed: "); //removing vertex
						v1 = scanText.nextLine();
						graph.removeVertex(v1);
				 		break;
				case 9: System.out.print("Enter an edge (vertex pair separated by comma ',') to be removed: ");
						v1 = scanText.nextLine();
						graph.removeEdge(v1);
						break;
				case 10:System.out.print("Enter valid starting vertex: "); 
						v1 = scanText.nextLine();
						graph.dfsTraversal(v1);
						break;
				case 11:System.out.print("Enter valid starting vertex: "); 
						v1 = scanText.nextLine(); 
						graph.bfsTraversal(v1);
						break;
				case 12:System.out.print("Enter Inital vertex: "); //follows the sequencing of the input vertices
						v1 = scanText.nextLine();
						System.out.println(Arrays.toString(graph.SSUPL(graph,v1)));
						break;

				default: break;
			}

		}while(input!=-1);
	}

	public static void main(String[] args){
		new Lab03Tester();
	}
}