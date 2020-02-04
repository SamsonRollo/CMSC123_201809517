package Graphs;

import java.util.Arrays;

public class UGraphBL implements Graph{

	public Edge edges=null;
	public Vertex vertices=null;
	public int vertexCount=0, edgeCount=0;
	
	public UGraphBL(){}

	public void addVertex(String name){
		String[] verticesName = name.split(" ");

		for(int i=0; i<verticesName.length; i++){
			addV(verticesName[i]);
		}
	}

	private void addV(String name){
		if(doesExistVertex(name)){
			System.out.println("Vertex already exists!");
			return;
		}

		Vertex v = new Vertex(name);

		if(vertices==null)
			vertices = v;
		else{
			v.setNext(vertices);
			vertices = v;
		}
		vertexCount++;
	}

	public void addEdge(String edge){
		String[] edgesName = edge.split(" ");
		String[] edgeSet;

		for(int i=0; i<edgesName.length; i++){			
			edgeSet = edgesName[i].split(",");

			if(edgeSet.length<2){
				System.out.println("Invalid set of edges: "+Arrays.toString(edgeSet));
				continue;
			}

			addE(edgeSet[0], edgeSet[1]);
		}
	}

	private void addE(String v1, String v2){
		if(!(doesExistVertex(v1) && doesExistVertex(v2))){
			System.out.println("One or two of the vertex does not exist!");
			return;
		}

		if(doesExistEdge(v1,v2)){
			System.out.println("Edge already exists!");
			return;
		}

		Edge e = new Edge(v1,v2);

		if(edges==null)
			edges = e;
		else{
			e.setNext(edges);
			edges = e;
		}

		edgeCount++;
	}

	public void removeVertex(String vertex){
		Vertex auxV = vertices;
		Vertex prev = null;

		while(auxV!=null){

			if(vertex.equals(auxV.getName())){
				if(prev==null)
					vertices = vertices.getNext();
				else
					prev.setNext(auxV.getNext());

				removeEdgesContaining(vertex);
				vertexCount--;
				return;
			}

			prev = auxV;
			auxV = auxV.getNext();
		}
	}

	public void removeEdge(String edge){
		String[] edgePair = edge.split(",");

		if(edgePair.length!=2){
			System.out.println("Invalid pair input");
			return;
		}

		String v1 = edgePair[0], v2 = edgePair[1];
		Edge auxE = edges;
		Edge prev = null;

		while(auxE!=null){
			if((v1.equals(auxE.getComponent1()) && v2.equals(auxE.getComponent2())) 
				|| (v2.equals(auxE.getComponent1()) && v1.equals(auxE.getComponent2()))){
					if(prev==null)
						edges = edges.getNext();
					else
						prev.setNext(auxE.getNext());
					edgeCount--;
					return;
			}
		prev = auxE;
		auxE = auxE.getNext();
		}
	}

	public int isAdjacent(String edge){
		String[] edgePair = edge.split(",");

		if(edgePair.length!=2)
			return -1;

		return doesExistEdge(edgePair[0],edgePair[1])?1:0;
	}

	public int numberOfEdges(){
		return edgeCount;
	}

	public int numberOfVertices(){
		return vertexCount;
	}

	public boolean isConnected(String edge){//need repair
		String[] pair = edge.split(",");

		if(pair.length!=2){
			System.out.println("Invalid input");
			return false;
		}

		String v1 = pair[0], v2 = pair[1];

		if(!doesExistVertex(v1) || !doesExistVertex(v2)){
			System.out.println("Vertex/vertices does not exists");
			return false;
		}

		Vertex current = vertices;
		Stack<String> stack = new Stack<>();
		boolean pass = false;

		while(current!=null){
			if(v1.equals(current.getName())){
				current.setVisited(true);
				stack.push(current.getName());
				break;
			}
			current = current.getNext();
		}

		while(!stack.isEmpty()){
			String currentVertex = stack.pop();
			Edge currentEdge = edges;

			while(currentEdge!=null){
				Vertex aux = vertices;
				
				if(v2.equals(currentEdge.getComponent1()) 
					|| v2.equals(currentEdge.getComponent2())){
						pass=true;
						break;
				}

				if(currentVertex.equals(currentEdge.getComponent1())){
					while(aux!=null){
						if(currentEdge.getComponent2().equals(aux.getName()) && !aux.getVisited()){
							aux.setVisited(true);
							stack.push(aux.getName());
							break;
						}
						aux = aux.getNext();
					}
				}
				if(currentVertex.equals(currentEdge.getComponent2())){
					while(aux!=null){
						if(currentEdge.getComponent1().equals(aux.getName()) && !aux.getVisited()){
							aux.setVisited(true);
							stack.push(aux.getName());
							break;
						}
						aux = aux.getNext();
					}
				}
				currentEdge = currentEdge.getNext();
			}
			if(pass)
				break;
		}
		current = vertices;
		while(current!=null){
			current.setVisited(false);
			current = current.getNext();
		}

		return pass;
	}

	public void printNeighbours(String vertex){
		Edge auxE = edges;

		if(!doesExistVertex(vertex))
			return;

		while(auxE!=null){
			if(vertex.equals(auxE.getComponent1()))
				System.out.println(auxE.getComponent2()+" ");
			else if(vertex.equals(auxE.getComponent2()))
				System.out.println(auxE.getComponent1()+" ");
			auxE = auxE.getNext();
		}
	}

	public UGraphBL getGraph(){
		return this;
	}

	private boolean doesExistVertex(String v){
		Vertex auxV = vertices;

		while(auxV!=null){

			if(v.equals(auxV.getName()))
				return true;
			auxV = auxV.getNext();
		}
		return false;
	}

	private boolean doesExistEdge(String v1, String v2){
		Edge auxE = edges;

		while(auxE!=null){
			if((v1.equals(auxE.getComponent1()) && v2.equals(auxE.getComponent2())) 
				|| (v2.equals(auxE.getComponent1()) && v1.equals(auxE.getComponent2())))
					return true;
			auxE = auxE.getNext();
		}
		return false;
	}

	private void removeEdgesContaining(String v){
		Edge auxE = edges;
		Edge prev = null;

		while(auxE!=null){

			if(v.equals(auxE.getComponent2()) || v.equals(auxE.getComponent1())){
				if(prev==null)
					edges = edges.getNext();
				else
					prev = auxE.getNext();
				edgeCount--;
			}

			prev = auxE;
			auxE = auxE.getNext();
		}
	}

	private class Vertex{
		Vertex next=null;
		String name="";
		boolean isVisited=false;

		public Vertex(){}

		public Vertex(String name){
			this.name = name;
		}

		public void setNext(Vertex next){
			this.next = next;
		}

		public Vertex getNext(){
			return next;
		}

		public void setVisited(boolean isVisited){
			this.isVisited = isVisited;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public boolean getVisited(){
			return isVisited;
		}
	}

	private class Edge{
		Edge next=null;
		String component1;
		String component2;

		public Edge(){}

		public Edge(String v1, String v2){
			component1 = v1;
			component2 = v2;
		}

		public void setNext(Edge next){
			this.next = next;
		}

		public Edge getNext(){
			return next;
		}

		public void setComponent1(String component1){
			this.component1 = component1;
		}

		public String getComponent1(){
			return component1;
		}

		public void setComponent2(String component2){
			this.component2 = component2;
		}

		public String getComponent2(){
			return component2;
		}

	}
}