package com.zyj.springboot_test.test.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @Author 周赟吉
 * @Date 2024/9/25 0:09
 * @Description :
 */
public class TT {
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel cards = new JPanel(cardLayout);

    public static void main(String[] args) {
        JFrame frame = new JFrame("问卷调查");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加问卷页面
        addQuestionPages();

        frame.add(cards);
        frame.setVisible(true);
    }

    private static void addQuestionPages() {
        JPanel page1 = new JPanel();
        JTextField answer1 = new JTextField(20);
        page1.add(new JLabel("你喜欢编程吗？"));
        page1.add(answer1);
        JButton nextButton1 = new JButton("下一步");
        nextButton1.addActionListener(e -> {
            cardLayout.show(cards, "page2");
        });
        page1.add(nextButton1);

        JPanel page2 = new JPanel();
        JTextField answer2 = new JTextField(20);
        page2.add(new JLabel("你使用什么编程语言？"));
        page2.add(answer2);
        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(e -> {
            saveToDatabase(answer1.getText(), answer2.getText());
            JOptionPane.showMessageDialog(null, "感谢参与问卷调查！");
        });
        page2.add(submitButton);

        cards.add(page1, "page1");
        cards.add(page2, "page2");
        cardLayout.show(cards, "page1");
    }

    private static void saveToDatabase(String response1, String response2) {
        System.out.println(response1);
        System.out.println(response2);

//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "user", "password");
//             PreparedStatement stmt = conn.prepareStatement("INSERT INTO responses (answer1, answer2) VALUES (?, ?)")) {
//            stmt.setString(1, response1);
//            stmt.setString(2, response2);
//            stmt.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
