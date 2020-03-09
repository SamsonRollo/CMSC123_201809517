package Graphs;

import Exceptions.*;

public class TopologicalSort{

	private Vertex[] vertexSet;
	private int size = 1, ctr = 0;
	
	public TopologicalSort(){}

	public String[] sort(DGraph g) throws InputNotDAGException, CannotFindGraphException{
		this.vertexSet = g.getVertexSet();

		if(g.numberOfVertices()==0)
			throw new CannotFindGraphException("Cannot Find Graph!");

		if(isDAG(vertexSet))
			throw new InputNotDAGException("Graph not DAG!");

		Stack<String> stack = new Stack<>();
		String[] list = new String[vertexSet.length];
		boolean[] visited = new boolean[vertexSet.length];
		int[] inDegree = new int[vertexSet.length];

		return sort(stack, list, visited, inDegree, 0);
	}

	public String[] getAllTopologicalSort(DGraph g) throws InputNotDAGException, CannotFindGraphException{
		this.vertexSet = g.getVertexSet();

		if(g.numberOfVertices()==0)
			throw new CannotFindGraphException("Cannot Find Graph!");

		if(isDAG(vertexSet))
			throw new InputNotDAGException("Graph not DAG!");

		Stack<String> stack = new Stack<>();
		boolean[] visited = new boolean[vertexSet.length];
		int[] inDegree = new int[vertexSet.length];
		String[] list = new String[vertexSet.length];
		size = vertexSet.length;
		ctr = 0;

		for(int i = 0; i<vertexSet.length; i++)
			inDegree[i] = vertexSet[i].getInDegree();		

		sortC(stack, visited, inDegree, list);

		return list;
	}

	private String[] sort(Stack<String> stack, String[] list, boolean[] visited, int[] inDegree, int ctr){
		for(int i = 0; i<vertexSet.length; i++)
			inDegree[i] = vertexSet[i].getInDegree();

		findCandidate(stack, visited, inDegree);

		while(!stack.isEmpty()){
			String current = stack.pop();
			list[ctr++] = current;

			VNode currentNeighbour = vertexSet[indexRetriever(current)].getNeighbour();

			while(currentNeighbour!=null){
				inDegree[indexRetriever(currentNeighbour.getVertexName())]--;
				currentNeighbour = currentNeighbour.getVNode();
			}
			findCandidate(stack, visited, inDegree);
		}	
		return list;
	}

	private void sortC(Stack<String> stack, boolean[] visited, int[] inDegree, String[] list){
		for(int i = 0; i<vertexSet.length; i++){

			if(!visited[i] && inDegree[i]==0){
				VNode neighbours = vertexSet[i].getNeighbour();

				while(neighbours!=null){
					int currentNeighbourIndex = indexRetriever(neighbours.getVertexName());
					inDegree[currentNeighbourIndex]--;
					neighbours = neighbours.getVNode();
				}

				stack.push(nameRetriever(i));
				visited[i] = true;

				sortC(stack, visited, inDegree, list);

				neighbours = vertexSet[i].getNeighbour();
				//System.out.println("\n");

				while(neighbours!=null){
					int currentNeighbourIndex = indexRetriever(neighbours.getVertexName());
					inDegree[currentNeighbourIndex]++;
				//	System.out.println(neighbours.getVertexName()+" inDegree "+inDegree[currentNeighbourIndex]);
					neighbours = neighbours.getVNode();
				}

				stack.pop();
				visited[i] = false;
			}
		}
		if(stack.getSize()==vertexSet.length){
			collectionCompiler(stack, list);
		}	
	}

	@SuppressWarnings("unchecked")
	private void collectionCompiler(Stack<String> stack, String[] list){
		Node<String> aux = stack.getFirstCopy();
		Stack<String> rev = new Stack<>();
		String sequence = "[";

		while(aux!=null){
			rev.push(aux.getContent());
			aux = aux.getNext();
		}

		while(!rev.isEmpty()){
			sequence+=rev.pop();
			if(rev.getSize()!=0)
				sequence+=",";
		}
		sequence+="]";

		if(ctr+1>size){
			String[] newList = new String[++size];

			for(int i=0; i<newList.length-1; i++){
				newList[i] = list[i];
			}
			newList[size-1] = sequence;
			list = newList;
		}
		else
			list[ctr] = sequence;

		ctr++;
	}

	private void findCandidate(Stack<String> stack, boolean[] visited, int[] inDegree){
		for(int i=0; i<inDegree.length; i++){
			if(!visited[i] && inDegree[i]==0){
				visited[i] = true;
				stack.push(nameRetriever(i));
			}
		}
	}

	private int indexRetriever(String name){
		for(int i=0; i<vertexSet.length; i++){
			if(name.equals(vertexSet[i].getVertexName()))
				return i;
		}
		return -1;
	}

	private String nameRetriever(int index){
		return vertexSet[index].getVertexName();
	}

	private boolean isDAG(Vertex[] vertexSet){
		for(int i=0; i<vertexSet.length; i++){
			if(isDAGIF(i, vertexSet))
				return true;
		}
		return false;
	}

	private boolean isDAGIF(int v, Vertex[] vertexSet){
		boolean[] isVisited = new boolean[vertexSet.length];
		Stack<Integer> stack = new Stack<>();
		stack.push(v);

		while(!stack.isEmpty()){
			VNode neighbours = vertexSet[stack.peek()].getNeighbour();
			isVisited[stack.pop()]=true;

			while(neighbours!=null){
				int testIndex = indexRetriever(neighbours.getVertexName());

				if(testIndex==v)
					return true;

				if(!isVisited[testIndex])
					stack.push(testIndex);

				neighbours = neighbours.getVNode();
			}
		}
		return false;
	}
}