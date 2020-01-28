import java.util.Scanner;

public class UGraphTester{

	private UGraph graph;
	
	public UGraphTester(){
		int input = 0;
		Scanner scan = new Scanner(System.in);
		System.out.print("Insert number of Vertices: ");
		input = scan.nextInt();
		graph = new UGraph(input);

		input = 0;
		for(int i = 0; i<graph.vertices; i++){ //add edges
			while(input!=-1){
				System.out.println("Enter valid edges for Vertex "+i+" (-1 to complete):");
				input = scan.nextInt();
				if(input==-1)
					break;
				if(input>=0 && input<graph.vertices){
					graph.addEdge(i,input);
				}
				else
					System.out.println("Invalid input!");
			}
			input = 0;
		}

		input = 0;
		do{
			System.out.print("\n\nCHoose\n\t1.Number of vertices\n\t2.Check list of adjacent vertices\n\t3.Adjacency test\n\t4.Number of edges\nChoice:");
			input = scan.nextInt();

			switch(input){
				case 1: System.out.println("Number of vertices: "+graph.vertices);break;
				case 2: System.out.print("Enter vertex: ");
						input = scan.nextInt();
						if(input<0 || input>graph.vertices-1)
							System.out.println("Invalid vertex!");
						else
							graph.printGraph(input);
						input = 2;
						break;
				case 3: int v1=0, v2=0;
						System.out.print("Enter two vertices: ");
						v1 = scan.nextInt();
						v2 = scan.nextInt();
						if(v1<0 || v2<0 || v1>graph.vertices-1 || v2>graph.vertices-1)
							System.out.println("Invalid vertex pair!");
						else 
							System.out.println(v1+" and "+v2+" :"+graph.isAdjacent(v1,v2));
						break;
				case 4: System.out.println("Number of edges: "+graph.edges+"\n"); break;
			}

			input = 0;

		}while(input!=-1);
	}

	public static void main(String[] args){
		new UGraphTester();
	}
}