package com.zyj.springboot_test.test.java.leetCode;

/**
 * 恢复空格
 *
 * 像句子"I reset the computer. It still didn’t boot!"已经变成了"iresetthecomputeritstilldidntboot"。
 * 在处理标点符号和大小写之前，你得先把它断成词语。
 * 当然了，你有一本厚厚的词典dictionary，不过，有些词没在词典里。
 * 假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
 *
 * 注意：本题相对原题稍作改动，只需返回未识别的字符数
 *
 * 输入：
 * dictionary = ["looked","just","like","her","brother"]
 * sentence = "jesslookedjustliketimherbrother"
 * 输出： 7
 * 解释： 断句后为"jess looked just like tim her brother"，共7个未识别字符。
 *
 */
public class TestRespace {
    public static void main(String[] args) {
        //测试substring方法
//        String s = "1234567";
//        String substring = s.substring(1, 2);
//        System.out.println(s);
//        System.out.println(substring);
//        System.out.println(s.subSequence(1, 2));
        String[] dictionary = new String[]{"looked", "just", "like", "her", "brother"};
        String sentence = "jesslookedjustliketimherbrother";
        int unidentification = respace(dictionary, sentence);
        System.out.println(unidentification);

    }

    public static int respace(String[] dictionary, String sentence) {
        int unidentification = 0;
        char[] sentenceChars = sentence.toCharArray();
        for (int i = 0; i < dictionary.length; i++) {
            String s = dictionary[i];
            char[] chars = s.toCharArray();
            for (int j = 0; j < sentenceChars.length; j++) {
                if (sentenceChars[j] == chars[0]) {
                    check(sentenceChars, chars, j);
                }
            }
        }
        for (int j = 0; j < sentenceChars.length; j++) {
            if (sentenceChars[j] != ' ') {
                unidentification++;
            }
        }
        return unidentification;
    }
    public static boolean check(char[] sentenceChars,char[] chars,int indexOfSenten) {
        for (int i = 0; i < chars.length; i++) {
            if (sentenceChars[indexOfSenten + i] != chars[i]) {
                return false;
            }
        }
        for (int i = 0; i < chars.length; i++) {
            sentenceChars[indexOfSenten + i] = ' ';
        }
        return true;

    }
}
