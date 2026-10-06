import java.util.ArrayList;
import java.util.List;

/**
 * PROVIDED — small helper used by the public test files. Do not submit.
 * You don't need to read this file.
 */
public class TestSupport {

    static int passed = 0, failed = 0;

    @FunctionalInterface
    interface Test { void run() throws Exception; }

    static void test(String name, Test t) {
        try {
            t.run();
            passed++;
            System.out.println("  PASS  " + name);
        } catch (UnsupportedOperationException e) {
            failed++;
            System.out.println("  FAIL  " + name + "  (not implemented: " + e.getMessage() + ")");
        } catch (AssertionError e) {
            failed++;
            System.out.println("  FAIL  " + name + "  -> " + e.getMessage());
        } catch (Throwable e) {
            failed++;
            System.out.println("  FAIL  " + name + "  -> unexpected " + e);
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    static void eq(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual))
            throw new AssertionError("expected <" + expected + "> but got <" + actual + ">");
    }

    /** Builds a node with the given children (either may be null). */
    static BinaryTree.Node n(int v, BinaryTree.Node l, BinaryTree.Node r) {
        BinaryTree.Node node = new BinaryTree.Node(v);
        node.setLeft(l);
        node.setRight(r);
        return node;
    }

    static BinaryTree.Node n(int v) {
        return new BinaryTree.Node(v);
    }

    /** Element stored at the node reached by following a path like "LR" from the root. */
    static Integer at(BinaryTree t, String path) {
        BinaryTree.Node u = t.getRoot();
        for (char c : path.toCharArray()) {
            if (u == null) return null;
            u = (c == 'L') ? u.getLeft() : u.getRight();
        }
        return u == null ? null : u.getElement();
    }

    static List<Integer> preorder(BinaryTree.Node u) {
        List<Integer> out = new ArrayList<>();
        pre(u, out);
        return out;
    }

    private static void pre(BinaryTree.Node u, List<Integer> out) {
        if (u == null) return;
        out.add(u.getElement());
        pre(u.getLeft(), out);
        pre(u.getRight(), out);
    }

    static List<Integer> inorder(BinaryTree.Node u) {
        List<Integer> out = new ArrayList<>();
        in(u, out);
        return out;
    }

    private static void in(BinaryTree.Node u, List<Integer> out) {
        if (u == null) return;
        in(u.getLeft(), out);
        out.add(u.getElement());
        in(u.getRight(), out);
    }

    static void summary(String label) {
        System.out.println("\n========================================");
        System.out.printf("%s: %d passed, %d failed (of %d)%n", label, passed, failed, passed + failed);
        System.out.println("Reminder: ADDITIONAL HIDDEN TESTS will also be used to grade your code.");
        System.out.println("========================================");
    }
}
