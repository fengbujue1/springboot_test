package com.zyj.springboot_test.test.java.AAAA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * We Are A Team（并查集）
 题目描述
 总共有 n 个人在机房，每个人有一个标号（1<=标号<=n），他们分成了多个团队，需要你根据收到的 m 条消息判定指定的两个人是否在一个团队中，
 具体的：

 消息构成为 a b c，整数 a、b 分别代表两个人的标号，整数 c 代表指令
 c == 0 代表 a 和 b 在一个团队内
 c == 1 代表需要判定 a 和 b 的关系，如果 a 和 b 是一个团队，输出一行’we are a team’,如果不是，输出一行’we are not a team’
 c 为其他值，或当前行 a 或 b 超出 1~n 的范围，输出‘da pian zi’
 输入描述
 第一行包含两个整数 n，m(1<=n,m<100000),分别表示有 n 个人和 m 条消息
 随后的 m 行，每行一条消息，消息格式为：a b c(1<=a,b<=n,0<=c<=1)
 输出描述
 c ==1,根据 a 和 b 是否在一个团队中输出一行字符串，在一个团队中输出‘we are a team‘,不在一个团队中输出’we are not a team’
 c 为其他值，或当前行 a 或 b 的标号小于 1 或者大于 n 时，输出字符串‘da pian zi‘
 如果第一行 n 和 m 的值超出约定的范围时，输出字符串”NULL“。
 用例
 输入
5 7
1 2 0
4 5 0
2 3 0
1 2 1
2 3 1
4 5 1
1 5 1
 输出
 we are a team
 we are a team
 we are a team
 we are not a team
 说明	无
 输入
5 6
1 2 0
1 2 1
1 5 0
2 3 1
2 5 1
1 3 2
 输出	we are a team
 we are not a team
 we are a team
 da pian zi
 说明	无
 */
public class Test3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numbers = scanner.nextInt();
        UnionFind unionFind = new UnionFind(numbers);

        int relations = scanner.nextInt();
        for (int i = 0; i < relations; i++) {
            int a =scanner.nextInt();
            int b =scanner.nextInt();
            int c =scanner.nextInt();
            if (c == 0) {
                unionFind.union(a, b);
            } else if (c == 1) {
                System.out.println(unionFind.find(a) == unionFind.find(b) ? "we are a team" : "we are not a team");
            } else {
                System.out.println("da pian zi");
            }

        }
    }

    static class UnionFind {

        int[] parent ;
        int[] rank ;
        public UnionFind(int numbers) {
            this.parent = new int[numbers+1];
            for (int i = 0; i <= numbers; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int number) {
            if (parent[number] != number) {
                parent[number] = find(parent[number]);
            }
            return parent[number];
        }

        public void union(int a, int b) {
            int aRoot = find(a);
            int bRoot = find(b);
            if (aRoot != bRoot) {
                if (rank[aRoot] > rank[bRoot]) {
                    parent[bRoot] = parent[aRoot];
                } else if (rank[aRoot] < rank[bRoot]) {
                    parent[aRoot] = parent[bRoot];
                } else {
                    parent[aRoot] = parent[bRoot];
                    rank[aRoot]++;
                }
            }

        }


    }
}
