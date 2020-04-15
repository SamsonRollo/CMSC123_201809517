package Graphs;

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import Exceptions.*;
import Algorithms.*;

public class DGraph implements Graph{

	public int vertices = 0, edges = 0;
	private int size;
	public Vertex[] graph;

	public DGraph(int size){
		this.size = size;
		graph = new Vertex[size];

		for(int i=0; i<size; i++){
			graph[i] = new Vertex();
		}
	}

	public void readGraph(String fileName){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("Source/"+fileName).getAbsoluteFile()));
			boolean v = false;
			String line;

			while((line = reader.readLine())!=null){
				if(line.startsWith("EDGES")){
					v = true;
					continue;
				}
				if(!v)
					addVertex(line);
				else
					addEdge(line);
			}
		}catch(Exception f){System.out.println("Error with file accessing or reading!");};
	}

	public void addVertex(String name){ //dArray used, list might take O(V) on some processes
		String[] verticesName = name.split(" ");
		double executionTime = 0.0;
		String vertexName = null;

		for(int i=0; i<verticesName.length; i++){
			vertexName = verticesName[i];
			if(verticesName[i].contains(":")){
				String[] vertexComponent = verticesName[i].split(":");
				vertexName = vertexComponent[0];
				try{
					executionTime = Double.parseDouble(vertexComponent[1]);
				}catch(NumberFormatException n){System.out.println("Illegal execution time input");continue;};
			}	
			try{
				addV(vertexName,executionTime);
			}catch(VertexAlreadyExistsException v){System.out.println(v);};
		}
	}

	private void addV(String name, double time) throws VertexAlreadyExistsException{
		if(indexRetriever(name)!=-1)
			throw new VertexAlreadyExistsException(name);
			
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
		graph[indexRetriever(name)].setExecTime(time);
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
			}catch(Exception f){weight = "0";};

			try{
				addE(edgeSet[0], edgeSet[1], weight);
			}catch(InvalidEdgeException ie){System.out.println(ie);};
		}
	}

	private void addE(String v1,String v2, String weight)throws InvalidEdgeException{
		int index1 = indexRetriever(v1);
		int index2 = indexRetriever(v2);
		double weightInt;

		if(index1==-1 || index2==-1)
			throw new InvalidEdgeException("One or more of the vertex does not exists.");

		try{
			weightInt = Double.parseDouble(weight);
		}catch(NumberFormatException e){
			System.out.println("Invalid weight input on edge "+v1+"-"+v2+"!");
			return;
		}

		boolean eV1 = graph[index1].addNeighbour(v2,weightInt);
		if(eV1==false){
			graph[index1].outDegree++;
			graph[index2].inDegree++;
			edges++;
		}
	}

	public void removeVertex(String vertex){
		try{
			removeVertexAux(vertex);
		}catch(InvalidVertexException iv){System.out.println(iv);};
	}

	private void removeVertexAux(String vertex) throws InvalidVertexException{//fixed needed
		int vertexIndex = indexRetriever(vertex);
		if(vertexIndex==-1)
			throw new InvalidVertexException(vertex+" does not exist.");
			
		VNode neighbours = graph[vertexIndex].getNeighbour();
		while(neighbours!=null){
			removeEdgeInner(vertex, neighbours.getVertexName()); //v, n
			neighbours = neighbours.getVNode();
		}
		graph[vertexIndex] = null;
		shiftDown(vertexIndex);
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

	public void removeEdge(String edge){
		try{
			removeEdgeAux(edge);
		}catch(InvalidEdgeException ie){System.out.println(ie);};
	}

	private void removeEdgeAux(String edge) throws InvalidEdgeException{

		String[] vertexPair = edge.split(",");

		if(vertexPair.length!=2)
			throw new InvalidEdgeException("Input not paired properly.");

		String v1 = vertexPair[0], v2 = vertexPair[1];

		int v1I = indexRetriever(v1);
		int v2I = indexRetriever(v2);

		if(v1I==-1 || v2I==-1)
			throw new InvalidEdgeException("One or more of the vertex does not exists.");
			
		removeEdgeInner(v1,v2);
 	}

 	private void removeEdgeInner(String v1, String v2){
		int vertexIndex = indexRetriever(v1);
		VNode auxVNode = graph[vertexIndex].getNeighbour();
		VNode prev = null;

		while(auxVNode!= null){
			if(v2.equals(auxVNode.getVertexName())){
				graph[vertexIndex].outDegree--;
				graph[indexRetriever(v2)].inDegree--;
				edges--;

				if(prev==null){//first element on the list
					graph[vertexIndex].setNeighbour(auxVNode.getVNode());
					return;
				}
				else{
					prev.setVNode(auxVNode.getVNode());
					return;
				}
			}
			prev = auxVNode;
			auxVNode = auxVNode.getVNode();
		}
	}

	public void printNeighbours(String vertex){
		try{
			printNeighboursAux(vertex);
		}catch(InvalidVertexException iv){System.out.println(iv);};
	}

	private void printNeighboursAux(String vertex) throws InvalidVertexException{
		int vertexIndex = indexRetriever(vertex);

		if(vertexIndex==-1)
			throw new InvalidVertexException("Vertex does not exists.");

		System.out.print("Vertex "+vertex+" has neighbours ");
		VNode auxVNode = graph[vertexIndex].getNeighbour();

		while(auxVNode!=null){
			System.out.print(auxVNode.getVertexName()+" ");
			auxVNode = auxVNode.getVNode();
		}
		System.out.println("");
	}

	public int isAdjacent(String edge){
		int ret=-1;
		try{
			ret = isAdjacentAux(edge);
		}catch(InvalidEdgeException ie){System.out.println(ie);};
		
		return ret;
	}
 
	private int isAdjacentAux(String edge) throws InvalidEdgeException{

		String[] vertexPair = edge.split(",");

		if(vertexPair.length!=2)
			throw new InvalidEdgeException("Input not paired properly.");

		String v1 = vertexPair[0], v2 = vertexPair[1];

		int v1I = indexRetriever(v1);
		int v2I = indexRetriever(v2);

		if(v1I==-1 || v2I==-1)
			throw new InvalidEdgeException("One or more of the vertex does not exists.");

		VNode auxVNode = graph[v1I].getNeighbour();

		while(auxVNode!=null){
			int v3I = indexRetriever(auxVNode.getVertexName());
			if(v3I==v2I){
				return 1;
			}
			auxVNode = auxVNode.getVNode();
		}
		return 0;
	}

	public boolean isConnected(String edge){
		try{
			return isConnectedAux(edge);
		}catch(InvalidEdgeException ie){System.out.println(ie);};
		return false;
	}
	
	private boolean isConnectedAux(String edge) throws InvalidEdgeException{
		String[] vertexPair = edge.split(",");

		if(vertexPair.length!=2)
			throw new InvalidEdgeException("Input not paired properly.");

		int v1I = indexRetriever(vertexPair[0]);
		int v2I = indexRetriever(vertexPair[1]);

		if(v1I==-1 || v2I==-1)
			return false;

		return isConnected(v1I,v2I);
	}

	public String nameRetriever(int index){
		return graph[index].getVertexName();
	}

	public int indexRetriever(String name){
		for(int i=0; i<vertices; i++){
			if(name.equals(graph[i].getVertexName()))
				return i;
		}
		return -1;
	}

	private boolean isConnected(int v1I, int v2I){
		boolean[] isVisited = new boolean[vertices];
		Stack<Integer> stack = new Stack<>();
		stack.push(v1I);

		while(!stack.isEmpty()){
			VNode neighbours = graph[stack.peek()].getNeighbour();
			isVisited[stack.pop()]=true;

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(testIndex==v2I)
					return true;
				if(!isVisited[testIndex])
					stack.push(testIndex);
				neighbours = neighbours.getVNode();
			}
		}
		return false;
	}

	public void dfsTraversal(String startVertex) throws InvalidVertexException{ //non func on  scyles
		if(indexRetriever(startVertex)==-1)
			throw new InvalidVertexException(startVertex+" does not exists.");

		Stack<Integer> stack = new Stack<>();
		boolean[] isVisited = new boolean[vertices];

		stack.push(indexRetriever(startVertex));
		isVisited[indexRetriever(startVertex)] = true;

		while(!stack.isEmpty()){
			int currentVertex = stack.pop();
			VNode neighbours = graph[currentVertex].getNeighbour();
			System.out.print(nameRetriever(currentVertex)+" ");

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(!isVisited[testIndex]){
					stack.push(testIndex);
					isVisited[testIndex] = true;
				}
				neighbours = neighbours.getVNode();
			}
		}
	}

	public void bfsTraversal(String startVertex) throws InvalidVertexException{
		if(indexRetriever(startVertex)==-1)
			throw new InvalidVertexException(startVertex+" does not exists.");

		Queue<Integer> queue = new Queue<>();
		boolean[] isVisited = new boolean[vertices];

		queue.enqueue(indexRetriever(startVertex));
		isVisited[indexRetriever(startVertex)] = true;

		while(!queue.isEmpty()){
			int currentVertex = queue.dequeue();
			VNode neighbours = graph[currentVertex].getNeighbour();
			System.out.print(nameRetriever(currentVertex)+" ");

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(!isVisited[testIndex]){
					isVisited[testIndex]=true;
					queue.enqueue(testIndex);
				}
				neighbours = neighbours.getVNode();
			}
		}
	}

	//test for dijkstra
	public void dijkstra(DGraph g, String v){
		Dijkstra dk = new Dijkstra();
		try{
			try{
				try{
					dk.findPath(g, v);
				}catch(InvalidVertexException e){System.out.println(e);};
			}catch(NegativeEdgeException d){System.out.println(d);};
		}catch(CannotFindGraphException c){System.out.println(c);};
	}


	public void tSort(DGraph g){
		TopologicalSort ts = new TopologicalSort();
		try{
			try{
				String[] list = ts.getAllTopologicalSort(g);
				for(int i=0; i<list.length; i++)
					if(list[i]!=null)
						System.out.println(list[i]);
			}catch(InputNotDAGException d){System.out.println(d);};
		}catch(CannotFindGraphException c){System.out.println(c);};
	}	

	public CPResult findCriticalPath(DGraph g){
		CriticalPath cp = new CriticalPath();
		try{
			try{
				return cp.findCriticalPath(g);
			}catch(InputNotDAGException d){System.out.println(d);};
		}catch(CannotFindGraphException c){System.out.println(c);};
		
		return new CPResult();
	}

	public CPResult findLeastCriticalPath(DGraph g){
		CriticalPath cp = new CriticalPath();
		try{
			try{
				return cp.leastCriticalPath(g);
			}catch(InputNotDAGException d){System.out.println(d);};
		}catch(CannotFindGraphException c){System.out.println(c);};

		return new CPResult();
	}

	public int numberOfVertices(){
		return vertices;
	}

	public int numberOfEdges(){
		return edges;
	}
 
	public DGraph getGraph(){
		return this;
	}

	public Vertex[] getVertexSet(){ //use clone soon
		return graph;
	}
}