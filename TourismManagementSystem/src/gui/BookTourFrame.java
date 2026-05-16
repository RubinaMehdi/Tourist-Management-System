package gui;

import javax.swing.*;

public class BookTourFrame extends JFrame {
    public BookTourFrame() {
        setTitle("Book Tour");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(new java.awt.FlowLayout());

        JTextField packageField = new JTextField(10);
        JTextField hotelField = new JTextField(10);
        JTextField guideField = new JTextField(10);
        JButton bookBtn = new JButton("Confirm Booking");

        bookBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Booking Confirmed!");
            dispose();
        });

        add(new JLabel("Package ID:"));
        add(packageField);
        add(new JLabel("Hotel ID:"));
        add(hotelField);
        add(new JLabel("Guide ID:"));
        add(guideField);
        add(bookBtn);
    }
}
