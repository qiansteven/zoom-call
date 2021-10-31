import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator<T> implements Iterator<T> {
	
	Node<T> current;
		
	public LinkedListIterator(Node<T> head){
		current = head;
	}

	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public T next() throws NoSuchElementException {
		// TODO:  COMPLETE THIS METHOD
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		
		T returnedElement = current.getData();
		current = current.getNext();
		return returnedElement;
	}
}
