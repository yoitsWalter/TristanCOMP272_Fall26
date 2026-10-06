# COMP 272/400C — Trees Assignment

## Binary Trees and Binary Search Trees

### Submission

Submit the following two files:

1. `BinaryTree.java` — the provided class with `add` and `search` completed for Exercise 1.
2. `BST.java` — your new Binary Search Tree class for Exercise 2.

Your written answers go in **comments inside your Java files**, directly after the method they analyze. Your explanations must identify the input size and justify every complexity claim. A bound without an explanation is not a complete answer.

Your code will be graded with the provided public tests **and with additional hidden tests**. Passing all public tests does not guarantee full marks. Code that does not compile receives 0 for the automated tests. Do not use `java.util.TreeSet`, `TreeMap`, or any other library tree or sorted structure.

---

## Exercise 1 — A Binary Tree of Integers

The provided `BinaryTree.java` is the binary tree class we used in class, now storing integers (`int`) instead of a generic type `T`. Two small changes were made so that the class can be extended:

- `root` is now `protected` instead of `private`, so that subclasses can use it.
- A no-argument constructor `BinaryTree()` creates an empty tree.

Everything else — `Node`, `getRoot()`, `height()`, and `printTree()` — is unchanged. Do **not** modify the provided code or any method name or signature. You may add private helper methods, and you may use `java.util.Queue` with `ArrayDeque` or `LinkedList`.

### Part A — Add at the first free position in level order

#### Background: level-order traversal

The traversals seen so far (pre-order, in-order, post-order) go **deep** first: they follow one branch down to a leaf before coming back up. A **level-order traversal** (also called a *breadth-first traversal*) goes **wide** first: it visits the nodes one **level** at a time.

The **level** (or depth) of a node is its distance, in edges, from the root. The root is on level 0, its children on level 1, their children on level 2, and so on. Level order visits every node of level 0, then every node of level 1, then level 2, and so on. Within a level, nodes are visited from **left to right**.

For example, consider this tree:

```text
          10              level 0
        /    \
      20      30          level 1
     /  \       \
   40    50      60       level 2
        /
      70                  level 3
```

- Level order: `10, 20, 30, 40, 50, 60, 70`
- Pre-order, for comparison: `10, 20, 40, 50, 70, 30, 60`

Notice that level order visits `30` right after `20`, even though `20` still has children left to visit.

#### Implementing level order with a queue

Recursion is not a natural fit for level order, because the next node to visit is often in a completely different branch of the tree. Instead, we use a **queue**.

Put the root in the queue. Then, while the queue is not empty, remove the node at the front, visit it, and add its left and right children (if any) to the back of the queue. Because children always join the back of the queue, the nodes come out level by level, from left to right. A `Queue<Node>` implemented by an `ArrayDeque` (already imported in `BinaryTree.java`) works well.

#### Your task

1. Read the provided `BinaryTree.java` file.
2. Implement the following method:

```java
public void add(int value)
```

The method adds a new node containing `value` at the **first free position in level order**:

- If the tree is empty, the new node becomes the root.
- Otherwise, traverse the tree in level order using a queue. Each time a node is removed from the queue:
  - if its **left** child is empty, attach the new node as its left child and **stop**;
  - otherwise, if its **right** child is empty, attach the new node as its right child and **stop**;
  - otherwise, add its left and right children to the queue and continue.
- Duplicate values are allowed.

The method must work on any tree, including trees built by hand with the `BinaryTree(Node root)` constructor.

**Example 1.** Adding `6` to the hand-built tree on the left produces the tree on the right:

```text
      1                   1
    /   \               /   \
   2     3             2     3
  / \                 / \   /
 4   5               4   5 6
```

| Step | Node removed | Free child? | Queue afterwards |
|---|---|---|---|
| start | — | — | `[1]` |
| 1 | `1` | no (both children present) | `[2, 3]` |
| 2 | `2` | no (both children present) | `[3, 4, 5]` |
| 3 | `3` | **yes — left** → attach `6` and stop | — |

A pre-order traversal would have reached `4` before `3` and placed `6` under `4`. That is **not** what is asked.

