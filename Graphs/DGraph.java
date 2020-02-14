package Graphs;

import java.util.Arrays;

public class DGraph implements Graph{

	public int[] vertices = new int[1];
	public int[] edges = new int[1];
	private int size;
	public Vertex[] graph;

	public DGraph(int size){ //used on modified functions
		this.size = size;
		graph = new Vertex[size];

		for(int i=0; i<size; i++){
			graph[i] = new Vertex();
		}
	}

	public void addVertex(String name){ //dArray used, list might take O(V) on some processes
		String[] verticesName = name.split(" ");

		for(int i=0; i<verticesName.length; i++){
			addV(verticesName[i],0);
		}
	}

	public void addVertexWET(String name){ //merge to addVertex
		String[] verticesName = name.split(" ");

		for(int i=0; i<verticesName.length; i++){
			String[] vertex = verticesName[i].split(":");
			double time = 0.0;
			if(vertex.length!=2){
				System.out.println("Invalid Vertex: "+ Arrays.toString(vertex));
				continue;
			}
			try{
				time = Double.parseDouble(vertex[1]);
			}catch(NumberFormatException n){System.out.println("Illegal execution time input");continue;};
			addV(vertex[0], time);
		}
	}

	private void addV(String name, double time){
		if(indexRetriever(name, graph)!=-1){
			System.out.println("Vertex "+name+" already exists");
			return;
		}

		if(vertices[0]+1>size){
			Vertex[] newGraph = new Vertex[++size];

			for(int i=0; i<newGraph.length-1; i++){
				newGraph[i] = graph[i];
			}
			newGraph[size-1] = new Vertex(name);//edit
			graph = newGraph;
		}
		else
			graph[vertices[0]].setVertexName(name);
		graph[indexRetriever(name, graph)].setExecTime(time);
		vertices[0]++;
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
		int index1 = indexRetriever(v1, graph);
		int index2 = indexRetriever(v2, graph);
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
			graph[index1].outDegree++;
			graph[index2].inDegree++;
			edges[0]++;
		}
	}

	public void removeVertex(String vertex){
		graph = removeVertexING(vertex, graph, vertices, edges, false);
	}

	private Vertex[] removeVertexING(String vertex, Vertex[] graphV, int[] verticesC, int[] edgesC, boolean copy){//fixed needed
		int vIndex = indexRetriever(vertex, graphV);

		if(vIndex==-1){
			System.out.println("Invalid input");
			return graphV;
		}

		Node neighbours = graphV[vIndex].neighbourNode;
		//removing from indegree
		for(int i=0; i<graphV.length; i++){
			if(graphV[i]==null)
				continue;

			neighbours = graphV[i].neighbourNode;

			if(i==vIndex){
				while(neighbours!=null){
					if(copy){
						graphV[indexRetriever(neighbours.getVertexName(), graphV)].inDegreeC--;
						graphV[vIndex].outDegreeC--;
					}
					else{
						graphV[indexRetriever(neighbours.getVertexName(), graphV)].inDegree--;
						graphV[vIndex].outDegree--;
					}
					edgesC[0]--;
					neighbours = neighbours.getNode();
				}
			}
			
			else
				while(neighbours!=null){
					if(vertex.equals(neighbours.getVertexName())){
						if(copy){
							graphV[indexRetriever(neighbours.getVertexName(), graphV)].outDegreeC--;
							graphV[vIndex].inDegreeC--;
						}
						else{
							graphV[indexRetriever(neighbours.getVertexName(), graphV)].outDegree--;
							graphV[vIndex].inDegree--;
						}
						edgesC[0]--;
					}
				neighbours = neighbours.getNode();
			}
		}

		graphV[vIndex] = null;
		graphV = shiftDown(vIndex, graphV);

		verticesC[0]--;
		return graphV;
	}

	private Vertex[] shiftDown(int index, Vertex[] graphV){
		Vertex[] down = new Vertex[graphV.length-1];

		for(int i=0, j=0; i<graphV.length-1; i++, j++){
			if(i==index)
				j++;
			down[i]=graphV[j];
		}
		return down;
	}

	private void removeEdgeInner(String v1, String v2){
		int v4 = indexRetriever(v1,graph);
		Node auxNode = graph[v4].neighbourNode;
		Node prev = null;

		while(auxNode!= null){
			if(v2.equals(auxNode.getVertexName())){
				graph[v4].outDegree--;
				graph[indexRetriever(v2, graph)].inDegree--;
				edges[0]--;

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

		int v1I = indexRetriever(v1, graph);
		int v2I = indexRetriever(v2, graph);

		if(v1I==-1 || v2I==-1){
			System.out.println("Invalid input");
			return;
		}
		removeEdgeInner(v1,v2);
 	}

	public void printNeighbours(String vertex){
		int vertexIndex = indexRetriever(vertex, graph);

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

		int v1I = indexRetriever(v1, graph);
		int v2I = indexRetriever(v2, graph);

		if(v1I==-1 || v2I==-1){
			System.out.println("Invalid input");
			return -1;
		}

		Node auxNode = graph[v1I].neighbourNode;

		while(auxNode!=null){
			int v3I = indexRetriever(auxNode.getVertexName(), graph);
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

		int v1 = indexRetriever(edgePair[0], graph);
		int v2 = indexRetriever(edgePair[1], graph);

		if(v1==-1 || v2==-1)
			return false;

		return isConnected(v1,v2);
	}

	public String nameRetriever(int index, Vertex[] vert){
		return vert[index].getVertexName();
	}

	public int indexRetriever(String name, Vertex[] vert){
		for(int i=0; i<vert.length; i++){
			if(name.equals(vert[i].getVertexName()))
				return i;
		}
		return -1;
	}

	private boolean isConnected(int v1, int v2){
		boolean[] isVisited = new boolean[vertices[0]];
		Stack<Integer> stack = new Stack<>();
		stack.push(v1);

		while(!stack.isEmpty()){
			Node neighbours = graph[stack.peek()].neighbourNode;
			isVisited[stack.pop()]=true;

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName(), graph);

				if(testIndex==v2)
					return true;
				if(!isVisited[testIndex])
					stack.push(testIndex);
				neighbours = neighbours.getNode();
			}
		}
		return false;
	}

	// //edit
	// public Topological_Sort[] topologicalSort(DGraph g){
	// 	Topological_Sort[] collection = new Topological_Sort[1];
	// 	return collection;
	// }

	public void tSort(DGraph g){ //need collection
		TopologicalSort ts = new TopologicalSort(g);
		for(int i=0; i<vertices[0];i++)
				graph[i].copyUpdate();
		try{
			System.out.println(Arrays.toString(ts.sort()));
		}catch(InputNotDAGException d){System.out.println(d);};
	}

	public void dfsTraversal(String startVertex){ //non func on  scyles
		if(indexRetriever(startVertex, graph)==-1){
			System.out.println("Invalid Vertex!");
			return;
		}

		Stack<Integer> stack = new Stack<>();
		boolean[] isVisited = new boolean[vertices[0]];

		stack.push(indexRetriever(startVertex, graph));
		isVisited[indexRetriever(startVertex, graph)] = true;

		while(!stack.isEmpty()){
			int curIn = stack.pop();
			Node neighbours = graph[curIn].neighbourNode;
			System.out.print(nameRetriever(curIn, graph)+" ");

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName(), graph);

				if(!isVisited[testIndex]){
					stack.push(testIndex);
					isVisited[testIndex] = true;
				}
				neighbours = neighbours.getNode();
			}
		}
	}

	public void bfsTraversal(String startVertex){
		if(indexRetriever(startVertex, graph)==-1){
			System.out.println("Invalid vertex!");
			return;
		}

		Queue<Integer> queue = new Queue<>();
		boolean[] isVisited = new boolean[vertices[0]];

		queue.enqueue(indexRetriever(startVertex, graph));
		isVisited[indexRetriever(startVertex, graph)] = true;

		while(!queue.isEmpty()){
			int curIn = queue.dequeue();
			Node neighbours = graph[curIn].neighbourNode;
			System.out.print(nameRetriever(curIn, graph)+" ");

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName(), graph);

				if(!isVisited[testIndex]){
					isVisited[testIndex]=true;
					queue.enqueue(testIndex);
				}
				neighbours = neighbours.getNode();
			}
		}
	}

	public CPResult findCriticalPath(DGraph g) throws InputNotDAGException{
		CPResult cp = new CPResult();
		
		if(isDAG(g.graph))
			throw new InputNotDAGException("Graph not DAG!");

		return cp;
	}

	private boolean isDAG(Vertex[] vert){
		for(int i=0; i<vert.length; i++){
			if(isDAGIF(i, vert))
				return true;
		}
		return false;
	}

	private boolean isDAGIF(int v, Vertex[] vert){
		boolean[] isVisited = new boolean[vert.length];
		Stack<Integer> stack = new Stack<>();
		stack.push(v);

		while(!stack.isEmpty()){
			Node neighbours = vert[stack.peek()].neighbourNode;
			isVisited[stack.pop()]=true;

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName(), graph);

				if(testIndex==v)
					return true;

				if(!isVisited[testIndex])
					stack.push(testIndex);

				neighbours = neighbours.getNode();
			}
		}
		return false;
	}

	public int numberOfVertices(){
		return vertices[0];
	}

	public int numberOfEdges(){
		return edges[0];
	}
 
	public DGraph getGraph(){
		return this;
	}

    protected class CPResult{
    	Node next = null;
    	String vertexName = null;
    	double criticalTime = 0.0;

    	public CPResult(){}

    	public void setNext(Node next){
    		this.next = next;
    	}

    	public void setVertexName(String vertexName){
    		this.vertexName = vertexName;
    	}

    	public void setCritTime(double time){
    		criticalTime = time;
    	}

    	public Node getNext(){
    		return next;
    	}

    	public String getVertexName(){
    		return vertexName;
    	}

    	public double getCritTime(){
    		return criticalTime;
    	}
    }

	protected class Vertex{
		Node neighbourNode = null;
		String name=null;
		int outDegree=0, inDegree=0;
		int outDegreeC=0, inDegreeC=0;
		double executionTime=0.0;

		public Vertex(){}

		public Vertex(String name){
			this.name = name;
		}

		public boolean addNeighbour(String neighbourName, int weight){ //edit adding neighbours to the end
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

		public void setNeighbour(Node neighbourNode){
			this.neighbourNode = neighbourNode;
		}

		public void setExecTime(double d){
			executionTime = d;
		}

		public String getVertexName(){
			return name;
		}

		public int getIn(){
			return inDegree;
		}

		public int getOut(){
			return outDegree;
		}

		public double getExecTime(){
			return executionTime;
		}

		public Node getNeighbour(){
			return neighbourNode;
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

		protected void copyUpdate(){
			inDegreeC = inDegree;
			outDegreeC = outDegree;
		}
	}

	protected class Node{
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

	protected class CNode{
		double criticalTime = 0.0; 
		Vertex previousTask;

		public CNode(){}

		public void getPreviousTask(Vertex vertex){
			previousTask = vertex;
		}

		public void setCritTime(double time){
			criticalTime = time;
		}

		public Vertex getPreviousTask(){
			return previousTask;
		}

		public double getCritTime(){
			return criticalTime;
		}
	}

	protected class TopologicalSort{ //fixed needed getting the TopoVertex of a graph 

		private int[] verticesI, edgesI; 
		private Vertex[] topoV;

		public TopologicalSort(DGraph g){
			verticesI = g.vertices.clone();
			edgesI = g.edges.clone();
			topoV = g.graph.clone();
		}

		public String[] sort() throws InputNotDAGException{
			String[] list = new String[verticesI[0]];

			if(isDAG(topoV))
				throw new InputNotDAGException("Graph not DAG!");
			tsort(list, 0);

			return list;
		}

		private void tsort(String[] list, int ctr){
			for(int i=0; i<topoV.length; i++){
				if(topoV[i]!=null && topoV[i].inDegreeC==0){
					String name = nameRetriever(i, topoV);
					list[ctr++] = name;
					topoV = removeVertexING(name, topoV, verticesI, edgesI, true);//add functionality of passing Vertex
					tsort(list, ctr);
				}
			}
		}
	}
}