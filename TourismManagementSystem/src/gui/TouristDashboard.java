package gui;

import javax.swing.*;
import java.awt.*;

public class TouristDashboard extends JFrame {

    public TouristDashboard() {
        setTitle("Tourist Dashboard");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel title = new JLabel("Tourist Dashboard");

        JButton viewPackagesBtn = new JButton("View Packages");
        JButton bookTourBtn = new JButton("Book Tour");
        JButton viewBookingsBtn = new JButton("View My Bookings");
        JButton makePaymentBtn = new JButton("Make Payment");
        JButton viewDestBtn = new JButton("View Destinations");
        JButton giveRatingBtn = new JButton("Give Rating");
        JButton logoutBtn = new JButton("Logout");

        viewPackagesBtn.addActionListener(e -> new ViewPackagesFrame().setVisible(true));

        bookTourBtn.addActionListener(e -> new BookTourFrame().setVisible(true));

        viewBookingsBtn.addActionListener(e -> new ViewBookingsFrame().setVisible(true));

        makePaymentBtn.addActionListener(e -> new PaymentFrame().setVisible(true));

        viewDestBtn.addActionListener(e -> new ViewDestinationsFrame().setVisible(true));

        giveRatingBtn.addActionListener(e -> new RatingFrame().setVisible(true));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        add(title);
        add(viewPackagesBtn);
        add(bookTourBtn);
        add(viewBookingsBtn);
        add(makePaymentBtn);
        add(viewDestBtn);
        add(giveRatingBtn);
        add(logoutBtn);
    }
}
