public class ArithmeticExpression {
    private final BinaryTree<String> tree;

    public ArithmeticExpression(BinaryTree<String> tree) {
        this.tree = tree;
    }

    public String toExpressionString() {
        return toExpressionString(tree.getRoot());
    }

    private String toExpressionString(BinaryTree.Node<String> node) {
    private String toExpressionString(BinaryTree.Node<String> node) {
    String result;
    if (node == null) {
        result = "";
    }
    else if (node.getLeft() == null && node.getRight() == null) {
        result = node.getElement();
    }
    else {
        String left = toExpressionString(node.getLeft());
        String right = toExpressionString(node.getRight());
        result = "(" + left + " " + node.getElement() + " " + right + ")";
    }
    return result;
}
        String result;
        if (node == null) {
            result = "";
        }
        else if (node.getLeft() == null && node.getRight() == null) {
            result = node.getElement();
        }
        else {
            String left = toExpressionString(node.getLeft());
            String right = toExpressionString(node.getRight());
            result =  "(" + left + " " + node.getElement() + " " + right + ")";
        }
        return result;
>>>>>>> 9209d5b244c2c3825227d930658aa988d454c48e
    }

    public static void main(String[] args) {
        // Represents ((2 * (a - 1)) + (3 * b))
        BinaryTree.Node<String> plus = new BinaryTree.Node<>("+");
        BinaryTree.Node<String> timesLeft = new BinaryTree.Node<>("*");
        BinaryTree.Node<String> minus = new BinaryTree.Node<>("-");
        BinaryTree.Node<String> timesRight = new BinaryTree.Node<>("*");

        plus.setLeft(timesLeft);
        plus.setRight(timesRight);
        timesLeft.setLeft(new BinaryTree.Node<>("2"));
        timesLeft.setRight(minus);
        minus.setLeft(new BinaryTree.Node<>("a"));
        minus.setRight(new BinaryTree.Node<>("1"));
        timesRight.setLeft(new BinaryTree.Node<>("3"));
        timesRight.setRight(new BinaryTree.Node<>("b"));

        BinaryTree<String> tree = new BinaryTree<>(plus);
        tree.printTree();
        System.out.println("Height: " + tree.height()); // 3

        ArithmeticExpression expression = new ArithmeticExpression(tree);
        String actual = expression.toExpressionString();
        String expected = "((2 * (a - 1)) + (3 * b))";
        System.out.println("Expression: " + actual);
        System.out.println("Matches expected: " + expected.equals(actual));
    }
}
