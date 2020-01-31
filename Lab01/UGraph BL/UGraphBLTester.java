import java.util.Scanner;

public class UGraphBLTester{

	private UGraphBL graph;
	
	public UGraphBLTester(){
		int input = 0;
		String v1, v2;
		Scanner scan = new Scanner(System.in);
		Scanner scanText = new Scanner(System.in);
		graph = new UGraphBL();

		do{
			System.out.print("\n\nCHoose\n\t1.Number of vertices\n\t2.Check list of adjacent vertices\n\t3.Adjacency test\n\t4.Number of edges\n\t5.Add Vertex\n\t6.Add Edge\n\t7.Remove vertex\n\t8.Remove Edge\n\t9.Check Connectivity\nChoice:");
			input = scan.nextInt();

			switch(input){
				case 1: System.out.println("Number of vertices: "+graph.vertexCount()+"\n");break;
				case 2: System.out.print("Enter vertex: ");
						String vertex = scanText.nextLine();
						graph.listAdjacentVerticesTo(vertex);
						break;
				case 3: System.out.println("Enter two vertices: ");
						v1 = scanText.nextLine();
						v2 = scanText.nextLine();
						boolean b = graph.isAdjacent(v1,v2);
						if(b)
							System.out.println(v1+" and "+v2+" are adjacent");
						else
							System.out.println(v1+" and "+v2+" are not adjacent");
						break;
				case 4: System.out.println("Number of edges: "+graph.edgeCount()+"\n"); 
						break;
				case 5: System.out.print("Enter a new name of vertex: ");
						v1 = scanText.nextLine();
						graph.addVertex(v1);
						break;
				case 6: System.out.println("Enter a new edge (vertex pair): ");
						v1 = scanText.nextLine();
						v2 = scanText.nextLine();
						graph.addEdge(v1, v2);
						break;
				case 7: System.out.print("Enter vertex name to be removed: ");
						v1 = scanText.nextLine();
						graph.removeVertex(v1);
				 		break;
				case 8: System.out.println("Enter an edge (vertex pair) to be removed: ");
						v1 = scanText.nextLine();
						v2 = scanText.nextLine();
						graph.removeEdge(v1,v2);
						break;
				case 9: System.out.println("Number of edges: "+graph.edges+"\n"); break;

				default: break;
			}

			input = 0;

		}while(input!=-1);
	}

	public static void main(String[] args){
		new UGraphBLTester();
	}
}