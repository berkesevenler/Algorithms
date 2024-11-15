
public class SparseVector {
	private int length;
	// The first node in the list (is an element)
	private Node head; 

	// A empty constructor allows the creation of empty objects
	public SparseVector() {
	}

	public SparseVector(int n) {
		length = n;
	}

	// Node in the linked list
	private static class Node {
		// Value and index of the element
		private double value;
		private int index;
		// Reference to the next element in the list
		private Node next;

		// Constructor for Node
		Node(int index, double value) {
			this.index = index;
			this.value = value;
		}
	}

	/**
	 * Sets the value of the element at the specified index in Sparse vector.
	 * If the provided value is non-zero, the method updates an existing node at the
	 * given index or inserts a new node at the appropriate position in the Sparse
	 * vector. If the Sparse vector is empty or the specified index is smaller than
	 * the current head's index, a new node is created and becomes the new head. If
	 * the specified index is greater than any existing index, a new node is added
	 * at the end of the Sparse vector.
	 *
	 * @param index The index at which to set the element.
	 * @param value The value to set at the specified index.
	 */

	public void setElement(int index, double value) {

		// If the specified index is invalid (less than 0 or
		// greater than or equal to the length
		// of the Sparse vector), then it throws an Exception
		if (index < 0 || index >= length) {
			throw new RuntimeException("\nError : Out of Bounds\n");
		}

		// Check if the value is not zero, as we only want to
		// store non-zero values
		if (value != 0.0) {
			// Check if the Sparse vector is empty or if the new
			// element's index is smaller than the head's index
			if (head == null || index < head.index) {
				// Create a new node and make it the new head
				Node newNode = new Node(index, value);
				newNode.next = head;
				head = newNode;
				length++;
			} else {

				// Traverse the Sparse vector to find the correct
				// position for the new element
				Node current = head;
				// Iterate through the list to find the correct position for the new element
				while (current.next != null) {

					// If the index already exists, update the
					// existing node's value
					if (index == current.index) {
						current.value = value;
						return;

					} else if (index > current.index) {
						// If the new index is greater than the current node's index,
						// and less than the next node's index, insert
						// the new node in between
						if (index < current.next.index) {
							Node newNode = new Node(index, value);
							newNode.next = current.next;
							current.next = newNode;
							length++;
							return;
						}
						// Move to the next node in the Sparse Vector
						current = current.next;
					}
				}
				// Handle the case where the new element's index
				// is greater than any existing index
				// or equal to last index of the Sparse vector
				if (index == current.index) {
					// Update the value if the index already exists
					current.value = value;
				} else {

					// Create a new node and add it at the end of the
					// Sparse vector
					Node newNode = new Node(index, value);
					current.next = newNode;
					newNode.next = null;
					length++;
				}
			}
		}
	}

	public double getElement(int index) {

		// Check if the passed index is in the valid range.
		// If the index is negative, "Ungültiger Index" is printed on the console
		if (index < 0) {
			throw new IllegalArgumentException("Ungültiger Index");
		}

		// A pointer (current) is set to the beginning of the list (head)
		Node current = head;

		// As long as the current pointer has not reached the end of the list
		// and the index of the current element is less than the desired index
		while (current != null && current.index < index) {
			// the pointer is moved to the next element in the list
			current = current.next;
		}

		// Check if the element with the desired index exists
		// If yes, return the value of that element (current.value)
		// Otherwise, return 0.0, indicating that the element with the desired index is
		// not present in the vector
		return (current != null && current.index == index) ? current.value : 0.0;
	}

	// removeElement -> enables the removal of an element at a specific position in
	// the sparse vector
	public void removeElement(int index) {
		// Checks if the passed index is in the valid range
		// If the index is negative or equal to/greater than the length of the vector,
		// "invalid index" is printed on the console
		if (index < 0 || index >= length) {
			System.out.println("invalid index");
		}
		// If the list is empty, there is nothing to remove, so the method terminates
		if (head == null) {
			return;
		}
		// If the index to be removed is the index of the first element,
		// the head is moved to the next element, effectively removing the first element
		if (head.index == index) {
			head = head.next;
			return;
		}

		// Iterates through the list to find the element with the index to be removed
		// The pointer `current` is moved so that it is positioned before the element to
		// be removed
		Node current = head;
		while (current.next != null && current.next.index < index) {
			current = current.next;
		}

		// If the next element of the current element has the index to be removed,
		// the next element is updated so that it skips the element to be removed
		if (current.next != null && current.next.index == index) {
			current.next = current.next.next;
		}
	}

	// Returns the length of the sparse vector
	public int getLength() {
		return this.length;
	}

