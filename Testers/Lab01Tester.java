package Testers;

import java.util.Scanner;
import Graphs.*;

public class Lab01Tester{

	private Graph graph;
	
	public Lab01Tester(){
		int input = 0;
		String v1;
		Scanner scan = new Scanner(System.in);
		Scanner scanText = new Scanner(System.in);

		do{
			input = 0;
			System.out.print("Implementation\n\t1.Naive\n\t2.Matrix\n\t3.List\nChoose an implementation: ");
			try{
				input = scan.nextInt();
				int size=1;
				if(input==2 || input==3){
					size=0;
					do{		
						size=0;
						System.out.print("Insert graph size: ");
				
						try{
							size = scan.nextInt();
						}catch(Exception e){scan.next();};
					}while(size<1);
				}
	
				switch(input){
					case 1: graph = new UGraphBL();
							break;
					case 2: graph = new UGraphMatrix(size);
							break;
					case 3: graph = new UGraph(size);
							break;
					default: break;
				}
			
			}catch(Exception g){scan.next();};
		}while(input<1 || input>3);

		do{
			input = 0;
			System.out.print("\n\nCHOOSE(-1 to exit)\n\t1.Number of vertices\n\t2.Number of edges\n\t3.Check list of adjacent vertices\n\t4.Adjacency test\n\t5.Check Connectivity\n\t6.Add Vertex\n\t7.Add Edge\n\t8.Remove vertex\n\t9.Remove Edge\nChoice:");
			try{	
				input = scan.nextInt();
			}catch(Exception f){scan.next();};

			switch(input){
				case 1: System.out.println("Number of vertices: "+graph.numberOfVertices());break; //vertexCount
				case 2: System.out.println("Number of edges: "+graph.numberOfEdges()+"\n"); break; //edgeCount
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
				case 5: System.out.print("Enter two vertices separated by comma ',': "); //connectivity test
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
				case 7: System.out.println("\t-Enter vertices as follows: Vertex1,Vertex2 (e.g. S,A)\n\t-You can enter multiple edges. Use space for separating each edge."); //adding edge
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
				default: break;
			}

		}while(input!=-1);
	}

	public static void main(String[] args){
		new Lab01Tester();
	}
}