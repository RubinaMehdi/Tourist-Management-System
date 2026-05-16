package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ManageTransportFrame extends JFrame {

    private DefaultTableModel tableModel;

    public ManageTransportFrame() {
        setTitle("Manage Transport");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "ID", "Type", "Capacity", "From", "To" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField typeField = new JTextField(8);
        JTextField capField = new JTextField(5);
        JTextField fromField = new JTextField(8);
        JTextField toField = new JTextField(8);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Capacity:"));
        inputPanel.add(capField);
        inputPanel.add(new JLabel("From:"));
        inputPanel.add(fromField);
        inputPanel.add(new JLabel("To:"));
        inputPanel.add(toField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            tableModel.addRow(new String[] {
                    idField.getText(), typeField.getText(),
                    capField.getText(), fromField.getText(),
                    toField.getText()
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
