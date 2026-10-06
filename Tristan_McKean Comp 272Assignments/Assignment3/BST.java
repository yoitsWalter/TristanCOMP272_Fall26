// Part A

public class BST extends BinaryTree {
    public BST() {
        super();
    }
// Part B
    @Override 
    public void add(int value) {
        if (root == null) {
            root = new Node(value);
            return;
    }
        Node current = root;
        while (true) {
            if (value < current.getElement()) {
                if (current.getLeft() == null) {
                    current.setLeft(new Node(value));
                    return;
                }
                current = current.getLeft();
            }
            else if (value > current.getElement()) {
                if (current.getRight() == null) {
                    current.setRight(new Node(value));
                    return;

                }
                current = current.getRight();
            }
            else {
                return;
            }

        }

    }
// Part C
    @Override
    public Node search(int value) {
        Node current = root;

        while (current != null) {
            if (value == current.getElement()) {
                return current;
            }
            else if (value < current.getElement()) {
                current = current.getLeft();
            }
            else {
                current = current.getRight();
            }
            
        }
        return null;




    }

   
}

/** 
1. Determine the running time of BST.search in terms of h. Then express it in terms of n:
- when the tree is balanced;
- in the worst case.

In terms of h, the loop has to pass through each layer of the tree, so the running time is O(h).

When a tree is balanced, the run time is h = log2 n. Therefore, it is O(log n).

The worst case is when a tree is chain. H = n - 1, so O(n) is the worst case.

2. Give an insertion order of the values 
1, 2, …, 7
 that produces the worst case, and draw the resulting tree.

 1 
 - R: 2
    --- R: 3
        --- R: 4
            --- R: 5
                --- R: 6
                    --- R: 7

h = 6.
O(n) because you have to visit all 7 nodes.


Compare your answer with Exercise 1, Part C. Why is it worth overriding search even though the inherited version returns correct answers?

Even though the inherited version returns correct answers, the override uses BST ordering, which is faster. BST shows that on a balanced tree, O(log n) is the fastest case, 
and at worst it is O(n), which is the same as for BinaryTree.search. So the worst can be better for BST, but BinaryTree.search cannot be any better. 

Explain what happens in the following code, and why. Which add method is executed? Draw the resulting tree.
BinaryTree t = new BST();
t.add(50);
t.add(70);
t.add(30);

1. BST.add is the method being executed.
2. Because the object is a BST. It knows to override because of this.
3. 
50
-  L: 30
-  R: 70

*/
