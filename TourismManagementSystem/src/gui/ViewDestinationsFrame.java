package gui;

import javax.swing.*;
import javax.swing.table.*;

public class ViewDestinationsFrame extends JFrame {
    public ViewDestinationsFrame() {
        setTitle("View Destinations");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = { "ID", "Name", "Country", "Rating" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
    }
}
