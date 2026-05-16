package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import model.Hotel;
import filehandling.FileManager;

public class ManageHotelsFrame extends JFrame {

    private DefaultTableModel tableModel;
    private ArrayList<Hotel> hotelList;

    public ManageHotelsFrame() {
        setTitle("Manage Hotels");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = { "Hotel ID", "Name", "Location", "Stars", "Price/Night" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load from file
        hotelList = FileManager.loadHotels();
        for (Hotel h : hotelList) {
            tableModel.addRow(new String[] {
                    h.getHotelID(), h.getName(),
                    h.getLocation(),
                    String.valueOf(h.getStarRating()),
                    String.valueOf(h.getPricePerNight())
            });
        }

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(8);
        JTextField locationField = new JTextField(8);
        JTextField starsField = new JTextField(3);
        JTextField priceField = new JTextField(5);

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Location:"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Stars:"));
        inputPanel.add(starsField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            try {
                String id = idField.getText();
                String nm = nameField.getText();
                String loc = locationField.getText();
                int stars = Integer.parseInt(starsField.getText());
                double price = Double.parseDouble(priceField.getText());

                Hotel h = new Hotel(id, nm, loc, stars, price, 10);
                hotelList.add(h);
                FileManager.saveHotels(hotelList);

                tableModel.addRow(new String[] { id, nm, loc,
                        String.valueOf(stars), String.valueOf(price) });

                idField.setText("");
                nameField.setText("");
                locationField.setText("");
                starsField.setText("");
                priceField.setText("");

                JOptionPane.showMessageDialog(this, "Hotel saved!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid data!");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                hotelList.remove(row);
                FileManager.saveHotels(hotelList);
                tableModel.removeRow(row);
            }
        });

        inputPanel.add(addBtn);
        inputPanel.add(deleteBtn);
        add(inputPanel, BorderLayout.SOUTH);
    }
}