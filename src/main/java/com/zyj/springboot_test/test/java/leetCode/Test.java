package com.zyj.springboot_test.test.java.leetCode;


import java.util.*;

public class Test {
    //List<节点idList<关联的节点id>>
    ArrayList<List<Integer>> idToVNodeMap = new ArrayList<>();

    Map<String, Integer> strToIdMap = new HashMap<String, Integer>();
    int id = 0;//字符串的唯一id,同时也是idToVNodeMap里面的索引

    /**
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

        Queue<Integer> ids = new LinkedList<>();//队列里面存的是Id
        ((LinkedList<Integer>) ids).offer(startid);

        while (!ids.isEmpty()) {
            Integer curretnStepId = ids.poll();
            if (strToIdMap.get(endWord).equals(curretnStepId)) {
                //找到结果，返回
                return steps[curretnStepId];
            }

            List<Integer> relationIds = idToVNodeMap.get(curretnStepId);//关联的所有节点

        }

    }

    //在图中添加可能的单词
    private void addWord(String s) {
        int id1 = id;
        if (!strToIdMap.containsKey(s)) {
            strToIdMap.put(s, id++);
            createRelation(s, id1);
        }
        char[] chars = s.toCharArray();
        //建立虚拟节点(所有可能被替换的字符)
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            chars[i] = '*';
            String vNode = new String(chars);//虚拟节点
            strToIdMap.put(vNode, id++);

            int id2 = id;
            createRelation(vNode,id1);
            chars[i] = temp;//把节点替换回来
            // 添加相互之间的映射关系
            idToVNodeMap.get(id1).add(id2);
            idToVNodeMap.get(id2).add(id1);

        }
    }

    private void createRelation(String vNode, int id1) {
        if (strToIdMap.containsKey(vNode)) {
            //如果这个节点是已经存在的，那就把这个已存在的节点(只会是虚拟节点)和当前的节点链接
            Integer id = strToIdMap.get(vNode);
            idToVNodeMap.get(id).add(id1);
        } else {
            idToVNodeMap.add(new ArrayList<>());//在id对应的索引处插入一个对应的关联List(id从0递增)
        }

    }
}
