package gui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel title = new JLabel("Admin Dashboard");

        JButton hotelsBtn = new JButton("Manage Hotels");
        JButton packagesBtn = new JButton("Manage Packages");
        JButton touristsBtn = new JButton("Manage Tourists");
        JButton guidesBtn = new JButton("Manage Tour Guides");
        JButton bookingsBtn = new JButton("Manage Bookings");
        JButton transportBtn = new JButton("Manage Transport");
        JButton diningBtn = new JButton("Manage Dining");
        JButton destinationBtn = new JButton("Manage Destinations");
        JButton ratingsBtn = new JButton("View Ratings");
        JButton logoutBtn = new JButton("Logout");

        hotelsBtn.addActionListener(e -> new ManageHotelsFrame().setVisible(true));

        packagesBtn.addActionListener(e -> new ManagePackagesFrame().setVisible(true));

        touristsBtn.addActionListener(e -> new ManageTouristsFrame().setVisible(true));

        guidesBtn.addActionListener(e -> new ManageGuidesFrame().setVisible(true));

        bookingsBtn.addActionListener(e -> new ManageBookingsFrame().setVisible(true));

        transportBtn.addActionListener(e -> new ManageTransportFrame().setVisible(true));

        diningBtn.addActionListener(e -> new ManageDiningFrame().setVisible(true));

        destinationBtn.addActionListener(e -> new ManageDestinationsFrame().setVisible(true));

        ratingsBtn.addActionListener(e -> new ViewRatingsFrame().setVisible(true));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        add(title);
        add(hotelsBtn);
        add(packagesBtn);
        add(touristsBtn);
        add(guidesBtn);
        add(bookingsBtn);
        add(transportBtn);
        add(diningBtn);
        add(destinationBtn);
        add(ratingsBtn);
        add(logoutBtn);
    }
}