**Example 2.** Adding `1, 2, 3, 4, 5, 6, 7` to an empty tree fills each level from left to right before starting the next one (output of `printTree()`):

```text
1
├── L: 2
│   ├── L: 4
│   └── R: 5
└── R: 3
    ├── L: 6
    └── R: 7
```

### Part B — Search

1. Implement the following method:

```java
public Node search(int value)
```

2. The method returns the **node** whose element equals `value`, or `null` if no such node exists (including when the tree is empty).
3. Use a pre-order traversal and return the **first** match found. The method must not modify the tree.
4. Compile and run the public tests for Exercise 1:

```text
javac BinaryTree.java TestSupport.java PublicTestsBinaryTree.java
java PublicTestsBinaryTree
```

**Point to consider:** Because duplicates are allowed, a value can appear several times. The first match in pre-order is the one that must be returned.

### Part C — Analysis

Let:

- $n$ be the number of nodes in the tree.

Determine the running time of `search` in the **best** case and in the **worst** case. Describe an input that produces the worst case, and justify your answer.

Write your answer as a comment in `BinaryTree.java`, directly after the `search` method, in the space marked `Exercise 1, Part C`.

---

## Exercise 2 — A Binary Search Tree That Inherits from `BinaryTree`

A **Binary Search Tree (BST)** satisfies the following invariant: for every node, every value in its left subtree is **smaller** than the node's value, and every value in its right subtree is **larger**. Your BST stores **distinct** values.

In this exercise you create a new file, `BST.java`, containing a class that represents a BST of integers and **inherits** from `BinaryTree`.

### Part A — Class declaration

1. Create the file `BST.java`.
2. Declare `public class BST` so that it inherits from `BinaryTree`.
3. Provide a public no-argument constructor `BST()` that creates an empty tree by calling the superclass constructor.
4. Annotate each overridden method with `@Override`.
5. Do **not** redeclare `root` or copy code from `BinaryTree`. Use the inherited `root`, the `Node` class, and its getters and setters.

### Part B — Override `add`

1. Override the following method:

```java
@Override
public void add(int value)
```

2. Insert `value` so that the BST invariant is preserved:
   - If the tree is empty, the new node becomes the root.
   - Otherwise, start at the root and move **left** if `value` is smaller than the current element, or **right** if it is larger, until an empty position is reached. Attach the new node there.
   - If `value` is already in the tree, do nothing.

For example, adding `50, 30, 70, 20, 40, 60, 80` to an empty `BST` produces:

```text
50
├── L: 30
│   ├── L: 20
│   └── R: 40
└── R: 70
    ├── L: 60
    └── R: 80
```

### Part C — Override `search`

1. Override the following method:

```java
@Override
public Node search(int value)
```

2. The method returns the node containing `value`, or `null` if it is not in the tree.
3. Your method must **use the BST invariant**: at each node, continue into only **one** subtree.
4. Compile and run the public tests for Exercise 2:

```text
javac *.java
java PublicTestsBST
```

**Point to consider:** The `search` inherited from `BinaryTree` already returns correct answers on a BST. A solution that visits the whole tree is correct but does not meet the requirement of this part.

### Part D — Algorithmic analysis

Let:

- $n$ be the number of nodes in the BST;
- $h$ be the height of the BST.

Answer the following questions in a comment in `BST.java`, directly after your `search` method. Draw trees with text in the comment.

1. Determine the running time of `BST.search` in terms of $h$. Then express it in terms of $n$:
   - when the tree is balanced;
   - in the worst case.
2. Give an insertion order of the values $1, 2, \dots, 7$ that produces the worst case, and draw the resulting tree.
3. Compare your answer with Exercise 1, Part C. Why is it worth overriding `search` even though the inherited version returns correct answers?
4. Explain what happens in the following code, and why. Which `add` method is executed? Draw the resulting tree.

```java
BinaryTree t = new BST();
t.add(50);
t.add(70);
t.add(30);
```

---

## Testing Your Code

Testing is part of programming, not a step you do at the very end. Programmers who test a little at a time find their mistakes while they are still small and easy to locate. This section explains how to use the tests in the starter package and how to go beyond them.

### Test early, test often

