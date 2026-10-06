import java.util.List;

import static java.util.Arrays.asList;

/**
 * PUBLIC TESTS — Exercise 1 (BinaryTree.add and BinaryTree.search).
 *
 *     javac BinaryTree.java TestSupport.java PublicTestsBinaryTree.java
 *     java PublicTestsBinaryTree
 *
 * Passing these tests does NOT guarantee full marks: your code will ALSO be
 * graded with additional hidden tests. Write your own tests as well.
 */
public class PublicTestsBinaryTree extends TestSupport {

    public static void main(String[] args) {
        System.out.println("\nExercise 1 - BinaryTree.add (first free position in level-order)");

        test("add to an empty tree creates the root", () -> {
            BinaryTree t = new BinaryTree();
            t.add(42);
            check(t.getRoot() != null, "root is still null");
            eq(42, t.getRoot().getElement());
            eq(0, t.height());
        });

        test("add fills left child first, then right child", () -> {
            BinaryTree t = new BinaryTree();
            t.add(1); t.add(2); t.add(3);
            eq(2, at(t, "L"));
            eq(3, at(t, "R"));
        });

        test("add 1..7 into an empty tree gives the expected shape", () -> {
            //  1
            //  ├── L: 2
            //  │   ├── L: 4
            //  │   └── R: 5
            //  └── R: 3
            //      ├── L: 6
            //      └── R: 7
            BinaryTree t = new BinaryTree();
            for (int i = 1; i <= 7; i++) t.add(i);
            eq(asList(1, 2, 4, 5, 3, 6, 7), preorder(t.getRoot()));
            eq(4, at(t, "LL"));
            eq(5, at(t, "LR"));
            eq(6, at(t, "RL"));
            eq(7, at(t, "RR"));
            eq(2, t.height());
        });

        test("add on a hand-built tree uses LEVEL-order, not pre-order", () -> {
            //      1
            //     / \
            //    2   3        level-order reaches 3 before 4,
            //   / \           so 6 becomes 3's LEFT child
            //  4   5          (pre-order would have put it under 4)
            BinaryTree t = new BinaryTree(n(1, n(2, n(4), n(5)), n(3)));
            t.add(6);
            eq(6, at(t, "RL"));
            eq(null, at(t, "LLL"));
        });

        test("add fills a missing RIGHT child when the left one exists", () -> {
            //     10
            //    /  \
            //  20    30
            //  /       \
            // 40        50
            BinaryTree t = new BinaryTree(n(10, n(20, n(40), null), n(30, null, n(50))));
            t.add(99);
            eq(99, at(t, "LR"));
            t.add(98);
            eq(98, at(t, "RL"));
        });

        test("add visits each level from left to right", () -> {
            //       1
            //     /   \
            //    2     3
            //     \   / \
            //      5 6   7     2 has a free LEFT slot: it is found first
            BinaryTree t = new BinaryTree(n(1, n(2, null, n(5)), n(3, n(6), n(7))));
            t.add(8);
            eq(8, at(t, "LL"));
            t.add(9);                // level 2 is now full: 9 goes under 8 (the leftmost node of level 2)
            eq(9, at(t, "LLL"));
        });

        test("add allows duplicate values", () -> {
            BinaryTree t = new BinaryTree();
            t.add(5); t.add(5); t.add(5);
            eq(asList(5, 5, 5), preorder(t.getRoot()));
        });

        System.out.println("\nExercise 1 - BinaryTree.search");

        test("search on an empty tree returns null", () ->
            eq(null, new BinaryTree().search(3)));

        test("search finds the root, an inner node and a leaf", () -> {
            BinaryTree.Node leaf = n(40);
            BinaryTree.Node inner = n(20, leaf, null);
            BinaryTree t = new BinaryTree(n(10, inner, n(30, null, n(50))));
            check(t.search(10) == t.getRoot(), "should return the root node itself");
            check(t.search(20) == inner, "should return the node object for 20");
            check(t.search(40) == leaf, "should return the node object for 40");
            eq(50, t.search(50).getElement());
        });

        test("search returns null for a missing value", () -> {
            BinaryTree t = new BinaryTree(n(10, n(20), n(30)));
            eq(null, t.search(25));
        });

        test("search with duplicates returns the FIRST match in pre-order", () -> {
            //      5
            //     / \
            //    7   9   <- second 9 in pre-order
            //   /
            //  9         <- first 9 in pre-order
            BinaryTree.Node firstNine = n(9);
            BinaryTree t = new BinaryTree(n(5, n(7, firstNine, null), n(9)));
            check(t.search(9) == firstNine, "must return the 9 under 7 (visited first in pre-order)");
        });

        test("search does not modify the tree", () -> {
            BinaryTree t = new BinaryTree();
            for (int i = 1; i <= 7; i++) t.add(i);
            List<Integer> before = preorder(t.getRoot());
            t.search(6); t.search(100);
            eq(before, preorder(t.getRoot()));
        });

        summary("Exercise 1 public tests");
    }
}
