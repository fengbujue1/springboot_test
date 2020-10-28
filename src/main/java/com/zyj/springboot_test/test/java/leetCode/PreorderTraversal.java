package com.zyj.springboot_test.test.java.leetCode;

import rx.internal.util.LinkedArrayList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PreorderTraversal {
    /**
     * 给定一个二叉树，返回它的 前序 遍历。
     */

      private static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
          }
      }
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        root.right = treeNode2;
        TreeNode treeNode3 = new TreeNode(3);
        treeNode2.left = treeNode3;


        System.out.println(preorderTraversal2(root));

    }

    /**
     * 迭代法
     * @param root
     * @return
     */
    public static List<Integer> preorderTraversal2(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        LinkedList<TreeNode> stack = new LinkedList<>();

        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                result.add(node.val);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop().right;
        }
        return result;

    }

    /**
     * 递归算法
     * @param root
     * @return
     */
    public static List<Integer> preorderTraversal1(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        recursion(root, result);
        return result;

    }

    private static void recursion(TreeNode treeNode, List<Integer> collection) {
        if (treeNode == null) {
            return;
        }
        collection.add(treeNode.val);
        if (treeNode.left != null) {
            recursion(treeNode.left, collection);
        }
        if (treeNode.right != null) {
            recursion(treeNode.right, collection);
        }
    }
}
