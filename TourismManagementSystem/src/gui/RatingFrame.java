package gui;

import javax.swing.*;

public class RatingFrame extends JFrame {
    public RatingFrame() {
        setTitle("Give Rating");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new java.awt.FlowLayout());

        JTextField guideField = new JTextField(10);
        JTextField scoreField = new JTextField(5);
        JTextField commentField = new JTextField(15);
        JButton submitBtn = new JButton("Submit Rating");

        submitBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Rating Submitted!");
            dispose();
        });

        add(new JLabel("Guide ID:"));
        add(guideField);
        add(new JLabel("Score 1-5:"));
        add(scoreField);
        add(new JLabel("Comment:"));
        add(commentField);
        add(submitBtn);
    }
}
