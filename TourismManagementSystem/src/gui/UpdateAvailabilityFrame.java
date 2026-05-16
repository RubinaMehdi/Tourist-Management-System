package gui;

import javax.swing.*;

public class UpdateAvailabilityFrame extends JFrame {
    public UpdateAvailabilityFrame() {
        setTitle("Update Availability");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new java.awt.FlowLayout());

        JTextField availField = new JTextField(5);
        JButton updateBtn = new JButton("Update");

        updateBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Availability Updated!");
            dispose();
        });

        add(new JLabel("Available (Y/N):"));
        add(availField);
        add(updateBtn);
    }
}
