package Graphs;

public class CPResult{

	String path = null;
	double time = 0.0;

	public CPResult(){}

	public CPResult(String path, double time){
		this.path = path;
		this.time = time;
	}

	public void setCriticalPath(String path){
		this.path = path;
	}

	public void setCriticalTime(Double time){
		this.time = time;
	}

	public String getCriticalPath(){
		return path;
	}

	public double getCriticalTime(){
		return time;
	}
}