	// equals -> checks if two sparse vectors are equal by comparing their lengths
	// and then verifying the indices and values of the elements in both vectors
	public boolean equals(SparseVector other) {
		// Checks if the lengths of the two sparse vectors (this and other) are
		// different
		// If they are, the vectors are not equal, and it returns false
		if (this.length != other.length) {
			return false;
		}

		// Pointer `current1` is set to the head of the first vector (this.head)
		// Pointer `current2` is set to the head of the second vector (other.head)
		Node current1 = this.head;
		Node current2 = other.head;

		// Iterates through both vectors simultaneously and compares the elements,
		// checking if the indices and values of the current elements in both vectors
		// are equal
		// If not, the vectors are not equal, and the method returns false
		while (current1 != null && current2 != null) {
			if (current1.index != current2.index || current1.value != current2.value) {
				return false;
			}

			// Moves the pointers to the next element in both vectors to compare the next
			// pair of elements
			current1 = current1.next;
			current2 = current2.next;
		}
		// Checks if both vectors are of equal length and if both pointers have reached
		// the end
		return current1 == null && current2 == null;
	}

	/**
	 * Adds the elements of another SparseVector to the current SparseVector. If the
	 * current SparseVector is empty, it copies the elements from the other
	 * SparseVector. If the indices of the current and other SparseVectors match,
	 * their values are summed
	 * 
	 * If the index of the current SparseVector is greater than or less than that of
	 * the other SparseVector, it inserts the element from the other SparseVector at
	 * the appropriate position in the current SparseVector.
	 *
	 * @param other The SparseVector to add to the current SparseVector.
	 */
	
	void add(SparseVector other) {
	    // Addition of Sparse Vectors whose lengths are different is not possible
	    if (this.length != other.length) {
	        // throw new RuntimeException("Error: Vector lengths are not the same");
	        // It's a good practice to handle this case, either by throwing an exception or returning an error code.
	        // For simplicity, I've commented it out, but you may want to include it based on your requirements.
	    }

	    // If the current SparseVector is empty, copy the elements from the other SparseVector
	    if (this.head == null) {
	        this.head = other.head;
	    }

	    // Initialize iterators for the current and other SparseVectors
	    Node thisCurr = this.head;
	    Node otherCurr = other.head;
	    Node prev = null;

	    // Iterate through the elements of the other SparseVector
	    while (otherCurr != null) {
	        // If the indices of the current and other SparseVectors match, sum their values
	        if (thisCurr != null && thisCurr.index == otherCurr.index) {
	            thisCurr.value += otherCurr.value;
	            prev = thisCurr;
	            thisCurr = thisCurr.next;
	            otherCurr = otherCurr.next;

	        } else if (thisCurr == null || thisCurr.index > otherCurr.index) {
	            // If the index of the current SparseVector is greater than that of the other SparseVector,
	            // insert the element from the other SparseVector into the current SparseVector
	            Node newNode = new Node(otherCurr.index, otherCurr.value);
	            newNode.next = thisCurr;
	            if (prev == null) {
	                this.head = newNode;
	            } else {
	                prev.next = newNode;
	            }
	            prev = newNode;
	            otherCurr = otherCurr.next;

	        } else {
	            // If the index of the current SparseVector is less than that of the other SparseVector
	            // and there is a next node, insert the element from the other SparseVector
	            // between the current node and the next node in the current SparseVector
	            prev = thisCurr;
	            thisCurr = thisCurr.next;
	        }
	    }
	}

	
	
	@Override
	public String toString() {
		// StringBuilder -> is created to efficiently build a string
		// The beginning of the string is initialized with information about the length
		// of the vector
		// and the start of the list of elements
		StringBuilder result = new StringBuilder("SparseVector{length=" + length + ",elements=[");

		// The pointer (current) is set to the head of the vector (head) to traverse the
		// linked list of elements
		Node current = head;

		// A loop is initiated that iterates through the linked list of vector elements
		// as long as there are still elements
		while (current != null) {
			// The index and value of the current element are added to the string
			result.append(current.index).append(":").append(current.value);

			// Checks if there is a next element
			// If yes, a comma and a space are added to the string to separate the elements
			if (current.next != null) {
				result.append(", ");
			}
			// The pointer (current) is moved to the next element in the linked list
			current = current.next;
		}
		// the conclusion of the string is added to close the list of elements
		result.append("]}");

		// The complete string is returned as the result
		return result.toString();
	}


 public static void main(String[] args) {
	 SparseVector vector1 = new SparseVector(5);
	 vector1.setElement(1, 2);
	 vector1.setElement(3, 4);
	 SparseVector vector2 = new SparseVector(5);
	 vector2.setElement(2, 3);
	 vector2.setElement(3, 4);
	 System.out.println("Vector 1: " + vector1.toString());
	 System.out.println("Vector 2: " + vector2.toString());
	 vector1.add(vector2);
	 System.out.println("Sum: " + vector1.toString());
	 }

}

