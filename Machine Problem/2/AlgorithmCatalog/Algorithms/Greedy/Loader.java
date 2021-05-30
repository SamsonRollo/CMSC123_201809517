package Algorithms.Greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;

public class Loader{

	public ArrayList<Edge> edgeList;
	public ArrayList<Vertex> graphAL;
	public int vertices = 0, edges = 0;
	private String[] inputs;

	public Loader(){
		readGraph();
	}

	public void readGraph(){
		graphAL = new ArrayList<Vertex>();
		edgeList = new ArrayList<Edge>();
		
		boolean v = false;
		String vertices = "V0:0.0:207:120/V1:0.0:126:193/V2:0.0:291:243/V3:0.0:197:300/V4:0.0:315:150/V5:0.0:90:283/V6:0.0:360:57/V7:0.0:390:208";
		String edges ="V1,V5,1.0/V5,V1,1.0/V3,V5,1.0/V5,V3,1.0/V0,V3,1.0/V3,V0,1.0/V0,V1,1.0/V1,V0,1.0/V5,V0,1.0/V0,V5,1.0/V2,V0,1.0/V0,V2,1.0/V7,V0,1.0/V0,V7,1.0/V6,V4,1.0/V4,V6,1.0/V7,V4,1.0/V4,V7,1.0/V7,V6,1.0/V6,V7,1.0/V3,V4,1.0/V4,V3,1.0";

		inputs = vertices.split("/");
		for(String vertex : inputs)
			addVertex(vertex);
		inputs = edges.split("/");
		for(String edge : inputs)
				addEdge(edge);
	}

	public void addVertex(String name){ //used when reading from a file
		String[] verticesName = name.split("\n");
		double executionTime = 0.0;
		String vertexName = null;
		int xPos = 0, yPos = 0;

		for(int i=0; i<verticesName.length; i++){
			vertexName = verticesName[i];
			if(verticesName[i].contains(":")){
				String[] vertexComponent = verticesName[i].split(":");// asume 4 elements, name:exec:xPos:yPos
				vertexName = vertexComponent[0];
				try{
					executionTime = Double.parseDouble(vertexComponent[1]);
				}catch(NumberFormatException n){System.out.println("Illegal execution time input at vertex "+vertexName);continue;};
				try{
					xPos = Integer.parseInt(vertexComponent[2]);
					yPos = Integer.parseInt(vertexComponent[3]);
				}catch(NumberFormatException n){System.out.println("Illegal coordinate input at vertex "+vertexName);continue;};
			}	
			addV(vertexName,executionTime,xPos,yPos);
		}
	}

	private void addV(String name, double time, int xPos, int yPos){ //general function for adding vertex
		graphAL.add(new Vertex(xPos, yPos, name, time)); //add exec time soon	
		vertices++;
	}

	public void addEdge(String edge){ //used in adding thru file
		String[] edgesName = edge.split("\n");
		String[] edgeSet;
		String weight;

		for(int i=0; i<edgesName.length; i++){
			edgeSet = edgesName[i].split(",");

			try{
				if(edgeSet[2]==null)
					weight = "1";
				else
					weight = edgeSet[2];
			}catch(Exception f){weight = "1";};

			addE(edgeSet[0], edgeSet[1], weight); //modify later that will copy from
		}
	}

	private void addE(String v1,String v2, String weight){ //general edge adder
		int index1 = indexRetriever(v1);
		int index2 = indexRetriever(v2);
		double weightInt =1;

		boolean eV1 = graphAL.get(index1).addNeighbour(v2,weightInt);
	
		if(eV1==false){
			graphAL.get(index2).addInDegree(); //adding indegree to v2 since this is directed
			if(!v1.equals(v2)){ // undirected graphs
				graphAL.get(index2).addNeighbour(v1,weightInt);
				graphAL.get(index1).addInDegree();	
				edgeList.add(new Edge(v2, v1, graphAL.get(index2).getStartX(), graphAL.get(index2).getStartY(), graphAL.get(index1).getStartX(), graphAL.get(index1).getStartY(), false));
				edgeList.add(new Edge(v1, v2, graphAL.get(index1).getStartX(), graphAL.get(index1).getStartY(), graphAL.get(index2).getStartX(), graphAL.get(index2).getStartY(), false));
			}
			edges++;
		}
	}

	public int indexRetriever(String name){
		for(int i=0; i<graphAL.size(); i++){
			if(name.equals(graphAL.get(i).getVertexName()))
				return i;
		}
		return -1;
	}

	public ArrayList<Edge> getEdges(){
		return edgeList;
	}

	public ArrayList<Vertex> getVertices(){
		return graphAL;
	}

	public Vertex[] getVertexSet(){
		return graphAL.toArray(new Vertex[graphAL.size()]);
	}

	public int edgeIndex(String name){
		for(Edge e: edgeList)
			if((e.getUniqueID()).equals(name))
				return edgeList.indexOf(e);

		return -1;
	}

	public void resetActivity(){
		for(Vertex v: graphAL)
			v.setActivity("inactive");
		for(Edge e: edgeList)
			e.setActivity("inactive");
	}
}