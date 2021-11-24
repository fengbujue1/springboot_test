package com.zyj.springboot_test.test.java;


import java.io.InputStream;
    import java.util.Arrays;
    import java.util.Scanner;

public class ZXTeest {
    public static void main(String[] args) {

        String string = new String("1234567");
        System.out.println(string.length()<6?string:string.substring(string.length()-6));

//        Scanner in = new Scanner(System.in);
//        String line = in.nextLine();
//        char[] chars = line.toCharArray();
//        int[] result = new int[62];
//        Arrays.fill(result, 0);
//        for (int i = 0; i < chars.length; i++) {
//            result[(chars[i] + 1 - 1)-65]++;
//        }
//        for (int i = 0; i < result.length; i++) {
//            if (result[i] > 0) {
//                System.out.print(Character.toChars(i+65));
//                System.out.print(result[i]);
//            }
//        }


    }
}
