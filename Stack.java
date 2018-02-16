
public interface Stack<E>
{
	// return number of elements in the stack
	int size();
	
	// tests whether stack is empty
	boolean isEmpty();
	
	// inserts an element at the top of the stack
	void push(E e);
	
	// returns but doesn't remove element at top of the stack
	E top();
	
	// removes and returns the top element from the stack
	E pop();
	
}
