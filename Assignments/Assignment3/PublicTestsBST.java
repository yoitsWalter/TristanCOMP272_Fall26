import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import static java.util.Arrays.asList;

/**
 * PUBLIC TESTS — Exercise 2 (your BST class in BST.java).
 * This file will NOT compile until you have created BST.java.
 *
 *     javac *.java
 *     java PublicTestsBST
 *
 * Passing these tests does NOT guarantee full marks: your code will ALSO be
 * graded with additional hidden tests. Write your own tests as well.
 *
 * (TreeSet is used here only as a reference answer inside the tests —
 *  you may NOT use it in your own code.)
 */
public class PublicTestsBST extends TestSupport {

    private static BST bst(int... values) {
        BST t = new BST();
        for (int v : values) t.add(v);
        return t;
    }

    public static void main(String[] args) {
        System.out.println("\nExercise 2 - BST class");

        test("BST is a BinaryTree (inheritance)", () -> {
            BinaryTree t = new BST();          // must compile: BST extends BinaryTree
            eq(null, t.getRoot());
            eq(-1, t.height());
        });

        System.out.println("\nExercise 2 - BST.add");

        test("add to an empty BST creates the root", () -> {
            BST t = bst(50);
            eq(50, t.getRoot().getElement());
        });

        test("add places values according to the BST invariant", () -> {
            BST t = bst(50, 30, 70, 20, 40, 60, 80);
            eq(30, at(t, "L"));
            eq(70, at(t, "R"));
            eq(20, at(t, "LL"));
            eq(40, at(t, "LR"));
            eq(60, at(t, "RL"));
            eq(80, at(t, "RR"));
            eq(2, t.height());
        });

        test("add ignores duplicate values", () -> {
            BST t = bst(50, 30, 70);
            t.add(30); t.add(50);
            eq(asList(50, 30, 70), preorder(t.getRoot()));
        });

        test("add in increasing order builds a chain to the right", () -> {
            BST t = bst(1, 2, 3, 4);
            eq(4, at(t, "RRR"));
            eq(3, t.height());
        });

        test("add is overridden (called through a BinaryTree variable)", () -> {
            BinaryTree t = new BST();
            t.add(50); t.add(30); t.add(70); t.add(20); t.add(10);
            // With BST.add, 10 goes left of 20. (BinaryTree.add would put it right of 30.)
            eq(10, at(t, "LLL"));
            eq(null, at(t, "LR"));
        });

        test("in-order traversal of the BST is sorted", () -> {
            BST t = bst(8, 3, 10, 1, 6, 14, 4, 7, 13);
            eq(asList(1, 3, 4, 6, 7, 8, 10, 13, 14), inorder(t.getRoot()));
        });

        System.out.println("\nExercise 2 - BST.search");

        test("search on an empty BST returns null", () ->
            eq(null, new BST().search(5)));

        test("search returns the node itself when found", () -> {
            BST t = bst(50, 30, 70, 20, 40);
            check(t.search(50) == t.getRoot(), "should return the root node");
            check(t.search(40) == t.getRoot().getLeft().getRight(), "should return the node object for 40");
        });

        test("search returns null for missing values (smaller, larger, in between)", () -> {
            BST t = bst(50, 30, 70, 20, 40);
            eq(null, t.search(10));
            eq(null, t.search(99));
            eq(null, t.search(45));
        });

        test("random values: in-order sorted, search matches reference", () -> {
            Random rng = new Random(272);
            BST t = new BST();
            TreeSet<Integer> ref = new TreeSet<>();
            for (int i = 0; i < 500; i++) {
                int v = rng.nextInt(1000) - 500;
                t.add(v);
                ref.add(v);
            }
            check(new ArrayList<>(ref).equals(inorder(t.getRoot())),
                  "in-order traversal is not the sorted list of distinct values added");
            for (int v = -510; v <= 510; v++) {
                BinaryTree.Node found = t.search(v);
                if (ref.contains(v)) {
                    check(found != null && found.getElement() == v, "should find " + v);
                } else {
                    check(found == null, "should not find " + v);
                }
            }
        });

        summary("Exercise 2 public tests");
    }
}
