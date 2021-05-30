package Algorithms.Greedy;


import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StringBuilder;
import UI.CatalogItem;
import Algorithms.AlgorithmItem;

public class Dijkstra implements AlgorithmItem{
	
	private Vertex[] vertexSet; 
	private String track, start;
	private boolean[] visited;
	private double[] distance = new double[8];
	private CatalogItem ci;
	private Loader loader;

	public Dijkstra(CatalogItem ci){
		this.ci = ci;
		this.start = start;
		loadNeededInputs();
		ci.refresh();
	}

	public void reset(){
		loadNeededInputs();
	}

	private void loadNeededInputs(){
		for(int i = 0; i<distance.length; i++)
			distance[i] = 0.0;
		loader = new Loader();
		vertexSet = loader.getVertexSet();
	}

	public void run(){
		boolean match = false;
		for(Vertex v : vertexSet){
			if(v.getVertexName().equals(ci.getGreedInput())){
				match = true;
				break;
			}
		}

		if(match)
			findPath(ci.getGreedInput());
		else{
			ci.popMessage("Invalid Source Vertex", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}
		ci.popMessage("Algorithm Done", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	public void findPath(String start){
		visited = new boolean[vertexSet.length];
		distance = new double[vertexSet.length];
		String[] prev = new String[vertexSet.length];
		Queue<String> queue = new Queue<>();
		int reservedInDegree = 0;

		for(int i = 0; i<vertexSet.length; i++)
			distance[i] = Double.POSITIVE_INFINITY;

		distance[indexRetriever(start)] = 0;
		queue.enqueue(start);
		findPath(visited,distance, prev, queue);
		
		String[] tracks = print(distance, prev, start);
		String[][] outputs;

		outputs = new String[vertexSet.length][3];
		for(int i=0; i<vertexSet.length; i++){
			outputs[i][0] = vertexSet[i].getVertexName();
			outputs[i][1] = String.valueOf(distance[i]);
			outputs[i][2] = tracks[i]; 
		}
	}

	private void findPath(boolean[] visited, double[] distance, String[] prev, Queue<String> queue){

		while(!queue.isEmpty()){
			String cur = queue.dequeue();
			loader.getVertices().get(loader.indexRetriever(cur)).setActivity("active");
			visualSleep();

			int indexCur = indexRetriever(cur);
			visited[indexCur] = true;

			VNode neighbours = vertexSet[indexCur].getNeighbour();

			while(neighbours!=null){
				double tempDist = distance[indexCur]+neighbours.getWeight();
				int neighcurIndex = indexRetriever(neighbours.getVertexName());
				
				int i = 0;
				int j = 0;
				String tempAct = loader.getVertices().get(neighcurIndex).getActivity();
				loader.getVertices().get(neighcurIndex).setActivity("check");

				i = loader.edgeIndex(cur+","+neighbours.getVertexName());
				j = loader.edgeIndex(neighbours.getVertexName()+","+cur);
				if(i!=-1 && j!=-1){	
					loader.getEdges().get(j).setActivity("active"); 
					loader.getEdges().get(i).setActivity("active");
				}
				visualSleep();
				if(tempDist<distance[neighcurIndex]){
					distance[neighcurIndex] = tempDist;
					prev[neighcurIndex] = cur;
					queue.enqueue(neighbours.getVertexName());
				}
				loader.getEdges().get(i).setActivity("done");
				loader.getEdges().get(j).setActivity("done");
				loader.getVertices().get(neighcurIndex).setActivity(tempAct);
				neighbours = neighbours.getVNode();
				visualSleep();
			}
			loader.getVertices().get(loader.indexRetriever(cur)).setActivity("done");
			visualSleep();//vertex evaluation done
		}
	}

	private String[] print(double[] vertices, String[] prev, String tSource){
		String[] tracks = new String[vertices.length];
		//System.out.println("Vertex\t\tDistance\tPath");
		
		for(int i=0; i<vertices.length; i++){
			if(vertices[i]==Double.POSITIVE_INFINITY)
				track = "";
			else
				track = nameRetriever(i);
			backtrack(prev, tSource, prev[i]);
			//System.out.println(nameRetriever(i)+"\t\t"+vertices[i]+"\t\t"+track);
			tracks[i] = track; 
		}
		
		for(int i = 0; i<tracks.length; i++){
			String[] revTrack = tracks[i].split("-");
			String s = revTrack[revTrack.length-1];
			for(int j = revTrack.length-2; j>=0; j--)
				s +="-"+revTrack[j];
			tracks[i]=s;
		}

		return tracks;
	}

	private void backtrack(String[] prev, String tSource, String source){
		if(source==null)
			return;
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

	private void visualSleep(){
		try{
			ci.refresh();
			Thread.sleep(ci.getSleepTime());	
		}catch(InterruptedException ie){};
	}

	public boolean[] getVisited(){
		return this.visited;
	}

	public double[] getDistances(){
		return distance;
	}

	public Loader getLoader(){
		return loader;
	}
}
