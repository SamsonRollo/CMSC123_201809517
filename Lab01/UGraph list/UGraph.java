import java.util.Stack;

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
		if(vertices+1>size){
			Vertex[] newGraph = new Vertex[++size];

			for(int i=0; i<newGraph.length-1; i++){
				newGraph[i] = graph[i];
			}
			newGraph[size-1] = new Vertex(name);//edit
			graph = newGraph;
		}
		else
			graph[vertices].setVertexName(name);
		vertices++;

	}

	protected void addEdge(String v1, String v2){ //v1 = i, v2 = input
		int index1 = indexRetriever(v1);
		int index2 = indexRetriever(v2);

		if(index1==-1 || index2==-1){
			System.out.println("Invalid input");
			return;
		}

		boolean eV1 = graph[index1].addNeighbour(v2);
		
		if(eV1==false){
			eV1 = graph[index2].addNeighbour(v1);
			edges++;
		}
	}

	protected void removeVertex(String vertex){//fixed needed
		int vIndex = indexRetriever(vertex);

		if(vIndex==-1){
			System.out.println("Invalid input");
			return;
		}

		Node neighbours = graph[vIndex].neighbourNode;

		while(neighbours!=null){
			removeEdgeInner(vertex, neighbours.getVertexName()); //v, n
			neighbours = neighbours.getNode();
		}

		graph[vIndex] = null;

		if(vertices==size)
			shiftDown(vIndex);

		vertices--;
	}

	private void shiftDown(int index){
		Vertex[] down = new Vertex[size-1];

		for(int i=0, j=0; i<vertices-1; i++, j++){
			if(i==index)
				j++;
			down[i]=graph[j];
		}

		graph = down;
	}

	private void removeEdgeInner(String v1, String v2){ //edge not removed
		int v4 = indexRetriever(v2);
		Node auxNode = graph[v4].neighbourNode;
		Node prev = null;

		while(auxNode!= null){

			if(v1.equals(auxNode.getVertexName())){
				if(prev==null){//first element on the list
					graph[v4].neighbourNode = auxNode.getNode();
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

		System.out.println(v1I+" "+v2I);

		if(v1I==-1 || v2I==-1){
			System.out.println("Invalid input");
			return;
		}

		removeEdgeInner(v1,v2);
		removeEdgeInner(v2, v1);

		edges--;
 	}


	protected void printGraph(String vertex){
		int vertexIndex = indexRetriever(vertex);

		if(vertexIndex==-1){
			System.out.println("Invalid input");
			return;
		}

		System.out.print("Vertex "+vertex+" has neighbours ");
		Node auxNode = graph[vertexIndex].neighbourNode;

		while(auxNode!=null){
			System.out.print(auxNode.getVertexName()+" ");
			auxNode = auxNode.getNode();
		}
		System.out.println("");
	}

	protected int isAdjacent(String vertex1, String vertex2){
		int v1 = indexRetriever(vertex1);
		int v2 = indexRetriever(vertex2);

		if(v1==-1 || v2==-1){
			System.out.println("Invalid input");
			return -1;
		}

		Node auxNode = graph[v1].neighbourNode;

		while(auxNode!=null){
			int v3 = indexRetriever(auxNode.getVertexName());
			if(v3==v2){
				return 1;
			}
			auxNode = auxNode.getNode();
		}
		return 0;
	}

	protected boolean isConnected(String v1, String v2){
		return isConnected(indexRetriever(v1),indexRetriever(v2));
	}

	private String nameRetriever(int index){
		return graph[index].getVertexName();
	}

	private int indexRetriever(String name){
		for(int i=0; i<graph.length; i++){
			if(name.equals(graph[i].getVertexName()))
				return i;
		}

		return -1;
	}

	private boolean isConnected(int v1, int v2){
		boolean[] isVisited = new boolean[vertices];
		Stack<Integer> adjVertices = new Stack<>();
		adjVertices.push(v1);

		for(int i=0; i<vertices; i++){
			Node neighbours = graph[adjVertices.peek()].neighbourNode;
			isVisited[adjVertices.pop()]=true;

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(testIndex==v2)
					return true;

				if(!isVisited[testIndex])
					adjVertices.push(testIndex);

				neighbours = neighbours.getNode();
			}

			if(adjVertices.isEmpty())
				return false;
		}

		return false;
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

		public boolean addNeighbour(String neighbourName){
			//check if neighbor is valid and already exists
			if(neighbourNode==null){
				neighbourNode = new Node(neighbourName, null);
				return false;
			}

			boolean nodeExist = false;
			Node auxNode = neighbourNode;

			while(auxNode!=null){
				if(auxNode.getVertexName()==neighbourName){
					nodeExist = true;
					return nodeExist;
				}
				auxNode = auxNode.getNode();
			}

			if(nodeExist==false){
				Node newNode = new Node(neighbourName, neighbourNode);
				neighbourNode = newNode;
			}
			return nodeExist;
		}

		protected String getVertexName(){
			return name;
		}
	}

	class Node{
		Node next;
		int vertexIndex;
		String vertexName;

		public Node(String vertexName, Node next){
			this.vertexName = vertexName;
			this.next = next;
		}

		public void setNode(Node neighbour){
			next = neighbour;
		}

		public Node getNode(){
			return next;
		}

		public void setVertexName(String vertexName){
			this.vertexName = vertexName;
		}

		public String getVertexName(){
			return vertexName;
		}
	}
}