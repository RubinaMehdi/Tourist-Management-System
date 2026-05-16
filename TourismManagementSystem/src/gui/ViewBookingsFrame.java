package gui;

import javax.swing.*;
import javax.swing.table.*;

public class ViewBookingsFrame extends JFrame {
    public ViewBookingsFrame() {
        setTitle("My Bookings");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = { "Booking ID", "Package ID", "Status" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
    }
}
