package com.zyj.springboot_test.test.java.leetCode;

public class CountNodes {

    /**
     * 222. 完全二叉树的节点个数
     * 说明：
     *
     * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，
     * 其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。
     * 若最底层为第 h 层，则该层包含 1~ 2h 个节点。
     *
     *
     * 示例:
     *
     * 输入:
     *     1
     *    / \
     *   2   3
     *  / \  /
     * 4  5 6
     *
     * 输出: 6
     */
    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left1 = new TreeNode(2);
        TreeNode right1 = new TreeNode(3);
        TreeNode left2 = new TreeNode(4);
        TreeNode right2 = new TreeNode(5);
        TreeNode left3 = new TreeNode(6);
        root.left = left1;
        root.right = right1;
        left1.left = left2;
        left1.right = right2;
        right2.left = left3;
        CountNodes countNodes = new CountNodes();

        System.out.println(countNodes.countNodes(root));;
    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int count = 1;
        TreeNode left = root.left;
        TreeNode right = root.right;
        if (left != null) {
            count+=countNodes(left);
        }
        if (right != null) {
            count+=countNodes(right);
        }
        return count;
    }
}
