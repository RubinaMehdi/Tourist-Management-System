package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ManageTouristsFrame extends JFrame {

    private DefaultTableModel tableModel;

    public ManageTouristsFrame() {
        setTitle("Manage Tourists");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "ID", "Name", "Email", "Nationality", "Passport" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(8);
        JTextField emailField = new JTextField(8);
        JTextField nationalityField = new JTextField(8);
        JTextField passportField = new JTextField(8);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Nationality:"));
        inputPanel.add(nationalityField);
        inputPanel.add(new JLabel("Passport:"));
        inputPanel.add(passportField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            tableModel.addRow(new String[] {
                    idField.getText(), nameField.getText(),
                    emailField.getText(), nationalityField.getText(),
                    passportField.getText()
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
