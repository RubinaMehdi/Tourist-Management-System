package gui;

import javax.swing.*;
import javax.swing.table.*;

public class ViewTouristsFrame extends JFrame {
    public ViewTouristsFrame() {
        setTitle("View Tourists");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = { "ID", "Name", "Email", "Nationality" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
    }
}
