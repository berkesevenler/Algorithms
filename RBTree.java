import java.io.FileWriter;
import java.io.IOException;

/**
 * Red-Black Tree implementation that maintains balance during insertions.
 *
 * @param <T> The type of data stored in the Red-Black Tree, must extend Comparable.
 */
public class RBTree<T extends Comparable<T>> {

  // Constants representing the colors of nodes in the Red-Black Tree
  private static final boolean RED = true;
  private static final boolean BLACK = false;

  // Reference to the root of the Red-Black Tree
  private Node root;
  private int n = 0;
 /**
   * Private inner class Node representing nodes in the Red-Black Tree.
   * Each node has data, left and right children, a parent, and a color.
   */
  private class Node {
    T data;
    Node left;
    Node right;
    Node parent;
    boolean color;

   /**
    * Constructs a node with the specified data.
    *
    * @param data The data to be stored in the node.
    */
    public Node(T data) {

      this.data = data;
    }
  }

  /**
   * This method corrects Red-Black Tree properties after the insertion of a new node.
   * After inserting a node, this method checks and adjusts the tree to ensure it adheres to
   * the Red-Black Tree rules, including color properties and tree structure.
   *
   * @param node The recently inserted node that may disrupt Red-Black Tree properties.
   *             This method ensures that the properties are restored starting from this node.
   *             The correction process involves rotations and color adjustments.
   */
  private void fixRedBlackPropertiesAfterInsert(Node node) {
    // Continue fixing properties until the current node is the root or its parent's color is black
    // Deal with Case 1 and Case 2
    while (node != root && node.parent.color == RED) {
      // Check if the parent of the current node is the left child of its grandparent
      if (node.parent == node.parent.parent.left) {
        Node uncle = node.parent.parent.right; // Get the uncle of the current node
        // Case 3: Uncle is red, change colors to restore properties
        if (uncle != null && uncle.color == RED) {
          node.parent.color = BLACK;
          uncle.color = BLACK;
          node.parent.parent.color = RED;
          node = node.parent.parent; // Move up the tree to fix violations higher up
        } else {
          // Case 4: Uncle is black, and the current node is "inner grandchild"
          if (node == node.parent.right) {
            node = node.parent;
            rotateLeft(node); // Rotate left to make it a Case 5 scenario
          }
          // Case 5: Uncle is black, and the current node is "outer grandchild"
          node.parent.color = BLACK; // Flip colors of parent and grandparent
          node.parent.parent.color = RED;
          rotateRight(node.parent.parent); // Rotate right to balance the tree
        }
      } else {
        // Symmetric cases for the right child of its grandparent
        Node uncle = node.parent.parent.left;
        if (uncle != null && uncle.color == RED) {
          node.parent.color = BLACK;
          uncle.color = BLACK;
          node.parent.parent.color = RED;
          node = node.parent.parent;
        } else {
          if (node == node.parent.left) {
            node = node.parent;
            rotateRight(node);
          }
          node.parent.color = BLACK;
          node.parent.parent.color = RED;
          rotateLeft(node.parent.parent);
        }
      }
    }
    root.color = BLACK; // Ensure the root is always black
  }

  /**
   * Performs a right rotation around the specified node in the Red-Black Tree.
   * A right rotation is a restructuring operation that preserves the binary search tree property
   * and helps maintain Red-Black Tree balance after certain insertions.
   *
   * @param node The node around which the right rotation is performed.
   *             After the rotation, this node becomes the right child of its left child.
   *             This operation helps in balancing the tree and maintaining Red-Black Tree properties.
   *             The parent-child relationships, as well as the pointers, are appropriately adjusted.
   */
  private void rotateRight(Node node) {
    Node parent = node.parent;
    Node leftChild = node.left;
    node.left = leftChild.right;

    //If the right child of the left child exists,-
    //its parent pointer is updated to point to the current node.
    if (leftChild.right != null) {
      leftChild.right.parent = node;
    }
    leftChild.right = node;
    node.parent = leftChild;

    //replace the original node with its new position in the tree.
    replaceParentsChild(parent, node, leftChild);
  }

