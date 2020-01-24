import java.util.Scanner;

public class UGraph{
	
	Graph[] graph;
	private int vertices;

	public UGraph(){
		int input = 0;
		Scanner scan = new Scanner(System.in);
		System.out.print("Insert number of Vertices: ");
		vertices = scan.nextInt();

		graph = new Graph[vertices];
		for(int i = 0; i<graph.length; i++){
			graph[i] = new Graph();
		}

		for(int i = 0; i<graph.length; i++){ //add edges
			while(input!=-1){
				System.out.println("Enter valid edges for vertex "+i+":");
				input = scan.nextInt();
				if(input==-1)
					break;
				if(input>=0 && input<vertices){
					graph[i].addneighbour(input);

					graph[input].addneighbour(i);
				}
			}
			input = 0;
		}

		input = 0;
		do{
			System.out.print("CHoose\n\t1.Number of vertices\n\t2.Print graph\nChoice:");
			input = scan.nextInt();

			switch(input){
				case 1: System.out.println("Number of vertices: "+vertices);break;
				case 2: printGraph();break;
			}

			input = 0;

		}while(input!=-1);

	}

	private void printGraph(){
		for(int i = 0; i<graph.length; i++){
			System.out.print("Vertex "+i+" has neighbours ");
			Node auxNode = graph[i].neighbourNode;
			while(auxNode!=null){
				System.out.print(auxNode.getNeighbourIndex()+" ");
				auxNode = auxNode.getNode();
			}
			System.out.println("");
		}
	}



	class Graph{
		Node neighbourNode;
		String name;

		public Graph(){
			neighbourNode = null;
		}

		public void addneighbour(int neighbour){
			//check if neighbor is valid and already exists
			if(neighbourNode==null){
				Node newNode = new Node(neighbour, null);
				neighbourNode = newNode;
				return;
			}

			boolean nodeExist = false;
			Node auxNode = neighbourNode;

			while(auxNode!=null){

				if(auxNode.getNeighbourIndex()==neighbour){
					nodeExist = true;
					break;
				}

				auxNode = auxNode.getNode();
			}

			if(nodeExist==false){
				Node newNode = new Node(neighbour, neighbourNode);
				neighbourNode = newNode;
			}
		}
	}

	class Node{
			Node next;
			int neighbourIndex;

			public Node(int neighbourIndex, Node next){
				this.neighbourIndex = neighbourIndex;
				this.next = next;
			}

			public void setNode(Node neighbour){
				next = neighbour;
			}

			public Node getNode(){
				return next;
			}

			public void setNeighbourInde(int neighbourIndex){
				this.neighbourIndex = neighbourIndex;
			}

			public int getNeighbourIndex(){
				return neighbourIndex;
			}
		}

	public static void main(String[] args){
		new UGraph();
	}
}