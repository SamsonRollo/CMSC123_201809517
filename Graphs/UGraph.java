package Graphs;

import java.util.Arrays;

public class UGraph{

	public int vertices=0;
	public int edges=0;
	private int size=0;
	public Vertex[] graph;

	public UGraph(int size){ //used on modified functions
		this.size = size;
		graph = new Vertex[size];

		 for(int i = 0; i<size; i++)
		 	graph[i] = new Vertex();
	}

	public void addVertex(String name){ //dArray used, list might take O(V) on some processes

		String[] verticesName = name.split(" ");

		for(int i=0; i<verticesName.length; i++){
			addV(verticesName[i]);
		}
	}

	private void addV(String name){
		if(indexRetriever(name)!=-1){
			System.out.println("Vertex "+name+" already exists");
			return;
		}

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

	public void addEdge(String edge){ //v1 = i, v2 = input

		String[] edgesName = edge.split(" ");
		String[] edgeSet;
		String weight;

		for(int i=0; i<edgesName.length; i++){
			
			edgeSet = edgesName[i].split(",");

			if(edgeSet.length<2){
				System.out.println("Invalid set of edges: "+Arrays.toString(edgeSet));
				continue;
			}

			try{
				if(edgeSet[2]==null)
					weight = "0";
				else
					weight = edgeSet[2];
			}catch(Exception f){
				weight = "0";
			}

			addE(edgeSet[0], edgeSet[1], weight);
		}
	}

	private void addE(String v1,String v2, String weight){
		int index1 = indexRetriever(v1);
		int index2 = indexRetriever(v2);
		int weightInt;

		if(index1==-1 || index2==-1){
			System.out.println("Invalid edge "+v1+"-"+v2+"!");
			return;
		}

		try{
			weightInt = Integer.parseInt(weight);
		}catch(NumberFormatException e){
			System.out.println("Invalid weight input on edge "+v1+"-"+v2+"!");
			return;
		}

		boolean eV1 = graph[index1].addNeighbour(v2,weightInt);
		
		if(eV1==false){
			eV1 = graph[index2].addNeighbour(v1,weightInt);
			edges++;
		}
	}

	public void removeVertex(String vertex){//fixed needed
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

	public void removeEdge(String edge){

		String[] edgePair = edge.split(",");

		if(edgePair.length!=2){
			System.out.println("Invalid edge!");
			return;
		}

		String v1 = edgePair[0], v2 = edgePair[1];

		int v1I = indexRetriever(v1);
		int v2I = indexRetriever(v2);

		if(v1I==-1 || v2I==-1){
			System.out.println("Invalid input");
			return;
		}
		removeEdgeInner(v1,v2);
		removeEdgeInner(v2,v1);

		edges--;
 	}

	public void printNeighbours(String vertex){
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
 
	public int isAdjacent(String edge){

		String[] edgePair = edge.split(",");

		if(edgePair.length!=2){
			System.out.println("Invalid edge!");
			return -1;
		}

		String v1 = edgePair[0], v2 = edgePair[1];

		int v1I = indexRetriever(v1);
		int v2I = indexRetriever(v2);

		if(v1I==-1 || v2I==-1){
			System.out.println("Invalid input");
			return -1;
		}

		Node auxNode = graph[v1I].neighbourNode;

		while(auxNode!=null){
			int v3I = indexRetriever(auxNode.getVertexName());
			if(v3I==v2I){
				return 1;
			}
			auxNode = auxNode.getNode();
		}
		return 0;
	}

	public boolean isConnected(String edge){
		String[] edgePair = edge.split(",");

		if(edgePair.length!=2){
			System.out.println("Invalid edge!");
			return false;
		}

		int v1 = indexRetriever(edgePair[0]);
		int v2 = indexRetriever(edgePair[1]);

		if(v1==-1 || v2==-1)
			return false;

		return isConnected(v1,v2);
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
		Stack<Integer> stack = new Stack<>();
		stack.push(v1);

		while(!stack.isEmpty()){
			Node neighbours = graph[stack.peek()].neighbourNode;
			isVisited[stack.pop()]=true;

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(testIndex==v2)
					return true;

				if(!isVisited[testIndex])
					stack.push(testIndex);

				neighbours = neighbours.getNode();
			}
		}

		return false;
	}

	public int[] SSUPL(String initialVertex){
		String[] nodevalues = new String[vertices];
		int[] pathLength = new int[vertices];
		Queue<Integer> queue = new Queue<>();

		if(indexRetriever(initialVertex)==-1){
			System.out.println("Invalid initial Vertex!");
			return pathLength;
		}

		queue.enqueue(indexRetriever(initialVertex));
		nodevalues[queue.peek()] = "Head";

		while(!queue.isEmpty()){
			String currentHead = nameRetriever(queue.peek());
			Node neighbours = graph[queue.dequeue()].neighbourNode;

			while(neighbours!=null){
				int currentNeighbourIndex = indexRetriever(neighbours.getVertexName());

				if(nodevalues[currentNeighbourIndex]==null){
					nodevalues[currentNeighbourIndex] = currentHead;
					queue.enqueue(currentNeighbourIndex);
				}
				neighbours = neighbours.getNode();
			}
		}

		for(int i=0; i<vertices; i++){
			try{
				pathLength[i] = headSearcher(nodevalues, initialVertex, 0, i);
			}catch(Exception e){pathLength[i]=-1;};
		}

		return pathLength;
	}

	private int headSearcher(String[] values, String head, int length, int currentIndex){
		if(values[currentIndex].equals("Head"))
			return 0;
		else if(values[currentIndex].equals(head))
			return length+1;
		else
			return headSearcher(values, head, length+1, indexRetriever(values[currentIndex]));

	}

	public void dfsTraversal(String startVertex){ //non func on  scyles
		if(indexRetriever(startVertex)==-1){
			System.out.println("Invalid Vertex!");
			return;
		}

		Stack<Integer> stack = new Stack<>();
		boolean[] isVisited = new boolean[vertices];

		stack.push(indexRetriever(startVertex));
		System.out.print("\nDepth-first Traversal order: ");
		isVisited[indexRetriever(startVertex)] = true;

		while(!stack.isEmpty()){

			int curIn = stack.pop();
			Node neighbours = graph[curIn].neighbourNode;

			System.out.print(nameRetriever(curIn)+" ");

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(!isVisited[testIndex]){
					stack.push(testIndex);
					isVisited[testIndex] = true;
				}

				neighbours = neighbours.getNode();
			}
		}
	}

	public void bfsTraversal(String startVertex){
		if(indexRetriever(startVertex)==-1){
			System.out.println("Invalid vertex!");
			return;
		}

		Queue<Integer> queue = new Queue<>();
		boolean[] isVisited = new boolean[vertices];

		queue.enqueue(indexRetriever(startVertex));
		System.out.print("\nBreadth-first Traversal order: ");
		isVisited[indexRetriever(startVertex)] = true;

		while(!queue.isEmpty()){
			int curIn = queue.dequeue();
			Node neighbours = graph[curIn].neighbourNode;

			System.out.print(nameRetriever(curIn)+" ");

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(!isVisited[testIndex]){
					isVisited[testIndex]=true;
					queue.enqueue(testIndex);
				}

				neighbours = neighbours.getNode();
			}

			if(queue.isEmpty())
				return;
		}
	}

