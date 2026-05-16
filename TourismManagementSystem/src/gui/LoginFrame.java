package gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Tourism Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel title = new JLabel("Welcome to Tourism Management System");
        JLabel select = new JLabel("Select Role:");

        JButton adminBtn = new JButton("Admin");
        JButton touristBtn = new JButton("Tourist");
        JButton guideBtn = new JButton("Tour Guide");

        adminBtn.addActionListener(e -> {
            dispose();
            new AdminDashboard().setVisible(true);
        });

        touristBtn.addActionListener(e -> {
            dispose();
            new TouristDashboard().setVisible(true);
        });

        guideBtn.addActionListener(e -> {
            dispose();
            new TourGuideDashboard().setVisible(true);
        });

        add(title);
        add(select);
        add(adminBtn);
        add(touristBtn);
        add(guideBtn);
    }
}