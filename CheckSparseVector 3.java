import static org.junit.Assert.*;  //provides assertion methods
import org.junit.Before;  //annotations for setup methods
import org.junit.Test;  //annotations for test methods

public class CheckSparseVector {

//Declares two private instance variables of type SparseVector named vec and otherVector.
private SparseVector vec; 
private SparseVector otherVector;
	

	@Before
	public void setUp() {
	    // Creates two SparseVector instances (vec and otherVector).
	    vec = new SparseVector(5);
	    otherVector = new SparseVector(5);

	    // Initializes their elements.
	    vec.setElement(0, 1.0);  //(index, element)
	    vec.setElement(1, 2.0);
	    vec.setElement(2, 3.0);
	    vec.setElement(3, 4.0);
	    vec.setElement(4, 5.0);

	    // Needed for testAdd function.
	    otherVector.setElement(1, 3.0);  //(index, element)
	    otherVector.setElement(3, 4.0);
	    otherVector.setElement(4, 1.0);
	}

	
	
	
	@Test  //Each annotated @Test method is a test case checking specific functionalities of the SparseVector class.
	public void testLength() {
		vec = new SparseVector(5); //Creates a new SparseVector
		assertEquals(5,vec.getLength()); //checking if the length is as expected.
	}
	
	
	
	@Test
	public void testRemoveElement() {
		vec.removeElement(1); //calling the removeElement method at index 1
		//Removes an element at index 1 and checks if the value at that index is now 0.0.
		assertEquals(0.0, vec.getElement(1), 0.0); //assertEquals(expected element, actual, delta);
	}
	
	
	
	@Test
	public void testSetElement() {
		vec = new SparseVector(3);
	    vec.setElement(2, 2.0);  //Sets an element at index 2.
	    assertEquals(2.0, vec.getElement(2), 0.0);  //checks if the value at index 2 is now 2.0.
	}
	
	
	
	@Test
	public void testAdd() {
		
		vec.add(otherVector); 
		//Adds otherVector to vec and checks if the resulting vector has the expected elements.
		assertEquals(1.0, vec.getElement(0), 0.0001);
	    assertEquals(5.0, vec.getElement(1), 0.0001);
	    assertEquals(3.0, vec.getElement(2), 0.0001);
	    assertEquals(8.0, vec.getElement(3), 0.0001);
	    assertEquals(6.0, vec.getElement(4), 0.0001);
	}
	
	
	
	@Test
	public void testGetElement() {
		//Checks if the elements at specific indices match the expected values.
		assertEquals(1.0, vec.getElement(0), 0.0);
        assertEquals(2.0, vec.getElement(1), 0.0);
        assertEquals(3.0, vec.getElement(2), 0.0);
        assertEquals(4.0, vec.getElement(3), 0.0);
        assertEquals(5.0, vec.getElement(4), 0.0);
	}
	
	
	
	@Test
	public void testEquals() {
		//new instance equalVector: A vector with the same elements as vec.
		SparseVector equalVector = new SparseVector(5);
        equalVector.setElement(0, 1.0);  //(index, element)
        equalVector.setElement(1, 2.0);
        equalVector.setElement(2, 3.0);
        equalVector.setElement(3, 4.0);
        equalVector.setElement(4, 5.0);
        
        //new instance differentVector: A vector with different elements as vec.
        SparseVector differentVector = new SparseVector(5);
        differentVector.setElement(0, 1.0);  //(index, element)
        differentVector.setElement(1, 2.0);
        differentVector.setElement(2, 3.0);
        differentVector.setElement(3, 4.0);
        differentVector.setElement(4, 6.0);
        
        //new instance nullVector: A vector with all elements set to 0.0
        SparseVector nullVector = new SparseVector(5);
        nullVector.setElement(0, 0.0);  //(index, element)
        nullVector.setElement(1, 0.0);
        nullVector.setElement(2, 0.0);
        nullVector.setElement(3, 0.0);
        nullVector.setElement(4, 0.0);
        
        assertTrue(vec.equals(equalVector)); // This asserts that vec is equal to equalVector. 
        assertFalse(vec.equals(differentVector)); //This asserts that vec is not equal to differentVector.
        assertFalse(vec.equals(nullVector));  //This asserts that vec is not equal to nullVector
	}

}
