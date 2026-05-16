package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ManageDiningFrame extends JFrame {

    private DefaultTableModel tableModel;

    public ManageDiningFrame() {
        setTitle("Manage Dining");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "ID", "Restaurant", "Cuisine", "Price Range" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(8);
        JTextField cuisineField = new JTextField(8);
        JTextField priceField = new JTextField(5);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Restaurant:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Cuisine:"));
        inputPanel.add(cuisineField);
        inputPanel.add(new JLabel("Price Range:"));
        inputPanel.add(priceField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            tableModel.addRow(new String[] {
                    idField.getText(), nameField.getText(),
                    cuisineField.getText(), priceField.getText()
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
