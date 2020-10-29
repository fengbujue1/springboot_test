package com.zyj.springboot_test.test.java.leetCode;

import java.util.*;

public class SumNumbers {
    /**
     * 题目：求根到叶子节点数字之和
     *
     * 给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。
     *
     * 例如，从根到叶子节点路径 1->2->3 代表数字 123。
     *
     * 计算从根到叶子节点生成的所有数字之和。
     *
     */
      public static class TreeNode {
         int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
      }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(4);
        TreeNode treeNode1 = new TreeNode(9);
        TreeNode treeNode2 = new TreeNode(0);
        treeNode.left = treeNode1;
        treeNode.right = treeNode2;

        TreeNode treeNode3 = new TreeNode(5);
        TreeNode treeNode4 = new TreeNode(1);
        treeNode1.left = treeNode3;
        treeNode1.right = treeNode4;
        System.out.println(sumNumbers3(treeNode));
    }

    /**
     * 广度优先算法实现
     *
     *
     */
    public static int sumNumbers3(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> nodes = new LinkedList<>();
        Queue<Integer> nums = new LinkedList<>();

        TreeNode node = root;
        nodes.offer(root);
        nums.offer(root.val);
        int sum = 0;
        while (!nodes.isEmpty()) {
            TreeNode peekNode = nodes.poll();
            Integer peekVal = nums.poll();
            if (peekNode.left == null && peekNode.right == null) {
                sum += peekVal;
            } else {
                if (peekNode.right != null) {
                    nodes.offer(peekNode.right);
                    nums.offer(peekVal * 10 + peekNode.right.val);
                }
                if (peekNode.left != null) {
                    nodes.offer(peekNode.left);
                    nums.offer(peekVal * 10 + peekNode.left.val);
                }
            }
        }
        return sum;
    }
    /**
     * 深度优先算法的实现
     * @param root
     * @return
     */
    public static int sumNumbers2(TreeNode root) {
        return calc2(root,0);
    }
    public static int calc2(TreeNode root, int preNum) {
        TreeNode node = root;
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return node.val + preNum * 10;
        } else {
            return calc2(node.left, node.val + preNum * 10) + calc2(node.right, node.val + preNum * 10);
        }
    }


    /**
     * 深度优先算法的一种不太理想的实现(自行解答的第一个版本)
     *
     * 其实在 每次走到叶子节点的时候，就可以计算出 一个值，
     * 并且计算方式也只需要将   前一个递归的传入值乘10+当前值 即可
     * 这种 记录节点的方式可以用于   广度优先算法
     *
     * @param root
     * @return
     */
    public static int sumNumbers(TreeNode root) {
        LinkedList<Integer> temp = new LinkedList<>();
        ArrayList<Integer> nums = new ArrayList<>();
        calc(root,temp,nums);
        int sum = 0;
        for (int i = 0; i < nums.size(); i++) {
            sum += nums.get(i);
        }
        return sum;
    }

    public static void calc(TreeNode root, LinkedList<Integer> temp, List<Integer> nums) {
        TreeNode node = root;
        if (node == null) {
            return;
        }
        temp.push(node.val);
        if (node.left != null) {
            calc(node.left, temp, nums);
        }
        if (node.right != null) {
            calc(node.right, temp, nums);
        }
        if (node.left == null && node.right == null) {
            int tempVal = 0;
            for (int i = 0; i < temp.size(); i++) {
                Integer integer = temp.get(temp.size() - i - 1);
                tempVal += integer * Math.pow(10, temp.size() - i - 1);
            }
            nums.add(tempVal);
        }
        temp.pop();
    }
}
