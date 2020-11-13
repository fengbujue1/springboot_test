package com.zyj.springboot_test.test.java.leetCode;


import java.util.*;

public class LadderLength {
    /**
     *
     * 127. 单词接龙
     *
     * 给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
     *
     * 每次转换只能改变一个字母。
     * 转换过程中的中间单词必须是字典中的单词。
     * 说明:
     *
     * 如果不存在这样的转换序列，返回 0。
     * 所有单词具有相同的长度。
     * 所有单词只由小写字母组成。
     * 字典中不存在重复的单词。
     * 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
     *
     * 算法：广度优先算法，配合图
     *
     *  1.建立图  每个单词如果和另一个存在一个单词的区别（可相互替换），就用一条线连接起来
     *  2.如果字典中不存在 endWord,证明不存在到达结果的路径，return 0
     *  3.在图中建立虚拟节点,虚拟节点代表 某个单词可能的转换形式
     *      建立虚拟节点，如 abc:存在 三个虚拟节点
     *          *bc,a*b,ab*
     *  4.将虚拟节点和 真实节点进行相互连接
     *  5.广度优先算法：
     *      5.1从初始节点开始，将所有可能连接的节点加入 一个先入先出的 队列中
     *          （此队列中 某段连续区间的元素 代表的就是某段步数的所有可能点）
     *      5.2循环 从队列头部取出数据，观察它是否就是终点：
     *          5.2.1如果不是：把这个节点的所有可能连接点加入队列尾部，在下一个遍历阶段再去寻找
     *          5.2.2如果是：那这条路线就是最短步数，遍历结束，得出结果
     *
     *
     */


    //List<节点idList<关联的节点id>>
    ArrayList<List<Integer>> idToVNodeMap = new ArrayList<>();

    Map<String, Integer> strToIdMap = new HashMap<String, Integer>();
    int id = 0;//字符串的唯一id,同时也是idToVNodeMap里面的索引

    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        String[] strings = {"hot", "dot", "dog", "lot", "log", "cog"};
        List<String> wordList = Arrays.asList(strings);

        LadderLength test = new LadderLength();
        System.out.println(test.ladderLength(beginWord, endWord, wordList));
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }
        addWord(beginWord);

        for (String s : wordList) {
            addWord(s);
        }
        int startid = 0;
        int[] steps = new int[idToVNodeMap.size()];
        Arrays.fill(steps, Integer.MIN_VALUE);
        steps[0] = 0;

        Queue<Integer> ids = new LinkedList<>();//队列里面存的是Id
        ids.offer(startid);
        int endId = strToIdMap.get(endWord);

        while (!ids.isEmpty()) {
            int curretnStepId = ids.poll();
            if (endId == curretnStepId) {
                //找到结果，返回
                return steps[curretnStepId] / 2 + 1;
            }

            List<Integer> relationIds = idToVNodeMap.get(curretnStepId);//关联的所有节点的id
            for (int x : relationIds) {
                if (steps[x] == Integer.MIN_VALUE) {
                    steps[x] = steps[curretnStepId] + 1;
                    ids.offer(x);
                }
            }
        }
        return 0;
    }

    //在图中添加可能的单词
    private void addWord(String s) {
        createRelation(s);
        int id1 = strToIdMap.get(s);
//        if (!strToIdMap.containsKey(s)) {
//            strToIdMap.put(s, id++);
//            createRelation(s);
//        }
        char[] chars = s.toCharArray();
        //建立虚拟节点(所有可能被替换的字符)
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            chars[i] = '*';
            String vNode = new String(chars);//虚拟节点
            createRelation(vNode);
            int id2 = strToIdMap.get(vNode);
            chars[i] = temp;//把节点替换回来
            // 添加相互之间的映射关系
            idToVNodeMap.get(id1).add(id2);
            idToVNodeMap.get(id2).add(id1);

        }
    }

    private void createRelation(String vNode) {
        if (!strToIdMap.containsKey(vNode)) {
            idToVNodeMap.add(new ArrayList<>());//在id对应的索引处插入一个对应的关联List(id从0递增)
            strToIdMap.put(vNode, id++);
        }

    }
}
