import java.util.Iterator;

public class LinkedList<T> implements ListADT<T>, Iterable<T> {
	private Node<T> head;
	private Node<T> tail;
	private int size;

       // Constructor 
	
	public LinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	private Node<T> getHead() {
		return this.head;
	}
	
	private void setHead(Node<T> head) {
		this.head = head;
	}
	
	private Node<T> getTail() {
		return this.tail;
	}
	
	private void setTail(Node<T> tail) {
		this.tail = tail;
	}

	/**
	 * prints out a representation of the list using sample output as a guide
	 */
	public void print() {
		//System.out.println("CS 300 Breakout Rooms: ");
		System.out.print("Size: " + this.size + " Contents: ");
		
		Node <T> current = head;
		T output = null;
		
		while (current != null) {
			output = current.getData();
			System.out.print(output + ", ");
			current = current.getNext();
		}
		System.out.println();
	}

	@Override
	public void addAtEnd(T element) {
		Node <T> addedNode = new Node<T>(element);
		
		//if empty LinkedList, set both head and tail to new node
		if (size == 0) {
			setHead(addedNode);
			setTail(addedNode);
		}
		
		else {
			tail.setNext(addedNode); //set next node of tail to new node
			setTail(addedNode); //set tail to new node
		}
		
		size++;
	}

	@Override
	public void add(int index, T element) throws IndexOutOfBoundsException {
		
		//throw exception if index is invalid
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Invalid index!");
		}
		
		//create node to add
		Node<T> addedNode = new Node<T>(element);
		
		//current LinkedList is empty
		if (size == 0) {
			setHead(addedNode);
			setTail(addedNode);
		}
		
		//adding new head
		else if (index == 0) {
			Node<T> headNode = getHead(); //get current head
			addedNode.setNext(headNode); //set next of current node to head 
			setHead(addedNode); //set head
		}
		
		//adding new tail
		else if (index == size) {
			Node<T> tailNode = getTail();
			tailNode.setNext(addedNode);
		}
		
		//adding at index != 0 and not head nor tail
		else {
			// keep count of current position
			int count = 0;
			
			// start at head
			Node<T> current = head;
			
			// find current index position
			while (count < index - 1) {
				current = current.getNext();
				count++;
			}
			
			// add node to index position and reset previous node's next value
			addedNode.setNext(current.getNext());
			current.setNext(addedNode);
		}
		
		size++;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public T remove(int index) throws IndexOutOfBoundsException {
		
		//throw exception if index is invalid
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index!");
		}
		
		T removedElement = null;
		
		if (size == 1) {
			removedElement = head.getData();
			setHead(null);
			setTail(null);
		}
		
		else if (index == 0) {
			removedElement = head.getData();
			setHead(head.getNext());
		}
		
		else {
			Node <T> current = head; //start loop at head
			int count = 0; //start count of position
			
			//get position right before index to be removed
			while (count < index - 1) {
				current = current.getNext();
				count++;
			}
			
			Node <T> previousNode = current; //store previous node 
			removedElement = previousNode.getNext().getData(); //get data to return
			previousNode.setNext(previousNode.getNext().getNext()); //set previous node next value to the removed node's next value
		}
		
		size--;
		
		return removedElement;
	}

	@Override
	public T removeFromFront() {
		
		T removedElement = null; // stores element at the front
		
		// if LinkedList length is 1, reset both head and tail
		if (size == 1) {
			removedElement = head.getData();
			setHead(null);
			setTail(null);
		}
		
		// if LinkedList length is more than 1, only reset head
		else if (size > 1) {
			removedElement = head.getData();
			setHead(head.getNext());
		}
		
		size--;
		
		return removedElement;
	}

	@Override
	public T get(int index) throws IndexOutOfBoundsException {
		
		//throw exception if index is invalid
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index!");
		}
		
		Node<T> current = head; // start at head
		int count = 0; // count position
		
		// loop to index location
		while (count < index) {
			current = current.getNext();
			count++;
		}
		
		return current.getData(); // return element at index
	}

	@Override
	public LinkedListIterator<T> iterator() {
		return new LinkedListIterator<T>(head);
	}
}
