package gui;

import javax.swing.*;
import java.awt.*;

public class TourGuideDashboard extends JFrame {

    public TourGuideDashboard() {
        setTitle("Tour Guide Dashboard");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel title = new JLabel("Tour Guide Dashboard");

        JButton viewToursBtn = new JButton("View My Tours");
        JButton viewTouristsBtn = new JButton("View Tourists");
        JButton availabilityBtn = new JButton("Update Availability");
        JButton viewDestBtn = new JButton("View Destinations");
        JButton viewRatingsBtn = new JButton("View My Ratings");
        JButton logoutBtn = new JButton("Logout");

        viewToursBtn.addActionListener(e -> new ViewToursFrame().setVisible(true));

        viewTouristsBtn.addActionListener(e -> new ViewTouristsFrame().setVisible(true));

        availabilityBtn.addActionListener(e -> new UpdateAvailabilityFrame().setVisible(true));

        viewDestBtn.addActionListener(e -> new ViewDestinationsFrame().setVisible(true));

        viewRatingsBtn.addActionListener(e -> new ViewRatingsFrame().setVisible(true));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        add(title);
        add(viewToursBtn);
        add(viewTouristsBtn);
        add(availabilityBtn);
        add(viewDestBtn);
        add(viewRatingsBtn);
        add(logoutBtn);
    }
}