Do not write all your methods and then run the tests once. Instead, work in small steps:

1. Implement **one** method (for example, `BinaryTree.add`).
2. Compile and run the tests right away.
3. Fix any failing test for that method before moving on to the next one.

A good order for this assignment is: `BinaryTree.add`, then `BinaryTree.search`, then create `BST.java` with its constructor, then `BST.add`, and finally `BST.search`.

### Running the public tests

The starter package contains three test files. Keep them in the same folder as your code, but do **not** modify or submit them.

| File | Purpose |
|---|---|
| `PublicTestsBinaryTree.java` | Tests `add` and `search` in `BinaryTree` (Exercise 1). |
| `PublicTestsBST.java` | Tests your `BST` class (Exercise 2). It compiles only once `BST.java` exists. |
| `TestSupport.java` | Helper code shared by both test files: it runs each test and prints the results. |

For Exercise 1 (you can do this before `BST.java` exists):

```text
javac BinaryTree.java TestSupport.java PublicTestsBinaryTree.java
java PublicTestsBinaryTree
```

For Exercise 2, once `BST.java` exists:

```text
javac *.java
java PublicTestsBST
```

If `javac` reports an error, fix it first: the tests cannot run until all your files compile.

### Reading the test output

Each test prints one line. Its **name** tells you what behaviour is being checked, so read it carefully.

At the start, before you have written any code, every test fails with `not implemented`. This is normal:

```text
  FAIL  add to an empty tree creates the root  (not implemented: TODO: BinaryTree.add)
```

When a method runs but gives a wrong result, the line shows what the test **expected** and what your code **produced**:

```text
  FAIL  add on a hand-built tree uses LEVEL-order, not pre-order  -> expected <6> but got <null>
```

Here, the test expected `6` to be the left child of `3`, but nothing was there: the new node was placed somewhere else. When you see a message like this, open `PublicTestsBinaryTree.java`, find the test with the same name, and read the tree it builds (it is drawn in a comment). Then trace your code on that tree **by hand**, on paper, and compare your result with the expected one.

When all the tests of a file pass, the summary line shows no failures:

```text
Exercise 1 public tests: 12 passed, 0 failed (of 12)
```

### Writing your own tests

The public tests do not cover every situation. **Your code will also be graded with additional hidden tests**, so passing the public tests is necessary but not sufficient. Get into the habit of asking: *"What input could break my method?"*

Write a small `main` method (in a separate file, e.g. `MyTests.java`, which you do not submit) that builds trees, calls your methods, and displays the result with `printTree()`. Compare what you see with what you expect **before** running the code. Try at least these cases:

- an **empty** tree and a tree with a **single** node;
- a hand-built tree where some nodes have only a left child or only a right child;
- **duplicate** values (for `BinaryTree`) and values already present (for `BST`);
- **negative** values and values that are not in the tree (for `search`);
- values added in increasing or decreasing order (for `BST`);
- a larger tree, for example 15 or 20 values.

For example:

```java
public class MyTests {
    public static void main(String[] args) {
        BinaryTree t = new BinaryTree();
        for (int i = 1; i <= 9; i++) {
            t.add(i);
        }
        t.printTree();                         // does the shape match your drawing?
        System.out.println(t.search(8) != null);  // expected: true
        System.out.println(t.search(42));         // expected: null
    }
}
```

### Before you submit

- [ ] All your files compile without errors.
- [ ] All public tests pass for both exercises.
- [ ] You tested the edge cases listed above with your own tests.
- [ ] Your analysis answers are written as comments after the `search` methods.
- [ ] You submit only `BinaryTree.java` and `BST.java`.

---

## Grading Rubric

| Exercise | Part | Points |
|---|---|---|
| Exercise 1 | Part A — `add` | 20 |
| | Part B — `search` | 15 |
| | Part C — Analysis | 5 |
| Exercise 2 | Part A — Class declaration | 10 |
| | Part B — Override `add` | 20 |
| | Part C — Override `search` | 15 |
| | Part D — Analysis (Q1: 2, Q2: 1, Q3: 1, Q4: 1) | 5 |
| Code quality | | 10 |
| **Total** | | **100** |
