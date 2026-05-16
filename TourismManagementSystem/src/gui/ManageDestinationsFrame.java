package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ManageDestinationsFrame extends JFrame {

    private DefaultTableModel tableModel;

    public ManageDestinationsFrame() {
        setTitle("Manage Destinations");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "ID", "Name", "Country", "Description", "Rating" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(8);
        JTextField countryField = new JTextField(8);
        JTextField descField = new JTextField(8);
        JTextField ratingField = new JTextField(3);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Country:"));
        inputPanel.add(countryField);
        inputPanel.add(new JLabel("Desc:"));
        inputPanel.add(descField);
        inputPanel.add(new JLabel("Rating:"));
        inputPanel.add(ratingField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            tableModel.addRow(new String[] {
                    idField.getText(), nameField.getText(),
                    countryField.getText(), descField.getText(),
                    ratingField.getText()
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
