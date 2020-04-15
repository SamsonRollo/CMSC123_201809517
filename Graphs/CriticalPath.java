package Graphs;

import Exceptions.*;
import java.lang.StringBuilder;

public class CriticalPath{
		
	Vertex[] vertexSet;

	public CriticalPath(){}

	//evaluate
	public CPResult findCriticalPath(DGraph g) throws InputNotDAGException, CannotFindGraphException{
		this.vertexSet = g.getVertexSet();

		if(g.numberOfVertices()==0)
			throw new CannotFindGraphException();

		if(isDAG(vertexSet))
			throw new InputNotDAGException();

		CriticalVertexNode[] list = new CriticalVertexNode[vertexSet.length];
		CVNE[] evalV = new CVNE[vertexSet.length];
		Queue<String> queue = new Queue<>();
		boolean[] visited = new boolean[vertexSet.length];

		setCVNEEquivalent(evalV);
		findCandidate(queue, visited, evalV);

		while(!queue.isEmpty()){
			String currentVertex = queue.dequeue();
			int currentVertexID = indexRetriever(currentVertex);
			double currentVertexCT = evalV[currentVertexID].getCriticalTime()+evalV[currentVertexID].getExecTime();

			evalV[currentVertexID].setCriticalTime(currentVertexCT);

			VNode neighbours = vertexSet[currentVertexID].getNeighbour();

			while(neighbours!=null){

				int currentNeighbourID = indexRetriever(neighbours.getVertexName());
				evalV[currentNeighbourID].inDegree--;

				if(evalV[currentNeighbourID].getCriticalTime()<(currentVertexCT)){
					evalV[currentNeighbourID].setPreviousTask(currentVertex);
					evalV[currentNeighbourID].setCriticalTime(currentVertexCT);
				}
				neighbours = neighbours.getVNode();
			}
			findCandidate(queue,visited,evalV);
		}
		//printCritical(evalV);
		return criticalCollection(evalV);
	}


	private void printCritical(CVNE[] evalV){
		for(int i = 0; i<evalV.length; i++){
			System.out.println("Task: "+evalV[i].getName()+" Crit.Time: "
				+evalV[i].getCriticalTime()+" Prev.Task: "
				+evalV[i].previousTask);
		}
	}

	private CPResult criticalCollection(CVNE[] evalV){
		int greatest = 0;
		for(int i = 0; i<evalV.length; i++){
			if(evalV[i].getCriticalTime()>evalV[greatest].getCriticalTime())
				greatest = i;
		}

		double criticalTime = evalV[greatest].getCriticalTime();
		String path = evalV[greatest].getName();
		while(true){
			if((evalV[greatest].previousTask)==null)
				break;
			for(int i=0; i<evalV.length; i++){
				if((evalV[greatest].previousTask).equals(evalV[i].getName())){
					path+=" ";
					path+=evalV[i].getName();
					greatest = i;
					break;
				}
			}
		}
		StringBuilder sb = new StringBuilder(path);
		path = (sb = sb.reverse()).toString();
		return new CPResult(path, criticalTime);
	}

	private void setCVNEEquivalent(CVNE[] evalV){
		for(int i =0; i<vertexSet.length; i++){
			evalV[i] = new CVNE(vertexSet[i].getVertexName(), 
				vertexSet[i].getExecTime(), 
				vertexSet[i].getInDegree()); 
		}
	}

