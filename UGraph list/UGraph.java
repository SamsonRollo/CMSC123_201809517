
public class UGraph{

	protected int vertices=0;
	protected int edges=0;
	private int size=0;
	protected Vertex[] graph; // transform to list

	public UGraph(int vertices){
		graph = new Vertex[vertices];
		this.vertices = vertices;

		for(int i = 0; i<vertices; i++)
			graph[i] = new Vertex();
	}

	public UGraph(int size, boolean dynamic){ //used on modified functions
		this.size = size;
		graph = new Vertex[size];

		 for(int i = 0; i<size; i++)
		 	graph[i] = new Vertex();
	}

	//cahnge to link of vertices and adding is possible and removing
	//sort vertices either neighbor or vertices itself

	protected void addVertex(String name){ //dArray used, list might take O(V) on some processes
		if(vertices+1==size){
			Vertex[] newGraph = new Vertex[size++];

			for(int i=0; i>newGraph.length-1; i++){
				newGraph[i] = graph[i];
			}
			newGraph[size-1] = new Vertex(name);//edit
		}
		else
			graph[vertices].setVertexName(name);
		vertices++;
	}

	protected void addEdge(String v1, String v2){ //v1 = i, v2 = input
		int index1 = indexRetriever(v1);
		int index2 = indexRetriever(v2);

		if(index1==-1 || index2==-1)
			return;

		boolean eV1 = graph[index1].addNeighbour(index2);
		
		if(eV1==false){
			eV1 = graph[index2].addNeighbour(index1);
			edges++;
		}
	}

	protected void removeVertex(String vertex){//fixed needed
		int vIndex = indexRetriever(vertex);

		if(vIndex==-1)
			return;

		Node neighbours = graph[vIndex].neighbourNode;

		while(neighbours!=null){
			removeEdge(vIndex, neighbours.getVertexIndex()); //v, n
			neighbours = neighbours.getNode();
		}
		vertices--;
	}

	private void removeEdge(int v1, int v2){
		Node auxNode = graph[v2].neighbourNode;
		Node prev = null;

		while(auxNode.getNode()!= null){
			if(auxNode.getVertexIndex()==v1){
				if(prev==null){//first element on the list
					graph[v2].neighbourNode = auxNode.getNode();
					return;
				}
				else{
					prev.setNode(auxNode.getNode());
					return;
				}
			}

			prev = auxNode;
			auxNode = auxNode.getNode();
		}
	}

	protected void removeEdge(String v1, String v2){
		int v1I = indexRetriever(v1);
		int v2I = indexRetriever(v2);

		if(v1I==-1 || v2I==-1)
			return;

		removeEdge(v1I,v2I);
		removeEdge(v2I, v1I);

		edges--;
 	}


	protected void printGraph(String vertex){
		int vertexIndex = indexRetriever(vertex);

		System.out.print("Vertex "+vertexIndex+" has neighbours ");
		Node auxNode = graph[vertexIndex].neighbourNode;

		while(auxNode!=null){
			System.out.print(auxNode.getVertexIndex()+" ");
			auxNode = auxNode.getNode();
		}
		System.out.println("");
	}

	protected boolean isAdjacent(String vertex1, String vertex2){
		int v1 = indexRetriever(vertex1);
		int v2 = indexRetriever(vertex2);

		System.out.println("test "+v1+" "+v2);

		if(v1==-1 || v2==-1)
			return false;

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

	private int indexRetriever(String name){
		for(int i=0; i<graph.length; i++){
			if(name.equals(graph[i].getVertexName()))
				return i;
		}

		return -1;
	}

	private class Vertex{
		Node neighbourNode = null;
		String name;

		public Vertex(){}

		public Vertex(String name){
			this.name = name;
		}

		public void setVertexName(String name){
			this.name = name;
		}

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

		protected String getVertexName(){
			return name;
		}
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