package Graphs;

public class CriticalVertexNode{

	String vertexName = null;
	double criticalTime = 0.0;
	
	public CriticalVertexNode(){}

	public void setVertexName(String vertexName){
		this.vertexName = vertexName;
	}

	public void setCriticalTime(double criticalTime){
		this.criticalTime = criticalTime;
	}

	public String getVertexName(){
		return vertexName;
	}

	public double getCriticalTime(){
		return criticalTime;
	}
}