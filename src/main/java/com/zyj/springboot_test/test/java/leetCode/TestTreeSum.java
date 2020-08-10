package com.zyj.springboot_test.test.java.leetCode;

import java.util.LinkedList;
import java.util.Queue;


/**
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例: 
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 */
public class TestTreeSum {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    public static void main(String[] args) {

    }
    public static boolean hasPathSum1(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        Queue<TreeNode> nodes = new LinkedList<>();
        Queue<Integer> sums = new LinkedList<>();
        nodes.offer(root);
        sums.offer(root.val);
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.poll();
            Integer val = sums.poll();
            if (node.right == null && node.left == null) {
                if (sum == val){
                    return true;
                }
               continue;
            }
            if (node.left != null) {
                nodes.offer(node.left);
                sums.offer(node.left.val + val);
            }
            if (node.right != null) {
                nodes.offer(node.right);
                sums.offer(node.right.val + val);
            }
        }
        return false;
    }

    public static boolean hasPathSum2(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        if (root.right == null && root.left == null) {
            if (root.val == sum) {
                return true;
            }
            return false;
        }
        return hasPathSum2(root.right,sum-root.val) || hasPathSum2(root.left,sum-root.val);
    }
}
