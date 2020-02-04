
public class UGraphBL{

	protected Edge edges;
	protected Vertex vertices;
	protected int vertexCount=0, edgeCount=0;
	
	public UGraphBL(){}

	protected void addVertex(String name){
		if(doesExistVertex(name)){
			System.out.println("Vertex already exists!");
			return;
		}

		Vertex v = new Vertex(name);

		if(vertices==null)
			vertices = v;
		else{
			v.setNext(vertices);
			vertices = v;
		}
		vertexCount++;
	}

	protected void addEdge(String v1, String v2){
		if(!(doesExistVertex(v1) && doesExistVertex(v2))){
			System.out.println("One or two of the vertex does not exist!");
			return;
		}

		if(doesExistEdge(v1,v2)){
			System.out.println("Edge already exists!");
			return;
		}

		Edge e = new Edge(v1,v2);

		if(edges==null)
			edges = e;
		else{
			e.setNext(edges);
			edges = e;
		}

		edgeCount++;
	}

	protected void removeVertex(String v){
		Vertex auxV = vertices;
		Vertex prev = null;

		while(auxV!=null){

			if(v.equals(auxV.getName())){
				if(prev==null)
					vertices = vertices.getNext();
				else
					prev.setNext(auxV.getNext());

				removeEdgesContaining(v);
				vertexCount--;
				return;
			}

			prev = auxV;
			auxV = auxV.getNext();
		}
	}

	protected void removeEdge(String v1, String v2){
		Edge auxE = edges;
		Edge prev = null;

		while(auxE!=null){
			if((v1.equals(auxE.getComponent1()) && v2.equals(auxE.getComponent2())) 
				|| (v2.equals(auxE.getComponent1()) && v1.equals(auxE.getComponent2()))){
					if(prev==null)
						edges = edges.getNext();
					else
						prev.setNext(auxE.getNext());
					edgeCount--;
					return;
			}
		prev = auxE;
		auxE = auxE.getNext();
		}
	}

	protected boolean isAdjacent(String v1, String v2){
		return doesExistEdge(v1,v2);
	}

	protected int edgeCount(){
		return edgeCount;
	}

	protected int vertexCount(){
		return vertexCount;
	}

	protected boolean isConnected(String v1, String v2){
		return false;
	}

	protected void listAdjacentVerticesTo(String v){
		Edge auxE = edges;

		if(!doesExistVertex(v))
			return;

		while(auxE!=null){
			if(v.equals(auxE.getComponent1()))
				System.out.println(auxE.getComponent2()+" ");
			else if(v.equals(auxE.getComponent2()))
				System.out.println(auxE.getComponent1()+" ");
			auxE = auxE.getNext();
		}
	}

	private boolean doesExistVertex(String v){
		Vertex auxV = vertices;

		while(auxV!=null){

			if(v.equals(auxV.getName()))
				return true;
			auxV = auxV.getNext();
		}
		return false;
	}

	private boolean doesExistEdge(String v1, String v2){
		Edge auxE = edges;

		while(auxE!=null){
			if((v1.equals(auxE.getComponent1()) && v2.equals(auxE.getComponent2())) 
				|| (v2.equals(auxE.getComponent1()) && v1.equals(auxE.getComponent2())))
					return true;
			auxE = auxE.getNext();
		}

		return false;
	}

	private void removeEdgesContaining(String v){
		Edge auxE = edges;
		Edge prev = null;

		while(auxE!=null){

			if(v.equals(auxE.getComponent2()) || v.equals(auxE.getComponent1())){
				if(prev==null)
					edges = edges.getNext();
				else
					prev = auxE.getNext();
				edgeCount--;
			}

			prev = auxE;
			auxE = auxE.getNext();
		}
	}

	class Vertex{
		Vertex next=null;
		String name;

		public Vertex(){}

		public Vertex(String name){
			this.name = name;
		}

		protected void setNext(Vertex next){
			this.next = next;
		}

		protected Vertex getNext(){
			return next;
		}

		protected void setName(String name){
			this.name = name;
		}

		protected String getName(){
			return name;
		}
	}

	class Edge{
		Edge next=null;
		String component1;
		String component2;

		public Edge(){}

		public Edge(String v1, String v2){
			component1 = v1;
			component2 = v2;
		}

		protected void setNext(Edge next){
			this.next = next;
		}

		protected Edge getNext(){
			return next;
		}

		protected void setComponent1(String component1){
			this.component1 = component1;
		}

		protected String getComponent1(){
			return component1;
		}

		protected void setComponent2(String component2){
			this.component2 = component2;
		}

		protected String getComponent2(){
			return component2;
		}

	}
}