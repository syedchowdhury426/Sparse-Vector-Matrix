public class LLSparseVec implements SparseVec {
	
	/*
		LLSparseVec is a one dimensional vector. 
		As single row of non zero elements
		Container for Nodes
	*/
	private class Node {

		/*
		The Node is the container of the elements. 
		Holds the value of the object, the position its located in, 
		and a reference to the next node
		*/
		private int element;
		private int index;
		private Node next;
	
		Node (int e, int i, Node n) {
			this.element = e;
			this.index = i;
			this.next = n;
		}

		public void setNext (Node n) {
			this.next = n;
		}
		
		public void setElement (int e) {
			this.element = e; 
		}
		
		public int getElement () {
			return element;
		}
		
		public int getIndex () {
			return index;
		}
		
		public Node getNext () {
			return next;	
		}
	}


	private Node head = null;
	private Node tail = null;
	private final int size;
	private int numOfElements = 0;

	public LLSparseVec (int len) {
		this.size = len;
	}

	@Override
	public int getLength () {
		return size;
	}

	@Override
	public int numElements () {
		return numOfElements;
	}

	//Check to see if the index is allowed
	public boolean outOfBounds (int idx) {
		if (idx < 0 || idx >= size)
			return true;
		return false;
	}

	@Override
	public int getElement (int idx) {
		//Check to see if the existence of the index is possible
		if (outOfBounds(idx) || tail.getIndex() < idx || head.getIndex() > idx || numElements() == 0)
			return 0;
		//A node that is used to traverse the vector
		Node currentNode = head;
		//Traverse the vector till either we run out of elements or we get to a node that may be correct
		while (currentNode != null && currentNode.getIndex() < idx)
			currentNode = currentNode.getNext();
		//The index is not in the list
		if (currentNode == null || currentNode.getIndex() != idx)
			return 0;
		//Index has been found. Return its value
		return currentNode.getElement();
	}

	@Override
	public void clearElement (int idx) {
		//Check if the existence of the index is possible
		if (outOfBounds(idx) || numElements() == 0)
			return;
		//Check if the first Node is what needs to be taken out
		if (head.getIndex() == idx) {
			//Only one element in the vector
			if (tail == head)
				tail = null;
			head = head.getNext();
			numOfElements--;
			return;
		}
		//Use two Nodes to traverse the vector, it allows for easy removal
		Node previousNode = null;
		Node currentNode = head;
		//Traverse the vector till we get to the right element
		while (currentNode != null && currentNode.getIndex() < idx) {
			previousNode = currentNode;
			currentNode = currentNode.getNext();
		}
		//The index is not in the vector
		if (currentNode == null || currentNode.getIndex() != idx)
			return;
		//Index has been found. We must take out the node
		previousNode.setNext(currentNode.getNext());
		numOfElements--;
		//Tail was removed, so we must make thelast element the new tail
		if (previousNode.getNext() == null)
			tail = previousNode;
	}

	@Override
	public void setElement (int idx, int val) {
		//Check if index is illegal
		if (outOfBounds(idx))
			return;
		//If value to be set is zero, then we must take out that node
		if (val == 0) {
			clearElement(idx);
			return;
		}
		//There are no nodes in the list, so create first node
		if (numElements() == 0) {
			head = new Node(val, idx, null);
			tail = head;
			numOfElements++;
			return;
		}
		//Use two nodes to traverse vector. Allows for easy insertion
		Node previousNode = null;
		Node currentNode = head;
		//Traverse list till we get to right position for insertion
		while (currentNode != null && currentNode.getIndex() < idx) {
			previousNode = currentNode;
			currentNode = currentNode.getNext();
		}
		//New element to be placed at the end of the vector
		if (currentNode == null) {
			Node newNode = new Node (val, idx, null);
			tail.setNext(newNode);
			tail = newNode;
			numOfElements++;
			return;
		}
		//New element to be placed within the vector
		else if (currentNode.getIndex() != idx) {
			//New element to be added is the new head
			if (previousNode == null) {
				head = new Node(val, idx, head);
				numOfElements++;
				return;
			}
			//Place new node between two nodes
			else {
				Node newNode = new Node (val, idx, null);
				previousNode.setNext(newNode);
				newNode.setNext(currentNode);
				numOfElements++;
				return;
			}
		}
		//Node has been found. Update its value
		else if (currentNode.getIndex() == idx) {
			currentNode.setElement(val);
			return;
		}
	}

	@Override
	public int[] getAllIndices () {
		//Vector is empty
		if (numElements() == 0)
			return null;
		Node currentNode = head;
		int[] indices = new int[numElements()];
		int i = 0;
		//Go through vector till completion
		while (currentNode != null) {
			indices[i++] = currentNode.getIndex();
			currentNode = currentNode.getNext();
		}
		return indices;
	}

	@Override
	public int[] getAllValues () { 
		//Vector is empty
		if (numElements() == 0)
			return null;
		Node currentNode = head;
		int[] values = new int[numElements()];
		int i = 0;
		//Go through vector till completion
		while (currentNode != null) {
			values[i++] = currentNode.getElement();
			currentNode = currentNode.getNext();
		}
		return values;
	}

	/*
		Implementation:
		Start at the head of both rows
		Add the lower indexed one to the end of the new vector
		Continue till both vectors are complete
	*/
	@Override
	public SparseVec addition (SparseVec otherV) {
		//The vectors are not of the same size
		if (this.getLength() != otherV.getLength())
            return null;
		//The original vector has no elements, so it is sufficient to return the other vector
		if (this.numElements() == 0)
			return otherV;
		//The other vector has no elements, so it is sufficient to return the original vector
		if (otherV.numElements() == 0)
			return this;
		//Get a referece to the first node in the original vector
		Node originalVectorElement = this.head;
		//Get a referecne to the first node in the other vector
		Node otherVectorElement = ((LLSparseVec) otherV).head;
		//Create a new vector, this will hold the sum of the other two vectors
		LLSparseVec finalVector = new LLSparseVec (getLength());
		//Traverse both vectors till both have run out of elements to add
		while (originalVectorElement != null || otherVectorElement != null) {
			//Original vector has run out of elements. Other vector still has elements to be entered
			if (originalVectorElement == null) {
				Node newNode = new Node(otherVectorElement.getElement(), otherVectorElement.getIndex(), null);
				//Currently no elements exist in the new vector
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//The vector already has elements, so place elements after tail
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//Get to the next node in the other vector
				otherVectorElement = otherVectorElement.getNext();
			}
			//Other vector has run out of elements. Original vector still has elements to be entered
			else if (otherVectorElement == null) {
				Node newNode = new Node(originalVectorElement.getElement(), originalVectorElement.getIndex(), null);
				//Currently no elements exists in the new vector
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//There exists elements in the vector. Add new elements to the tail
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//Get to the next node in the original vector
				originalVectorElement = originalVectorElement.getNext();
			}
			//The current element in the original vector has a lower index than the element in other, so we add the element in original first
			else if (originalVectorElement.getIndex() < otherVectorElement.getIndex()) {
				Node newNode = new Node(originalVectorElement.getElement(), originalVectorElement.getIndex(), null);
				//Currently there exists no elements in the vector
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//There are elements in the vector. Add new elements to the tail of the new vector
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//Get to the next node in the original vector
				originalVectorElement = originalVectorElement.getNext();
			}
			//The current element in the other vector has a lower index than the element in the original, so we add the element in other first
			else if (otherVectorElement.getIndex() < originalVectorElement.getIndex()) {
				Node newNode = new Node(otherVectorElement.getElement(), otherVectorElement.getIndex(), null);
				//Currently there are no elements in the vector
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//There are elements in the vector. Add new elements to the tail of the vector
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				//Get to the next element in the other vector
				otherVectorElement = otherVectorElement.getNext();
			}
			//The current elements in both vectors has the same index so we add their values, and store the new node at the end of the vector
			else if (originalVectorElement.getIndex() == otherVectorElement.getIndex()) {
				int sum = originalVectorElement.getElement() + otherVectorElement.getElement();
				//If the sum is not zero, create a new element with the sum and store it
				if (sum != 0) {
					Node newNode = new Node(sum, originalVectorElement.getIndex(), null);
					//Currently there are no elements in the vector
					if (finalVector.numElements() == 0) {
						finalVector.head = newNode;
						finalVector.tail = newNode;
						finalVector.numOfElements++;
					}
					else {
						finalVector.tail.setNext(newNode);
						finalVector.tail = newNode;
						finalVector.numOfElements++;
					}
				}
				//Get the next elements in the original and other vector
				originalVectorElement = originalVectorElement.getNext();
				otherVectorElement = otherVectorElement.getNext();
			}
		}
		//Return the sum of the two vectors
		return finalVector;
	}

	/*
		Implementation:
		Same as Addition
	*/
	@Override
	public SparseVec substraction (SparseVec otherV) {
		if (this.getLength() != otherV.getLength())
            return null;
		if (otherV.numElements() == 0)
			return this;
		Node originalVectorElement = this.head;
		Node otherVectorElement = ((LLSparseVec) otherV).head;
		LLSparseVec finalVector = new LLSparseVec (getLength());
		while (originalVectorElement != null || otherVectorElement != null) {
			if (originalVectorElement == null) {
				Node newNode = new Node(0 - otherVectorElement.getElement(), otherVectorElement.getIndex(), null);
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				otherVectorElement = otherVectorElement.getNext();
			}
			else if (otherVectorElement == null) {
				Node newNode = new Node(originalVectorElement.getElement(), originalVectorElement.getIndex(), null);
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				originalVectorElement = originalVectorElement.getNext();
			}
			else if (originalVectorElement.getIndex() < otherVectorElement.getIndex()) {
				Node newNode = new Node(originalVectorElement.getElement(), originalVectorElement.getIndex(), null);
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				originalVectorElement = originalVectorElement.getNext();
			}
			else if (otherVectorElement.getIndex() < originalVectorElement.getIndex()) {
				Node newNode = new Node(0 - otherVectorElement.getElement(), otherVectorElement.getIndex(), null);
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				otherVectorElement = otherVectorElement.getNext();
			}
			else if (originalVectorElement.getIndex() == otherVectorElement.getIndex()) {
				int difference = originalVectorElement.getElement() - otherVectorElement.getElement();
				if (difference != 0) {
					Node newNode = new Node(difference, originalVectorElement.getIndex(), null);
					if (finalVector.numElements() == 0) {
						finalVector.head = newNode;
						finalVector.tail = newNode;
						finalVector.numOfElements++;
					}
					else {
						finalVector.tail.setNext(newNode);
						finalVector.tail = newNode;
						finalVector.numOfElements++;
					}
				}
				originalVectorElement = originalVectorElement.getNext();
				otherVectorElement = otherVectorElement.getNext();
			}
		}
		return finalVector;
	}

	/*
		Implementation:
		Same as addition
		Easier due their only being one case where the element is put in the vector
	*/
	@Override
	public SparseVec multiplication (SparseVec otherV) {
		if (this.getLength() != otherV.getLength())
            return null;
		if (this.numElements() == 0 || otherV.numElements() == 0)
			return new LLSparseVec(0);
		Node originalVectorElement = this.head;
		Node otherVectorElement = ((LLSparseVec)otherV).head;
		LLSparseVec finalVector = new LLSparseVec(getLength());
		while (originalVectorElement != null || otherVectorElement != null) {
			if (originalVectorElement == null)
				return finalVector;
			else if (otherVectorElement == null)
				return finalVector;
			else if (originalVectorElement.getIndex() < otherVectorElement.getIndex()) 
				originalVectorElement = originalVectorElement.getNext();
			else if (otherVectorElement.getIndex() < originalVectorElement.getIndex())
				otherVectorElement = otherVectorElement.getNext();
			else if (originalVectorElement.getIndex() == otherVectorElement.getIndex()) {
				int product = originalVectorElement.getElement() * otherVectorElement.getElement();
				Node newNode = new Node(product, originalVectorElement.getIndex(), null);
				if (finalVector.numElements() == 0) {
					finalVector.head = newNode;
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				else {
					finalVector.tail.setNext(newNode);
					finalVector.tail = newNode;
					finalVector.numOfElements++;
				}
				originalVectorElement = originalVectorElement.getNext();
				otherVectorElement = otherVectorElement.getNext();
			}
		}
		return finalVector;
	}
}
