
public class UGraph{

	protected int vertices=0;
	protected int edges=0;
	private Node vertex;
	protected Vertex[] graph; // transform to list

	public UGraph(int vertices){
		graph = new Vertex[vertices];
		this.vertices = vertices;

		for(int i = 0; i<vertices; i++)
			graph[i] = new Vertex();
	}

	//cahnge to link of vertices and adding is possible and removing
	//sort vertices either neighbor or vertices itself

	protected void addVertex(int vertexIndex){
		Node newVertex =  new Node(vertexIndex, vertex);
		vertex = newVertex;
		//vertices++;
	}

	protected void addEdge(int v1, int v2){ //v1 = i, v2 = input
		boolean eV1 = graph[v1].addNeighbour(v2);
		
		if(eV1==false){
			eV1 = graph[v2].addNeighbour(v1);
			edges++;
		}
	}

	protected void removeVertex(int vertexIndex){//fixed needed
		Node auxNode = vertex;
		Node prevNode = null;

		while(auxNode!=null){
			if(auxNode.getVertexIndex()==vertexIndex){

				if(prevNode!=null)
					prevNode=auxNode.getNode();
				break; 	
			}
			prevNode = auxNode;
			auxNode = auxNode.getNode();
		}
	}

	protected void removeEdge(int v1, int v2){

	}

	protected void printGraph(int vertexIndex){
		System.out.print("Vertex "+vertexIndex+" has neighbours ");
		Node auxNode = graph[vertexIndex].neighbourNode;

		while(auxNode!=null){
			System.out.print(auxNode.getVertexIndex()+" ");
			auxNode = auxNode.getNode();
		}
		System.out.println("");
	}

	protected boolean isAdjacent(int v1, int v2){
		boolean adjacent = false;
		Node auxNode = graph[v1].neighbourNode;
		while(auxNode!=null){
			if(auxNode.getVertexIndex()==v2){
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
				if(auxNode.getVertexIndex()==neighbourIndex){
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

		protected boolean removeNeighbour(){return false;}
	}

	class Node{
		Node next;
		int vertexIndex;

		public Node(int vertexIndex, Node next){
			this.vertexIndex = vertexIndex;
			this.next = next;
		}

		public void setNode(Node neighbour){
			next = neighbour;
		}

		public Node getNode(){
			return next;
		}

		public void setVertexIndex(int vertexIndex){
			this.vertexIndex = vertexIndex;
		}

		public int getVertexIndex(){
			return vertexIndex;
		}
	}
}