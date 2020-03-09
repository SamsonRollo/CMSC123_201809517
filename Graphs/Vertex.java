package Graphs;

public class Vertex{

	VNode neighbours = null;
	String name=null;
	int outDegree=0, inDegree=0;
	double executionTime=0.0;

	public Vertex(){}

	public Vertex(String name){
		this.name = name;
	}

	public boolean addNeighbour(String neighbourName, double weight){ //edit adding neighbours to the end
		//check if neighbor is valid and already exists
		if(neighbours==null){
			neighbours = new VNode(neighbourName, null, weight);
			return false;
		}

		VNode auxVNode = neighbours;

		while(auxVNode!=null){
			if(auxVNode.getVertexName()==neighbourName)
				return true;
			auxVNode = auxVNode.getVNode();
		}
		
		VNode newVNode = new VNode(neighbourName, null, weight);
		arrangeNeighbours(newVNode);

		return false;
	}

	public void setVertexName(String name){
		this.name = name;
	}

	public void setNeighbour(VNode neighbours){
		this.neighbours = neighbours;
	}

	public void setExecTime(double d){
		executionTime = d;
	}

	public String getVertexName(){
		return name;
	}

	public int getInDegree(){
		return inDegree;
	}

	public int getOutDegree(){
		return outDegree;
	}

	public double getExecTime(){
		return executionTime;
	}

	public VNode getNeighbour(){
		return neighbours;
	}

	private void arrangeNeighbours(VNode newVNode){//remake fast sort algo
		VNode auxVNode = neighbours;
		VNode prev = null;

		if(newVNode.getWeight()==0){
			newVNode.setVNode(neighbours);
			neighbours = newVNode;
			return;
		}

		while(auxVNode!=null){
			if(newVNode.getWeight()<auxVNode.getWeight()){
				if(prev==null){
					newVNode.setVNode(neighbours);
					neighbours = newVNode;
					return;
				}
				newVNode.setVNode(auxVNode);
				prev.setVNode(newVNode);
				return;
			}
			prev = auxVNode;
			auxVNode = auxVNode.getVNode();
		}
		prev.setVNode(newVNode); //capture
	}
}