	public int numberOfVertices(){
		return vertices;
	}

	public int numberOfEdges(){
		return edges;
	}

	private class Vertex{
		Node neighbourNode = null;
		String name;

		public Vertex(){}

		public Vertex(String name){
			this.name = name;
		}

		public boolean addNeighbour(String neighbourName, int weight){
			//check if neighbor is valid and already exists
			if(neighbourNode==null){
				neighbourNode = new Node(neighbourName, null, weight);
				return false;
			}

			Node auxNode = neighbourNode;

			while(auxNode!=null){
				if(auxNode.getVertexName()==neighbourName)
					return true;
	
				auxNode = auxNode.getNode();
			}
			
			Node newNode = new Node(neighbourName, null, weight);
			arrangeNeighbours(newNode);
	
			return false;
		}

		public void setVertexName(String name){
			this.name = name;
		}

		public String getVertexName(){
			return name;
		}

		private void arrangeNeighbours(Node newNode){//remake fast sort algo
			Node auxNode = neighbourNode;
			Node prev = null;

			if(newNode.getWeight()==0){
				newNode.setNode(neighbourNode);
				neighbourNode = newNode;
				return;
			}

			while(auxNode!=null){
				if(newNode.getWeight()<auxNode.getWeight()){
					if(prev==null){
						newNode.setNode(neighbourNode);
						neighbourNode = newNode;
						return;
					}
					newNode.setNode(auxNode);
					prev.setNode(newNode);
					return;
				}
				prev = auxNode;
				auxNode = auxNode.getNode();
			}
			prev.setNode(newNode); //capture
		}
	}

	private class Node{
		Node next;
		String vertexName;
		int weight=0;

		public Node(String vertexName, Node next, int weight){
			this.vertexName = vertexName;
			this.next = next;
			this.weight = weight;
		}	

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

		public void setWeight(int weight){
			this.weight = weight;
		}

		public int getWeight(){
			return weight;
		}
	}
}