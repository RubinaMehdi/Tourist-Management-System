package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ManageBookingsFrame extends JFrame {

    private DefaultTableModel tableModel;

    public ManageBookingsFrame() {
        setTitle("Manage Bookings");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "Booking ID", "Tourist ID", "Package ID", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        approveBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0)
                tableModel.setValueAt("Approved", row, 3);
            else
                JOptionPane.showMessageDialog(this, "Select a booking!");
        });

        rejectBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0)
                tableModel.setValueAt("Rejected", row, 3);
            else
                JOptionPane.showMessageDialog(this, "Select a booking!");
        });

        btnPanel.add(approveBtn);
        btnPanel.add(rejectBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
