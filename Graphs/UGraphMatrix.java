package Graphs;

import java.util.Arrays;

public class UGraphMatrix implements Graph{
			
	boolean[][] relationTable;
	String[] vertices;
	int  vertexCount=0, edgeCount=0, size=0;

	public UGraphMatrix(int size){
		relationTable = new boolean[size][size];
		vertices = new String[size];
		this.size = size;
	}

	public void addVertex(String name){
		String[] verticesName = name.split(" ");

		for(int i=0; i<verticesName.length; i++){
			addV(verticesName[i]);
		}
	}

	private void addV(String name){
		if(vertexPosition(name)!=-1)
			return;

		if(vertexCount+1>size){
			boolean[][] newTable = new boolean[size+2][size+2];
			String[] newVertices = new String[size+2];

			for(int i=0; i<vertexCount; i++){
				newVertices[i] = vertices[i]; 
				for(int j = 0; j<vertexCount; j++){
					newTable[i][j] = relationTable[i][j];
				}
			}
			size+=2;
			newVertices[vertexCount] = name;
			vertices = newVertices;
			relationTable = newTable;
		}

		vertices[vertexCount] = name;

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
		int v1Index = vertexPosition(v1);
		int v2Index = vertexPosition(v2);

		if(v1Index==-1 || v2Index==-1)
			return;

		relationTable[v1Index][v2Index] = true;
		relationTable[v2Index][v1Index] = true;

		edgeCount++;
	}

	public void removeVertex(String vertex){
		int vIndex = vertexPosition(vertex);

		if(vIndex==-1)
			return;

		shiftDown(vIndex);
		vertexCount--;
	}

	public void removeEdge(String edge){
		String[] edgePair = edge.split(",");

		if(edgePair.length!=2){
			System.out.println("Invalid edge!");
			return;
		}

		int v1Index = vertexPosition(edgePair[0]);
		int v2Index = vertexPosition(edgePair[1]);

		if(v1Index==-1 || v2Index==-1){
			System.out.println("Invalid input");
			return;
		}

		if(v1Index==-1 || v2Index==-1)
			return;

		relationTable[v1Index][v2Index] = false;
		relationTable[v2Index][v1Index] = false;

		edgeCount--;
	}

	public int isAdjacent(String edge){
		String[] pair = edge.split(",");

		if(pair.length!=2)
			return -1;

		int v1Index = vertexPosition(pair[0]);
		int v2Index = vertexPosition(pair[1]);

		if(v1Index==-1 || v2Index==-1)
			return -1;
		else if(relationTable[v1Index][v2Index])
				return 1;
		else
			return 0;
	}

	public void printNeighbours(String vertex){
		int index = vertexPosition(vertex);

		if(index==-1)
			return;
		for(int i=0; i<vertexCount; i++){
			if(relationTable[i][index])
				System.out.print(vertices[i]+" ");
		}
	}

	public int numberOfVertices(){
		return vertexCount;
	}

	public int numberOfEdges(){
		return edgeCount;
	}

	public boolean isConnected(String edge){
		String[] pair = edge.split(",");
		if(pair.length!=2)
			return false;

		return isConnected(vertexPosition(pair[0]), vertexPosition(pair[1]));
	}

	private int vertexPosition(String name){
		for(int i=0; i<vertices.length; i++){
			if(name.equals(vertices[i]))
				return i;
		}
		return -1;
	}

	private void shiftDown(int index){
		String[] auxv = new String[size];
		boolean[][] auxe = new boolean[size][size];
		int edge=0;

		for(int i = 0, j=0; i<vertexCount; i++, j++){
			if(i==index){
				j++;
				continue;
			}
			auxv[i] = vertices[j];
		}
		vertices = auxv;

		for(int i=0; i<vertexCount; i++){
			if(relationTable[i][index])
				edge++;
		}

		for(int i=0, j=0; i<vertexCount; i++,j++){
			if(i==index){
				j++;
				continue;
			}

			for(int k=0, l=0; k<vertexCount; k++,l++){
				if(k==index){
					l++;
					continue;
				}
				auxe[i][k] = relationTable[j][l];
			}
		}
		relationTable = auxe;
		edgeCount-=edge;
	}

	private boolean isConnected(int v1, int v2){
		Stack<Integer> stack = new Stack<>();
		boolean[] isVisited = new boolean[vertexCount];

		if(v1==-1 || v2==-1)
			return false;

		if(relationTable[v1][v2])
			return true;

		stack.push(v1);

		while(!stack.isEmpty()){
			int cur = stack.pop();
			isVisited[cur] = true;

			for(int i=0; i<vertexCount; i++){
				if(relationTable[cur][i] && i==v2)
					return true;
				if(relationTable[cur][i] && !isVisited[i])
					stack.push(i);
			}
		}

		return false;
	}

}