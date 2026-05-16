package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ManagePackagesFrame extends JFrame {

    private DefaultTableModel tableModel;

    public ManagePackagesFrame() {
        setTitle("Manage Packages");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "Package ID", "Title", "Price", "Duration" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField titleField = new JTextField(8);
        JTextField priceField = new JTextField(5);
        JTextField durField = new JTextField(5);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Duration:"));
        inputPanel.add(durField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            tableModel.addRow(new String[] {
                    idField.getText(), titleField.getText(),
                    priceField.getText(), durField.getText()
            });
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0)
                tableModel.removeRow(row);
        });

        inputPanel.add(addBtn);
        inputPanel.add(deleteBtn);
        add(inputPanel, BorderLayout.SOUTH);
    }
}
