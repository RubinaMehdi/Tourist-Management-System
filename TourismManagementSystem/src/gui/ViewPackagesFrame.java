package gui;

import javax.swing.*;
import javax.swing.table.*;

public class ViewPackagesFrame extends JFrame {
    public ViewPackagesFrame() {
        setTitle("View Packages");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = { "Package ID", "Title", "Price", "Duration" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
    }
}
