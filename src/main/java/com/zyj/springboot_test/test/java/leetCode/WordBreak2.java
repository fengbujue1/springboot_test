package com.zyj.springboot_test.test.java.leetCode;

import java.util.ArrayList;
import java.util.List;

public class WordBreak2 {
    /**
     *单词拆分 II
     *
     * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，
     * 在字符串中增加空格来构建一个句子，
     * 使得句子中所有的单词都在词典中。返回所有这些可能的句子。
     *
     * 说明：
     *
     * 分隔时可以重复使用字典中的单词。
     * 你可以假设字典中没有重复的单词。
     *
     * 示例 1：
     * 输入:
     * s = "catsanddog"
     * wordDict = ["cat", "cats", "and", "sand", "dog"]
     * 输出:
     * [
     *   "cats and dog",
     *   "cat sand dog"
     * ]
     *
     *
     */
    public static void main(String[] args) {
        ArrayList<String> wordDict = new ArrayList<>();
        wordDict.add("cat");
        wordDict.add("cats");
        wordDict.add("and");
        wordDict.add("sand");
        wordDict.add("dog");

        List<String> catsanddog = wordBreak("catsanddog", wordDict);
        for (String s : catsanddog) {
            System.out.println(s);
        }

    }

    public static List<String> wordBreak(String s, List<String> wordDict) {
        int startIndex = 0;
        int endIndex = 0;


        ArrayList<String> result = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        while (startIndex < s.length() && endIndex <= s.length()) {
            String substring = s.substring(startIndex, endIndex);
            if (wordDict.contains(substring)) {
                temp.append(substring);
                temp.append(" ");
                startIndex = endIndex;
            }
            endIndex++;
        }
        result.add(temp.toString());

        return result;
    }
}
