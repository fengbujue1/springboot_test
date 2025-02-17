package com.zyj.springboot_test.test.java.inWork;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
   *@Author 周赟吉
   *@Date  2023/11/2  10:11
   *@Company: 九恒星成都信息技术有限公司
   *@Description :规范转dto工具
*/
public class SplitX {
    public static void main(String[] args) throws IOException {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        HashMap<String, String> map = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("###");
            map.put(split[0], line);
        }
        Set<Map.Entry<String, String>> entries = map.entrySet();
        List<Node> rootNodes = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            if (key.split("\\.").length == 1) {
                Node node = new Node();
                node.setKey(key);
                node.setVal(entry.getValue());
                rootNodes.add(node);
            }
        }

        for (int i = 0; i < rootNodes.size(); i++) {
            Node node = rootNodes.get(i);
            fillChild(node, map);
            System.out.println(node);
        }


    }

    /**
     * 填充子节点
     * @param node
     * @param map
     */
    public static void fillChild(Node node, HashMap<String, String> map) {
        if (!hasChild(node, map)) {
            return;
        }
        String[] split = node.getKey().split("\\.");
        int childLength = split.length + 1;
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String[] split1 = key.split("\\.");
            int length = split1.length;
            if (key.startsWith(node.getKey()) && childLength == length) {
                Node childNode = new Node();
                childNode.setKey(key);
                childNode.setVal(map.get(key));
                node.getChilds().add(childNode);
                fillChild(childNode, map);
            }
        }
    }


    /**
     * 是否存在子节点
     * @param node
     * @param map
     * @return
     */
    public static boolean hasChild(Node node, HashMap<String, String> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (key.startsWith(node.getKey()) && key.split("\\.").length > node.getKey().split("\\.").length) {
                return true;
            }
        }
        return false;
    }

    private static String trimStr(String s) {
        if (s.startsWith("-")) {
            return trimStr(s.substring(1));
        }
        return s;
    }

    public static class Node{
        private String key;
        private String val;
        private List<Node> childs = new ArrayList<>();

        public boolean hasChildern() {
            return childs.size() > 0;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public List<Node> getChilds() {
            return childs;
        }

        public void setChilds(List<Node> childs) {
            this.childs = childs;
        }

        @Override
        public String toString() {
            String s;
            if (hasChildern()) {
                String[] split = val.split("###");
                String className = split[1];
                char c = className.charAt(0);

                className = Character.toUpperCase(c) + className.substring(1);
                s = "public static class " + className + " {\n";
                for (Node child : childs) {
                    s += child.toString();
                }
                s += "}\n";
                return s;
            }

            String[] split = val.split("###");
            s = "    /**\n" +
                    "     *" + split[2] + "\n" +
                    "     * 是否必输：" + split[split.length - 1] + "\n" +
                    "     * 备注：" + (split.length >= 5 ? split[5] : "") + "\n" +
                    "     */" + "\n";
            System.out.println();
            System.out.println();
            s += "    @JacksonXmlProperty(localName = \"" + trimStr(split[1]) + "\")"+"\n";
            s += "    private String "  + trimStr(split[1]) + ";"+"\n";
            return s;
        }
    }

}
