package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ManageGuidesFrame extends JFrame {

    private DefaultTableModel tableModel;

    public ManageGuidesFrame() {
        setTitle("Manage Tour Guides");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "ID", "Name", "Language", "Expertise", "Available" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(8);
        JTextField langField = new JTextField(8);
        JTextField expertiseField = new JTextField(8);
        JTextField availField = new JTextField(3);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Language:"));
        inputPanel.add(langField);
        inputPanel.add(new JLabel("Expertise:"));
        inputPanel.add(expertiseField);
        inputPanel.add(new JLabel("Available(Y/N):"));
        inputPanel.add(availField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            tableModel.addRow(new String[] {
                    idField.getText(), nameField.getText(),
                    langField.getText(), expertiseField.getText(),
                    availField.getText()
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
