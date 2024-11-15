import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The Main class contains the main method that demonstrates the usage of the Red-Black Tree.
 * It generates 15 unique random Integer values, creates IntComparable objects for each value,
 * and inserts them into a Red-Black Tree. The state of the tree is visualized at each insertion
 * step by printing the DOT representation to a file.
 */
public class Main {
  public static void main(String[] args) {

    // Create a Red-Black Tree to store IntComparable objects
    RBTree<IntComparable> redBlackTree = new RBTree<>();

    // Set to keep track of generated unique values
    Set<Integer> generatedValues = new HashSet<>();
    // Random object for generating random values
    Random random = new Random();

    // Generate 15 unique random Integer values and insert them into the RB-Tree
    int i = 1;
    while (generatedValues.size() < 15) {
      int randomNumber = random.nextInt(100);
      // If the generated value is unique, add it to the RB-Tree
      if (generatedValues.add(randomNumber)) {
        IntComparable intComparable = new IntComparable(randomNumber);
        redBlackTree.insert(intComparable);

        // Print the current state of the Red-Black Tree to a DOT file
        redBlackTree.printDOT("insert_step_" + i + ".dot");
        i++;
      }
    }
  }
}

/**
 * Class representing an Integer value with Comparable interface for Red-Black Tree.
 */
class IntComparable implements Comparable<IntComparable> {
  private final int value;

  /**
   * Constructs an IntComparable object with the specified value.
   *
   * @param value The Integer value to be stored.
   */
  public IntComparable(int value) {

    this.value = value;
  }


  /**
   * Compares this IntComparable object with another.
   *
   * @param other The IntComparable object to compare with.
   * @return A negative integer, zero, or a positive integer if this object is less than,
   *         equal to, or greater than the specified object.
   */
  @Override
  public int compareTo(IntComparable other) {

    return Integer.compare(this.value, other.value);
  }

  /**
   * Returns a string representation of the Integer value.
   *
   * @return A string representation of the Integer value.
   */
  @Override
  public String toString() {

    return Integer.toString(value);
  }
}

