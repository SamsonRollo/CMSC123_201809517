package Algorithms;

import Exceptions.*;
import Graphs.*;
import java.util.PriorityQueue;
import java.util.Arrays;

public class Dijkstra{
	
	Vertex[] vertexSet;
	private String track;

	public Dijkstra(){}

	public void findPath(DGraph g, String initialVertex) throws NegativeEdgeException, CannotFindGraphException, InvalidVertexException{
		this.vertexSet = g.getVertexSet();

		if(g.numberOfVertices()==0)
			throw new CannotFindGraphException();
		String check = checkNegativeVertex();
		if(!check.equals("null"))
			throw new NegativeEdgeException(check);
		if(indexRetriever(initialVertex)==-1)
			throw new InvalidVertexException(initialVertex,1);

		// VNode[] vertices = new VNode[vertexSet.length];

		// for(int i = 0; i<vertices.length; i++)
		// 	vertices[i] = new VNode(nameRetriever(i), Double.POSITIVE_INFINITY);

		// vertices[indexRetriever(initialVertex)].setWeight(0);

		// PriorityQueue<VNode> pqueue = new PriorityQueue<>();

		// pqueue.add(vertices[indexRetriever(initialVertex)]);

		boolean[] visited = new boolean[vertexSet.length];
		double[] distance = new double[vertexSet.length];
		String[] prev = new String[vertexSet.length];
		Queue<String> queue = new Queue<>();

		for(int i = 0; i<vertexSet.length; i++)
			distance[i] = Double.POSITIVE_INFINITY;
		distance[indexRetriever(initialVertex)] = 0;
		queue.enqueue(initialVertex);

		//findPath(vertices, pqueue, initialVertex);
		findPath(visited,distance, prev, queue);

		print(distance, prev, initialVertex);
	}

	private void findPath(boolean[] visited, double[] distance, String[] prev, Queue<String> queue){
		while(!queue.isEmpty()){
			String cur = queue.dequeue();
			int indexCur = indexRetriever(cur);
			visited[indexCur] = true;

			VNode neighbours = vertexSet[indexCur].getNeighbour();

			while(neighbours!=null){
				double tempDist = distance[indexCur]+neighbours.getWeight();
				int neighcurIndex = indexRetriever(neighbours.getVertexName());
				
				if(tempDist<distance[neighcurIndex]){
					distance[neighcurIndex] = tempDist;
					prev[neighcurIndex] = cur;
					queue.enqueue(neighbours.getVertexName());
				}

				neighbours = neighbours.getVNode();
			}
		}
	}

	private void print(double[] vertices, String[] prev, String tSource){
		System.out.println("Vertex\t\tDistance\tPath");
		for(int i=0; i<vertices.length; i++){
			if(vertices[i]==Double.POSITIVE_INFINITY)
				track = "";
			else
				track = nameRetriever(i);
			backtrack(prev, tSource, prev[i]);
			System.out.println(nameRetriever(i)+"\t\t"+vertices[i]+"\t\t"+track);
		}
	}

	private void backtrack(String[] prev, String tSource, String source){
		
		if(source==null){
			return;
		}

		track+="-";
		if(source.equals(tSource)){
			track+=source;
			return;
		}
		else{
			track+=source;
			backtrack(prev, tSource, prev[indexRetriever(source)]);
		}
	}

	private String checkNegativeVertex(){
		String out = "null";
		for(int i = 0; i<vertexSet.length; i++){
			VNode aux = vertexSet[i].getNeighbour();

			while(aux!=null){
				if(aux.getWeight()<0)
					return nameRetriever(i)+" and "+aux.getVertexName();
				aux = aux.getVNode();
			}
		}
		return out;
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
}