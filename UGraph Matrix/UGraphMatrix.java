
public class UGraphMatrix{
			
	boolean[][] relationTable;
	String[] vertices;
	int  vertexCount=0, edgeCount=0, size=0;

	public UGraphMatrix(int size){
		relationTable = new boolean[size][size];
		vertices = new String[size];
		this.size = size;
	}

	protected void addVertex(String name){
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

	protected void addEdge(String v1, String v2){
		int v1Index = vertexPosition(v1);
		int v2Index = vertexPosition(v2);

		if(v1Index==-1 || v2Index==-1)
			return;

		relationTable[v1Index][v2Index] = true;
		relationTable[v2Index][v1Index] = true;

		edgeCount++;
	}

	protected void removeVertex(String name){
		int vIndex = vertexPosition(name);

		if(vIndex==-1)
			return;

		shiftDown(vIndex);
		vertexCount--;
	}

	protected void removeEdge(String v1, String v2){
		int v1Index = vertexPosition(v1);
		int v2Index = vertexPosition(v2);

		if(v1Index==-1 || v2Index==-1)
			return;

		relationTable[v1Index][v2Index] = false;
		relationTable[v2Index][v1Index] = false;

		edgeCount--;
	}

	protected int isAdjacent(String v1, String v2){
		int v1Index = vertexPosition(v1);
		int v2Index = vertexPosition(v2);

		if(v1Index==-1 || v2Index==-1)
			return -1;
		else if(relationTable[v1Index][v2Index])
				return 1;
		else
			return 0;
	}

	protected void adjacentVertices(String name){
		int index = vertexPosition(name);

		if(index==-1)
			return;
		for(int i=0; i<vertexCount; i++){
			if(relationTable[i][index])
				System.out.print(vertices[i]+" ");
		}
	}

	protected int getVertices(){
		return vertexCount;
	}

	protected int getEdges(){
		return edgeCount;
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

}