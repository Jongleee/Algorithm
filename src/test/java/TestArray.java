import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArray {
    class TreeNode {
        int x;
        int y;
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    int[][] result;
    int idx;

    public int[][] solution(int[][] nodeinfo) {
        TreeNode[] nodes = buildNodes(nodeinfo);

        Arrays.sort(nodes, new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode n1, TreeNode n2) {
                if (n1.y == n2.y)
                    return n1.x - n2.x;
                else
                    return n2.y - n1.y;
            }
        });
        TreeNode root = buildBinaryTree(nodes);

        result = new int[2][nodeinfo.length];
        idx = 0;
        preorderTraversal(root);
        idx = 0;
        postorderTraversal(root);

        return result;
    }

    private TreeNode[] buildNodes(int[][] nodeinfo) {
        TreeNode[] nodes = new TreeNode[nodeinfo.length];
        for (int i = 0; i < nodeinfo.length; i++) {
            nodes[i] = new TreeNode(nodeinfo[i][0], nodeinfo[i][1], i + 1);
        }
        return nodes;
    }

    private TreeNode buildBinaryTree(TreeNode[] nodes) {
        TreeNode root = nodes[0];
        for (int i = 1; i < nodes.length; i++) {
            insertNode(root, nodes[i]);
        }
        return root;
    }

    private void insertNode(TreeNode parent, TreeNode child) {
        if (parent.x > child.x) {
            if (parent.left == null)
                parent.left = child;
            else
                insertNode(parent.left, child);
        } else {
            if (parent.right == null)
                parent.right = child;
            else
                insertNode(parent.right, child);
        }
    }

    private void preorderTraversal(TreeNode root) {
        if (root != null) {
            result[0][idx++] = root.value;
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
    }

    private void postorderTraversal(TreeNode root) {
        if (root != null) {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            result[1][idx++] = root.value;
        }
    }

    @Test
    void 정답() {
        int[][] nodeinfo = { { 5, 3 }, { 11, 5 }, { 13, 3 }, { 3, 5 },
                { 6, 1 }, { 1, 3 }, { 8, 6 }, { 7, 2 }, { 2, 2 } };
        int[][] result = { { 7, 4, 6, 9, 1, 8, 5, 2, 3 }, { 9, 6, 5, 8, 1, 4, 3, 2, 7 } };

        Assertions.assertArrayEquals(result, solution(nodeinfo));
    }
}
