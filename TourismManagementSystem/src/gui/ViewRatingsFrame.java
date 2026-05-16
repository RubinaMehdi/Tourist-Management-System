package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ViewRatingsFrame extends JFrame {

    public ViewRatingsFrame() {
        setTitle("View Ratings");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "Rating ID", "Tourist ID", "Guide ID", "Score", "Comment" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JLabel info = new JLabel("All Ratings");
        add(info, BorderLayout.NORTH);
    }
}
