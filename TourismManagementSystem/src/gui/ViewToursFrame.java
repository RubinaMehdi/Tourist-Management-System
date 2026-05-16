package gui;

import javax.swing.*;
import javax.swing.table.*;

public class ViewToursFrame extends JFrame {
    public ViewToursFrame() {
        setTitle("My Tours");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = { "Tour ID", "Package", "Tourist", "Date" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
    }
}
