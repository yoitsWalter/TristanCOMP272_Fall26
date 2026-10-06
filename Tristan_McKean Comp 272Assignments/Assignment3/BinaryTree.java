import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A binary tree of integers (the tree from class, now storing int values).
 *
 * EXERCISE 1 — Complete the two methods marked TODO: add(int) and search(int).
 * Do NOT change the provided code, method names, or signatures.
 * You may add private helper methods.
 *
 * Name:
 * Student ID:
 */
public class BinaryTree {

    public static class Node {
        private int element;
        private Node left;
        private Node right;

        public Node(int element) {
            this.element = element;
        }

        public int getElement() { return element; }
        public Node getLeft() { return left; }
        public Node getRight() { return right; }
        public void setLeft(Node left) { this.left = left; }
        public void setRight(Node right) { this.right = right; }
    }

    // protected (not private) so that subclasses such as BST can use it.
    protected Node root;

    /** Creates an empty tree. */
    public BinaryTree() {
        this.root = null;
    }

    /** Creates a tree whose root is the given node (may be a hand-built tree). */
    public BinaryTree(Node root) {
        this.root = root;
    }

    public Node getRoot() { return root; }

    // Height is measured in edges: a leaf has height 0.
    // An empty tree has height -1.
    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return -1;
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        return 1 + Math.max(leftHeight, rightHeight);
    }

    // ------------------------------------------------------------------
    // EXERCISE 1 — TODO
    // ------------------------------------------------------------------

    /**
     * Part A: Adds a new node containing {@code value} at the FIRST FREE POSITION
     * found by a LEVEL-ORDER (breadth-first) traversal of the tree.
     *
     * Rule: visit the nodes level by level, from top to bottom, and from left
     * to right within each level (use a queue). At each visited node:
     *   - if its left child is empty, attach the new node there and stop;
     *   - otherwise, if its right child is empty, attach the new node there and stop;
     *   - otherwise, keep traversing.
     * If the tree is empty, the new node becomes the root.
     * Duplicate values are allowed in a general binary tree.
     */
    public void add(int value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            return;
        }

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.remove();

            if (current.getLeft() == null) {
                current.setLeft(newNode);
                return;
            }
            if (current.getRight() == null) {
                current.setRight(newNode);
                return;
            }

            queue.add(current.getLeft());
            queue.add(current.getRight());

        }

    }

    /**
     * Part B: Searches the tree for a node whose element equals {@code value}.
     * Use a PRE-ORDER traversal and return the FIRST matching node found
     * (this matters when the tree contains duplicates).
     *
     * @return the node containing value, or null if no such node exists
     *         (including when the tree is empty).
     */
    public Node search(int value) {
        return search(root, value);
    }
    private Node search(Node node, int value) {
        if (node == null) return null;
        if (node.getElement() == value) return node;
        
        Node found = search(node.getLeft(), value);
        if (found != null) return found;

        return search(node.getRight(), value);



    }
    /*
     * Exercise 1, Part C — Complexity of search (n = number of nodes)
     *
     * Best case: O(1)
     *
     * Worst case: O(n)
     *
     * Input that produces the worst case: The input that would produce the worst case would be the last node in the tree searched, or a node that does not exist.
     *
     * Justification: 
     * My justification for the best case is because the first node in the tree could be the one you are looking for, so it would only take one.
     * My justification for the worst case is because if it is the last node, you have to go through every node to get there, so O(n) time. Every node will be visited to reach said node.
     * Finally, if the value is not in the tree, you still have to go through every node, so still O(n).
     */

    // ------------------------------------------------------------------
    // PROVIDED — display the structure of any binary tree.
    // ------------------------------------------------------------------

    public void printTree() {
        if (root == null) {
            System.out.println("(empty tree)");
        }
        else {
            System.out.println(root.getElement());
            printChildren(root, "");
        }
    }

    private void printChildren(Node node, String prefix) {
        Node left = node.getLeft();
        Node right = node.getRight();

        if (left != null) {
            boolean last = right == null;
            System.out.println(prefix + (last ? "└── " : "├── ")
                    + "L: " + left.getElement());
            printChildren(left, prefix + (last ? "    " : "│   "));
        }
        if (right != null) {
            System.out.println(prefix + "└── R: " + right.getElement());
            printChildren(right, prefix + "    ");
        }
    }
}