	private CriticalVertexNode[] setCVNEquivalent(CVNE[] evalV, CriticalVertexNode[] list){

		for(int i=0; i<evalV.length; i++){
			list[i] = new CriticalVertexNode();
			list[i].setVertexName(evalV[i].getName());
			list[i].setCriticalTime(evalV[i].getCriticalTime());
		}
		return list;
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

	private void findCandidate(Queue<String> queue, boolean[] visited, CVNE[] evalV){
		for(int i=0; i<evalV.length; i++){
			if(!visited[i] && evalV[i].inDegree==0){
				visited[i] = true;
				queue.enqueue(nameRetriever(i));
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

	//new lab shortest path w eT and weight

	private void findCandidate(Stack<String> stack, boolean[] visited, String currentVertex){
		int currentIndex = indexRetriever(currentVertex);
		visited[currentIndex] = true;

		VNode neighbours = vertexSet[currentIndex].getNeighbour();

		while(neighbours!=null){
			if(!visited[indexRetriever(neighbours.getVertexName())])
				findCandidate(stack, visited, neighbours.getVertexName());

			neighbours = neighbours.getVNode();
		}
		stack.push(nameRetriever(currentIndex));
	}

	private void findCandidate(Vertex[] v, Queue<Integer> candidateQueue){
		for(int i=0; i<v.length; i++)
			if(v[i].getInDegree()==0)
				candidateQueue.enqueue(i);
	}

	//add previous task
	public CPResult leastCriticalPath(DGraph g) throws InputNotDAGException, CannotFindGraphException{
		this.vertexSet = g.getVertexSet();

		if(g.numberOfVertices()==0)
			throw new CannotFindGraphException();

		if(isDAG(vertexSet))
			throw new InputNotDAGException();

		Stack<String> stack = new Stack<>();
		boolean[] visited = new boolean[vertexSet.length];
		double[] criticalTime = new double[vertexSet.length];
		String[] previousTask = new String[vertexSet.length];

		for(int i = 0; i<vertexSet.length; i++){
			previousTask[i] = null;
			criticalTime[i] = Double.POSITIVE_INFINITY;
		}

		Queue<Integer> candidateQueue = new Queue<>();
		findCandidate(vertexSet, candidateQueue);

		while(!candidateQueue.isEmpty()){
			criticalTime[candidateQueue.peek()] = vertexSet[candidateQueue.dequeue()].getExecTime();
		}

		for(int i = 0; i<vertexSet.length; i++){
			if(!visited[i])
				findCandidate(stack, visited, nameRetriever(i));
		}

		while(!stack.isEmpty()){
			String currentEvaluatedVertex = stack.pop();
			int currentEvaluatedIndex = indexRetriever(currentEvaluatedVertex);

			if(criticalTime[currentEvaluatedIndex]!=Double.POSITIVE_INFINITY){
				VNode neighbours = vertexSet[currentEvaluatedIndex].getNeighbour();

				while(neighbours!=null){
					int currentNeighbourID = indexRetriever(neighbours.getVertexName());

					if((criticalTime[currentNeighbourID] < criticalTime[currentEvaluatedIndex]+vertexSet[currentEvaluatedIndex].getExecTime()+neighbours.getWeight())||criticalTime[currentNeighbourID]==Double.POSITIVE_INFINITY){
						criticalTime[currentNeighbourID] = criticalTime[currentEvaluatedIndex]+vertexSet[currentEvaluatedIndex].getExecTime()+neighbours.getWeight();
						previousTask[currentNeighbourID] = currentEvaluatedVertex;
					}

					neighbours = neighbours.getVNode();
				}
			}
		}

		//test print
		// for(int i=0; i<vertexSet.length; i++)
		// 	System.out.println("Vertex: "+vertexSet[i].getVertexName()+" Critical Time: "+criticalTime[i]+" previous Task: "+previousTask[i]);

		CVNE[] cvne = new CVNE[vertexSet.length];
		for(int i=0; i<vertexSet.length; i++)
			cvne[i] = new CVNE(vertexSet[i].getVertexName(), criticalTime[i], previousTask[i]);

		return criticalCollection(cvne);
	}

	class CVNE{
		String name = null;
		String previousTask = null;
		double executionTime = 0.0;
		double criticalTime = 0.0;
		int inDegree = 0;

		public CVNE(){}

		public CVNE(String name, double executionTime, int inDegree){
			this.name = name;
			this.executionTime = executionTime;
			this.inDegree = inDegree;
		}

		public CVNE(String name, double criticalTime, String previousTask){
			this.name = name;
			this.criticalTime = criticalTime;
			this.previousTask = previousTask;
		}

		public void setCriticalTime(double ct){
			criticalTime = ct;
		}

		public void setPreviousTask(String previousTask){
			this.previousTask = previousTask;
		}

		public double getCriticalTime(){
			return criticalTime;
		}

		public double getExecTime(){
			return executionTime;
		}

		public String getName(){
			return name;
		}

	}
}