package gui;

import javax.swing.*;

public class PaymentFrame extends JFrame {
    public PaymentFrame() {
        setTitle("Make Payment");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new java.awt.FlowLayout());

        JTextField bookingField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JButton payBtn = new JButton("Pay Now");

        payBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Payment Successful!");
            dispose();
        });

        add(new JLabel("Booking ID:"));
        add(bookingField);
        add(new JLabel("Amount:"));
        add(amountField);
        add(payBtn);
    }
}
