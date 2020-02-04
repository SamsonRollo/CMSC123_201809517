
public class Stack<T>{
	Node<T> first= null;

	public Stack(){}

	public void push(T content){
		if(isEmpty()){
			first = new Node<>(content);
			return;
		}

		Node<T> newNode = new Node<>(content, first);
		first = newNode;
	}

	@SuppressWarnings("unchecked")
	public T pop(){
		if(isEmpty())
			return null;
		T obj = first.getContent();
		first = first.getNext();

		return obj;
	}

	public T peek(){
		return first.getContent();
	}

	public boolean isEmpty(){
		return first==null?true:false;
	}
}