  /**
   * Performs a left rotation around the specified node in the Red-Black Tree.
   * A left rotation is a restructuring operation that preserves the binary search tree property
   * and helps maintain Red-Black Tree balance after certain insertions.
   *
   * @param node The node around which the left rotation is performed.
   *             After the rotation, this node becomes the left child of its right child.
   *             This operation helps in balancing the tree and maintaining Red-Black Tree properties.
   *             The parent-child relationships, as well as the pointers, are appropriately adjusted.
   */
  private void rotateLeft(Node node) {
    Node parent = node.parent;
    Node rightChild = node.right;
    node.right = rightChild.left;

    //If the left child of the right child exists,
    //its parent pointer is updated to point to the current node.
    if (rightChild.left != null) {
      rightChild.left.parent = node;
    }
    rightChild.left = node;
    node.parent = rightChild;

    //replace the original node with its new position in the tree
    replaceParentsChild(parent, node, rightChild);
  }

  /**
   * Replaces the old child node with a new child node in the Red-Black Tree.
   * This method is responsible for updating the parent's child pointer to maintain
   * the correct structure of the Red-Black Tree after rotations or other restructuring operations.
   *
   * @param parent   The parent node whose child is to be replaced.
   *                 If the parent is null, it means the root of the Red-Black Tree is being updated.
   * @param oldChild The old child node to be replaced.
   *                 This is the node currently connected as the left or right child of the parent.
   * @param newChild The new child node that replaces the old child.
   *                 This is the node that will be connected as the left or right child of the parent.
   *                 If null, the oldChild is being detached, and the parent's child pointer is set to null.
   * @throws IllegalStateException If the old child is neither the left nor the right child of the parent,
   *                               indicating an unexpected inconsistency in the Red-Black Tree structure.
   *                               This exception ensures the integrity of the tree.
   */
  private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {

    //If the parent is null
    //the root of the Red-Black Tree is updated to be the new child.
    if (parent == null) {
      root = newChild;
    }

    //If the old child is the left child of the parent,
    //the left child of the parent is updated to be the new child.
    else if (parent.left == oldChild) {
      parent.left = newChild;
    }

    //If the old child is the right child of the parent,
    //the right child of the parent is updated to be the new child.
    else if (parent.right == oldChild) {
      parent.right = newChild;
    }

    //If the old child is neither the left nor the right child of the parent,
    //an exception is thrown.
    else {
      throw new IllegalStateException("Node is not a child of its parent");
    }

    //Regardless of whether the old child was found and replaced,
    //the parent pointer of the new child is updated to point to the parent.
    if (newChild != null) {
      newChild.parent = parent;
    }
  }

  /**
   * Inserts a new node with the specified data into the Red-Black Tree and ensures
   * the Red-Black Tree properties are maintained after the insertion operation.
   *
   * @param data The data to be inserted into the Red-Black Tree as a new node.
   * @throws IllegalArgumentException If a node with the given data already exists in the tree.
   *                                  This is to maintain the uniqueness of data in the tree.
   *                                  If the same data is attempted to be inserted again,
   *                                  it would violate the tree's structure.
   */
  public void insert(T data) {
    Node node = root; // Start from the root of the tree
    Node parent = null; // Initialize the parent as null for the root

    // Traverse the tree to find the appropriate position for the new node
    while (node != null) {
      parent = node; // Track the current node as the potential parent
      int cmp = data.compareTo(node.data);

      // Traverse to the left child if the new data is smaller
      if (cmp < 0) {
        node = node.left;
      }
      // Traverse to the right child if the new data is larger
      else if (cmp > 0) {
        node = node.right;
      }
      // Throw an exception if the new data is already present in the tree
      else {
        throw new IllegalArgumentException("Tree already contains a node with data " + data);
      }
    }

    // Create a new node with the specified data and set its color to RED
    Node newNode = new Node(data);
    newNode.color = RED;

    // If the tree is empty, set the new node as the root
    if (parent == null) {
      root = newNode;
    }
    // Attach the new node as the left child if the data is smaller than the parent's data
    else if (data.compareTo(parent.data) < 0) {
      parent.left = newNode;
    }
    // Attach the new node as the right child if the data is larger than the parent's data
    else {
      parent.right = newNode;
    }

    // Set the parent of the new node
    newNode.parent = parent;

    // Fix Red-Black Tree properties after the insertion
    fixRedBlackPropertiesAfterInsert(newNode);
  }

