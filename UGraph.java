
public class UGraph{

	protected int vertices;
	protected int edges=0;
	protected Vertex[] graph;

	public UGraph(int vertices){
		graph = new Vertex[vertices];
		this.vertices = vertices;

		for(int i = 0; i<vertices; i++)
			graph[i] = new Vertex();
	}

	//cahnge to link of vertices and adding is possible and removing

	protected void addEdge(int v1, int v2){ //v1 = i, v2 = input
		boolean eV1 = graph[v1].addNeighbour(v2);
		
		if(eV1==false){
			eV1 = graph[v2].addNeighbour(v1);
			edges++;
		}

	}

	protected void printGraph(int vertexIndex){
		System.out.print("Vertex "+vertexIndex+" has neighbours ");
		Node auxNode = graph[vertexIndex].neighbourNode;

		while(auxNode!=null){
			System.out.print(auxNode.getNeighbourIndex()+" ");
			auxNode = auxNode.getNode();
		}
		System.out.println("");
	}

	protected boolean isAdjacent(int v1, int v2){
		boolean adjacent = false;
		Node auxNode = graph[v1].neighbourNode;
		while(auxNode!=null){
			if(auxNode.getNeighbourIndex()==v2){
				adjacent = true;
				break;
			}
			auxNode = auxNode.getNode();
		}
		return adjacent;
	}

	private class Vertex{
		Node neighbourNode = null;
		String name;

		public Vertex(){}

		public boolean addNeighbour(int neighbourIndex){
			//check if neighbor is valid and already exists
			if(neighbourNode==null){
				neighbourNode = new Node(neighbourIndex, null);
				return false;
			}

			boolean nodeExist = false;
			Node auxNode = neighbourNode;

			while(auxNode!=null){
				if(auxNode.getNeighbourIndex()==neighbourIndex){
					nodeExist = true;
					return nodeExist;
				}
				auxNode = auxNode.getNode();
			}

			if(nodeExist==false){
				Node newNode = new Node(neighbourIndex, neighbourNode);
				neighbourNode = newNode;
			}
			return nodeExist;
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
}