  /**
   * Generates and writes a DOT file representation of the Red-Black Tree to the specified file.
   * The DOT file can be visualized using Graphviz tools to illustrate the structure of the tree.
   *
   * @param filename The name of the file to which the DOT representation will be written.
   * @throws IOException If an IOException occurs during file writing operations, the stack trace is printed.
   */
  public void printDOT(String filename) {

    //try-catch block
    try {

      //Creates a FileWriter object to write to the specified file (filename).
      FileWriter writer = new FileWriter(filename);

      //Adjusts graph ratio, and sets node attributes such as -
      //style, color, shape, width, font, etc.
      writer.write("digraph G {\n");
      writer.write("\tgraph [ratio=.48];\n");
      writer.write("\tnode [style=filled, color=black, shape=circle, width=.6 \n" +
              "\t\tfontname=Helvetica, fontweight=bold, fontcolor=white, \n" +
              "\t\tfontsize=24, fixedsize=true];\n");

      //Initializes a counter variable n to keep track of the number of nodes in the tree.
      n = 0;
      writer.write("\n");
      writer.write("\t");
      printDOTRecursive(writer, root);
      writer.write("}\n");

      //Closes the FileWriter
      writer.close();

      //if any IOException occurs during file writing operations, it prints the stack trace.
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Recursively generates the DOT representation of the Red-Black Tree starting from the given node.
   * Writes DOT statements to color nodes, represent edges, and create NIL nodes as needed.
   *
   * @param writer The FileWriter object used to write to the DOT file.
   * @param node   The current node in the recursive traversal of the Red-Black Tree.
   * @throws IOException If an IOException occurs during file writing operations.
   */
  private void printDOTRecursive(FileWriter writer, Node node) throws IOException {
    //Ensures that the recursive calls stop when a null node is reached
    if (node != null) {

      //if the current node's color is red, it writes a DOT statement -
      //to color the corresponding node in the graph as red.
      if (node.color == RED) {
        writer.write(node.data + " [fillcolor=red];\n");
      }

      // the left child exists, it writes a DOT statement to represent the edge -
      //from the current node to its left child with a label "L."
      if (node.left != null) {
        writer.write(node.data + " -> " + node.left.data + " [label=\"L\"];\n");
        printDOTRecursive(writer, node.left);
      }

      //f the left child is null (NIL), it creates a virtual node (NIL node) and represents -
      //the edge from the current node to the NIL node with a label "L."
      else {
        writer.write("n" + ++n + " [label=\"NIL\", shape=record, width=.4,height=.25, fontsize=16];\n");
        writer.write(node.data + " -> n" + n + " [label=\"L\"];\n");
      }

      //writes DOT statements for the right child, representing the edge with a label "R."
      if (node.right != null) {
        writer.write(node.data + " -> " + node.right.data + " [label=\"R\"];\n");
        printDOTRecursive(writer, node.right);
      }
      //If the right child is null (NIL), it creates a virtual node (NIL node) and represents -
      //the edge from the current node to the NIL node with a label "R."
      else {
        writer.write("n" + ++n + " [label=\"NIL\", shape=record, width=.4,height=.25, fontsize=16];\n");
        writer.write(node.data + " -> n" + n + " [label=\"R\"];\n");
      }
    }
  }
}

