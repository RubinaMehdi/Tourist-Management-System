package tourismSystem;

import touristPackage.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Main GUI for the Tourism Management System.
 * Swing-based multi-tab interface demonstrating:
 *   - File handling  : tourists, packages, destinations persisted via ObjectOutputStream
 *   - Exception handling : all I/O and parse operations wrapped in try/catch with user feedback
 *   - Polymorphism   : Manageable and Displayable used throughout
 */
public class TourManagementGUI extends JFrame {

    // ── Data lists ─────────────────────────────────────────────────────────────
    private ArrayList<Tourist>   tourists;
    private ArrayList<TourGuide> guides;
    private ArrayList<Hotel>     hotels;
    private ArrayList<Transport> transports;
    private Tourist              loggedInTourist;

    // ── Table models ───────────────────────────────────────────────────────────
    private DefaultTableModel touristTableModel;
    private DefaultTableModel packageTableModel;
    private DefaultTableModel guideTableModel;

    // ── Theme colours ──────────────────────────────────────────────────────────
    private final Color DARK_BG       = new Color(30,  30,  35);
    private final Color DARKER_BG     = new Color(25,  25,  30);
    private final Color CARD_BG       = new Color(40,  40,  45);
    private final Color ACCENT_BLUE   = new Color(52,  152, 219);
    private final Color ACCENT_GREEN  = new Color(46,  204, 113);
    private final Color ACCENT_PURPLE = new Color(155, 89,  182);
    private final Color ACCENT_PINK   = new Color(241, 76,  139);
    private final Color ACCENT_ORANGE = new Color(243, 156, 18);
    private final Color TEXT_COLOR    = new Color(220, 220, 220);
    private final Color TEXT_LIGHT    = new Color(180, 180, 180);

    // ── Constructor ────────────────────────────────────────────────────────────
    public TourManagementGUI() {
        setTitle("Tourism Management System");
        setSize(1300, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply system look-and-feel (Nimbus fallback)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
        }

        loadData();
        buildUI();
    }

    // ── UI Construction ────────────────────────────────────────────────────────
    private void buildUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(DARK_BG);
        tabs.setForeground(TEXT_COLOR);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.addTab("Dashboard",     createDashboardPanel());
        tabs.addTab("Tourists",      createTouristPanel());
        tabs.addTab("Tour Packages", createPackagePanel());
        tabs.addTab("Hotels",        createHotelPanel());
        tabs.addTab("Tour Guides",   createGuidePanel());
        tabs.addTab("Transport",     createTransportPanel());
        tabs.addTab("Tourist Portal",createTouristPortalPanel());

        add(tabs);
        setJMenuBar(buildMenuBar());
        getContentPane().setBackground(DARK_BG);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(DARKER_BG);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(TEXT_COLOR);
        JMenuItem saveItem = new JMenuItem("Save All Data");
        saveItem.addActionListener(e -> saveAllData());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(TEXT_COLOR);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        bar.add(fileMenu);
        bar.add(helpMenu);
        return bar;
    }

    // ── Data I/O ───────────────────────────────────────────────────────────────
    private void loadData() {
        tourists   = loadList("tourists.dat");
        guides     = new ArrayList<>();
        hotels     = new ArrayList<>();
        transports = new ArrayList<>();

        TourPackage.loadFromFile();
        Destination.loadFromFile();

        if (hotels.isEmpty()) {
            hotels.add(new Hotel("H001", "Grand Palace",     "New York", 5, 250.0, 30));
            hotels.add(new Hotel("H002", "Sea View Resort",  "Miami",    4, 180.0, 45));
            hotels.add(new Hotel("H003", "Mountain Lodge",   "Denver",   3, 120.0, 20));
        }
        if (transports.isEmpty()) {
            transports.add(new Transport("TR001", "Luxury Bus",   40,  "New York", "Boston"));
            transports.add(new Transport("TR002", "Mini Van",     15,  "Miami",    "Orlando"));
            transports.add(new Transport("TR003", "Express Train",100, "Chicago",  "Detroit"));
        }
        if (guides.isEmpty()) {
            guides.add(new TourGuide("P001","John Smith",  "john@guide.com", "pass123","G001","English","Historical Tours"));
            guides.add(new TourGuide("P002","Maria Garcia","maria@guide.com","pass123","G002","Spanish","Adventure Tours"));
        }
    }

    @SuppressWarnings("unchecked")
    private <T> ArrayList<T> loadList(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load " + fileName + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private <T> void saveList(ArrayList<T> list, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving " + fileName + ": " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveAllData() {
        saveList(tourists, "tourists.dat");
        TourPackage.getPackageList();        // Packages self-save via TourPackage.saveToFile()
        // One single write instead of looping and overwriting
        if (!TourPackage.getPackageList().isEmpty())
            TourPackage.getPackageList().get(0).saveToFile();
        JOptionPane.showMessageDialog(this, "All data saved successfully!");
    }

    // ── Dashboard ──────────────────────────────────────────────────────────────
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBackground(DARK_BG);

        JLabel title = centeredLabel("TOURISM MANAGEMENT SYSTEM",
                new Font("Segoe UI", Font.BOLD, 28), ACCENT_BLUE);
        JLabel sub   = centeredLabel("Complete Travel Management Solution",
                new Font("Segoe UI", Font.PLAIN, 14), TEXT_LIGHT);

        JPanel stats = new JPanel(new GridLayout(1, 4, 20, 20));
        stats.setBackground(DARK_BG);
        stats.setMaximumSize(new Dimension(1000, 140));
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);
        stats.add(statCard("Total Tourists",  String.valueOf(tourists.size()),                     ACCENT_BLUE,   "Registered travellers"));
        stats.add(statCard("Tour Packages",   String.valueOf(TourPackage.getPackageList().size()), ACCENT_GREEN,  "Available trips"));
        stats.add(statCard("Hotels",          String.valueOf(hotels.size()),                        ACCENT_PURPLE, "Partner properties"));
        stats.add(statCard("Tour Guides",     String.valueOf(guides.size()),                        ACCENT_PINK,   "Expert guides"));

        JPanel actions = new JPanel(new GridLayout(2, 2, 15, 15));
        actions.setBackground(DARK_BG);
        actions.setBorder(titledBorder("Quick Actions", ACCENT_BLUE));
        actions.setMaximumSize(new Dimension(700, 200));
        actions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addTouristBtn = styledButton("Add New Tourist",  ACCENT_BLUE);
        JButton addPackageBtn = styledButton("Add Tour Package", ACCENT_GREEN);
        JButton addHotelBtn   = styledButton("Add Hotel",        ACCENT_PURPLE);
        JButton reportsBtn    = styledButton("View Reports",     ACCENT_ORANGE);

        addTouristBtn.addActionListener(e -> showAddTouristDialog());
        addPackageBtn.addActionListener(e -> showAddPackageDialog());
        addHotelBtn  .addActionListener(e -> showAddHotelDialog());
        reportsBtn   .addActionListener(e -> showReportsDialog());

        actions.add(addTouristBtn);
        actions.add(addPackageBtn);
        actions.add(addHotelBtn);
        actions.add(reportsBtn);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(sub);
        panel.add(Box.createVerticalStrut(30));
        panel.add(stats);
        panel.add(Box.createVerticalStrut(30));
        panel.add(actions);

        JScrollPane sp = new JScrollPane(panel);
        sp.getViewport().setBackground(DARK_BG);
        sp.setBorder(null);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(DARK_BG);
        container.add(sp, BorderLayout.CENTER);
        return container;
    }

    // ── Tourist Management ─────────────────────────────────────────────────────
    private JPanel createTouristPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(DARK_BG);

        touristTableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Nationality", "Contact", "Group"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(touristTableModel);
        refreshTouristTable();

        JPanel btnPanel = buttonRow();
        JButton addBtn    = styledButton("Add Tourist",    ACCENT_BLUE);
        JButton editBtn   = styledButton("Edit Tourist",   ACCENT_GREEN);
        JButton deleteBtn = styledButton("Delete Tourist", ACCENT_PINK);
        JButton refresh   = styledButton("Refresh",        ACCENT_PURPLE);

        addBtn   .addActionListener(e -> { showAddTouristDialog(); refreshTouristTable(); });
        editBtn  .addActionListener(e -> { int r = table.getSelectedRow(); if (r >= 0) editTourist(r); });
        deleteBtn.addActionListener(e -> { int r = table.getSelectedRow(); if (r >= 0) deleteTourist(r); });
        refresh  .addActionListener(e -> refreshTouristTable());

        btnPanel.add(addBtn); btnPanel.add(editBtn); btnPanel.add(deleteBtn); btnPanel.add(refresh);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshTouristTable() {
        touristTableModel.setRowCount(0);
        for (Tourist t : tourists)
            touristTableModel.addRow(new Object[]{
                t.getPersonID(), t.getName(), t.getEmail(),
                t.getNationality(), t.getContactNumber(), t.getGroupSize()});
    }

    private void showAddTouristDialog() {
        JDialog dlg = darkDialog("Add New Tourist", 450, 480);
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(DARK_BG);

        JTextField idF = tf(), nameF = tf(), emailF = tf(), passF = tf();
        JTextField natF = tf(), passportF = tf(), contactF = tf(), groupF = tf();

        addRow(panel, "ID:",              idF);
        addRow(panel, "Name:",            nameF);
        addRow(panel, "Email:",           emailF);
        addRow(panel, "Password:",        passF);
        addRow(panel, "Nationality:",     natF);
        addRow(panel, "Passport No:",     passportF);
        addRow(panel, "Contact:",         contactF);
        addRow(panel, "Group Size:",      groupF);

        JButton save = styledButton("Save Tourist", ACCENT_GREEN);
        save.addActionListener(e -> {
            try {
                validateNotEmpty(idF,"ID"); validateNotEmpty(nameF,"Name");
                validateNotEmpty(emailF,"Email"); validateNotEmpty(passF,"Password");
                if (!emailF.getText().contains("@")) throw new IllegalArgumentException("Invalid email format.");
                if (passF.getText().length() < 6)    throw new IllegalArgumentException("Password must be at least 6 characters.");
                int group = parsePositiveInt(groupF.getText(), "Group Size");

                Tourist t = new Tourist(idF.getText().trim(), nameF.getText().trim(),
                        emailF.getText().trim(), passF.getText().trim(),
                        natF.getText().trim(), passportF.getText().trim(),
                        contactF.getText().trim(), group);
                tourists.add(t);
                refreshTouristTable();
                dlg.dispose();
                JOptionPane.showMessageDialog(this, "Tourist added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(panel, BorderLayout.CENTER);
        dlg.add(save,  BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private void editTourist(int row) {
        Tourist t = tourists.get(row);
        JDialog dlg = darkDialog("Edit Tourist", 450, 360);
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(DARK_BG);

        JTextField nameF    = tfWith(t.getName());
        JTextField emailF   = tfWith(t.getEmail());
        JTextField contactF = tfWith(t.getContactNumber());
        JTextField groupF   = tfWith(String.valueOf(t.getGroupSize()));

        addRow(panel, "Name:",    nameF);
        addRow(panel, "Email:",   emailF);
        addRow(panel, "Contact:", contactF);
        addRow(panel, "Group:",   groupF);

        JButton update = styledButton("Update Tourist", ACCENT_BLUE);
        update.addActionListener(e -> {
            try {
                validateNotEmpty(nameF,"Name");
                if (!emailF.getText().contains("@")) throw new IllegalArgumentException("Invalid email.");
                int group = parsePositiveInt(groupF.getText(), "Group Size");
                t.setName(nameF.getText().trim());
                t.setEmail(emailF.getText().trim());
                t.setContactNumber(contactF.getText().trim());
                t.setGroupSize(group);
                refreshTouristTable();
                dlg.dispose();
                JOptionPane.showMessageDialog(this, "Tourist updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(panel,  BorderLayout.CENTER);
        dlg.add(update, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private void deleteTourist(int row) {
        if (confirm("Delete this tourist?")) {
            tourists.remove(row);
            refreshTouristTable();
        }
    }

    // ── Package Management ─────────────────────────────────────────────────────
    private JPanel createPackagePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(DARK_BG);

        packageTableModel = new DefaultTableModel(
                new String[]{"ID", "Title", "Price", "Duration", "Dining"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(packageTableModel);
        refreshPackageTable();

        JPanel btnPanel = buttonRow();
        JButton addBtn    = styledButton("Add Package",    ACCENT_GREEN);
        JButton deleteBtn = styledButton("Delete Package", ACCENT_PINK);
        JButton refresh   = styledButton("Refresh",        ACCENT_PURPLE);

        addBtn   .addActionListener(e -> { showAddPackageDialog(); refreshPackageTable(); });
        deleteBtn.addActionListener(e -> { int r = table.getSelectedRow(); if (r >= 0) deletePackage(r); });
        refresh  .addActionListener(e -> refreshPackageTable());

        btnPanel.add(addBtn); btnPanel.add(deleteBtn); btnPanel.add(refresh);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshPackageTable() {
        packageTableModel.setRowCount(0);
        for (TourPackage p : TourPackage.getPackageList())
            packageTableModel.addRow(new Object[]{
                p.getPackageID(), p.getTitle(),
                "$" + p.getPrice(), p.getDuration() + " days",
                p.isDiningIncluded() ? "Yes" : "No"});
    }

    private void showAddPackageDialog() {
        JDialog dlg = darkDialog("Add Tour Package", 480, 380);
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(DARK_BG);

        JTextField idF = tf(), titleF = tf(), descF = tf(), priceF = tf(), durF = tf();
        JCheckBox diningCB = new JCheckBox("Include Dining");
        diningCB.setBackground(DARK_BG);
        diningCB.setForeground(TEXT_COLOR);

        addRow(panel, "Package ID:",      idF);
        addRow(panel, "Title:",           titleF);
        addRow(panel, "Description:",     descF);
        addRow(panel, "Price ($):",       priceF);
        addRow(panel, "Duration (days):", durF);
        panel.add(new JLabel()); panel.add(diningCB);

        JButton save = styledButton("Save Package", ACCENT_GREEN);
        save.addActionListener(e -> {
            try {
                validateNotEmpty(idF,"ID"); validateNotEmpty(titleF,"Title");
                double price    = parsePositiveDouble(priceF.getText(), "Price");
                int    duration = parsePositiveInt(durF.getText(),      "Duration");

                TourPackage pkg = new TourPackage(
                        idF.getText().trim(), titleF.getText().trim(),
                        descF.getText().trim(), price, duration, diningCB.isSelected());
                TourPackage.addPackage(pkg);
                refreshPackageTable();
                dlg.dispose();
                JOptionPane.showMessageDialog(this, "Package added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(panel, BorderLayout.CENTER);
        dlg.add(save,  BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private void deletePackage(int row) {
        TourPackage pkg = TourPackage.getPackageList().get(row);
        if (confirm("Delete package: " + pkg.getTitle() + "?"))
            TourPackage.removePackage(pkg.getPackageID());
        refreshPackageTable();
    }

    // ── Hotel Management ───────────────────────────────────────────────────────
    private JPanel createHotelPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(DARK_BG);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Name", "Location", "Stars", "Price/Night", "Rooms"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(model);
        refreshHotelTable(model);

        JPanel btnPanel = buttonRow();
        JButton addBtn    = styledButton("Add Hotel",    ACCENT_BLUE);
        JButton deleteBtn = styledButton("Delete Hotel", ACCENT_PINK);
        JButton refresh   = styledButton("Refresh",      ACCENT_PURPLE);

        addBtn   .addActionListener(e -> { showAddHotelDialog(); refreshHotelTable(model); });
        deleteBtn.addActionListener(e -> { int r = table.getSelectedRow(); if (r >= 0) { if(confirm("Delete hotel?")){ hotels.remove(r); refreshHotelTable(model); } } });
        refresh  .addActionListener(e -> refreshHotelTable(model));

        btnPanel.add(addBtn); btnPanel.add(deleteBtn); btnPanel.add(refresh);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshHotelTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Hotel h : hotels) {
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < h.getStarRating(); i++) stars.append("★");
            for (int i = h.getStarRating(); i < 5; i++) stars.append("☆");
            model.addRow(new Object[]{h.getHotelID(), h.getName(), h.getLocation(),
                stars.toString(), "$" + h.getPricePerNight(), h.getAvailableRooms()});
        }
    }

    private void showAddHotelDialog() {
        JDialog dlg = darkDialog("Add Hotel", 450, 400);
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(DARK_BG);

        JTextField idF = tf(), nameF = tf(), locF = tf(), priceF = tf(), roomsF = tf();
        JComboBox<Integer> starsBox = new JComboBox<>(new Integer[]{1,2,3,4,5});
        starsBox.setBackground(CARD_BG); starsBox.setForeground(TEXT_COLOR);

        addRow(panel, "Hotel ID:",        idF);
        addRow(panel, "Name:",            nameF);
        addRow(panel, "Location:",        locF);
        addRow(panel, "Star Rating:",     starsBox);
        addRow(panel, "Price/Night ($):", priceF);
        addRow(panel, "Rooms:",           roomsF);

        JButton save = styledButton("Save Hotel", ACCENT_GREEN);
        save.addActionListener(e -> {
            try {
                validateNotEmpty(idF,"ID"); validateNotEmpty(nameF,"Name"); validateNotEmpty(locF,"Location");
                double price = parsePositiveDouble(priceF.getText(), "Price");
                int    rooms = parsePositiveInt(roomsF.getText(), "Rooms");
                hotels.add(new Hotel(idF.getText().trim(), nameF.getText().trim(), locF.getText().trim(),
                        (Integer) starsBox.getSelectedItem(), price, rooms));
                dlg.dispose();
                JOptionPane.showMessageDialog(this, "Hotel added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(panel, BorderLayout.CENTER);
        dlg.add(save,  BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    // ── Tour Guide Management ──────────────────────────────────────────────────
    private JPanel createGuidePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(DARK_BG);

        guideTableModel = new DefaultTableModel(
                new String[]{"Guide ID", "Name", "Email", "Language", "Expertise", "Available"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(guideTableModel);
        refreshGuideTable();

        JPanel btnPanel = buttonRow();
        JButton addBtn    = styledButton("Add Guide",          ACCENT_PURPLE);
        JButton toggleBtn = styledButton("Toggle Availability",ACCENT_ORANGE);
        JButton refresh   = styledButton("Refresh",            ACCENT_BLUE);

        addBtn   .addActionListener(e -> { showAddGuideDialog(); refreshGuideTable(); });
        toggleBtn.addActionListener(e -> { int r = table.getSelectedRow(); if (r >= 0) toggleGuide(r); });
        refresh  .addActionListener(e -> refreshGuideTable());

        btnPanel.add(addBtn); btnPanel.add(toggleBtn); btnPanel.add(refresh);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshGuideTable() {
        guideTableModel.setRowCount(0);
        for (TourGuide g : guides)
            guideTableModel.addRow(new Object[]{g.getGuideID(), g.getName(), g.getEmail(),
                g.getLanguage(), g.getExpertise(), g.isAvailable() ? "Yes" : "No"});
    }

    private void showAddGuideDialog() {
        JDialog dlg = darkDialog("Add Tour Guide", 450, 430);
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(DARK_BG);

        JTextField pidF=tf(), nameF=tf(), emailF=tf(), passF=tf(), gidF=tf(), langF=tf(), expF=tf();

        addRow(panel,"Person ID:", pidF);
        addRow(panel,"Name:",      nameF);
        addRow(panel,"Email:",     emailF);
        addRow(panel,"Password:",  passF);
        addRow(panel,"Guide ID:",  gidF);
        addRow(panel,"Language:",  langF);
        addRow(panel,"Expertise:", expF);

        JButton save = styledButton("Save Guide", ACCENT_GREEN);
        save.addActionListener(e -> {
            try {
                validateNotEmpty(pidF,"Person ID"); validateNotEmpty(nameF,"Name");
                validateNotEmpty(emailF,"Email"); validateNotEmpty(passF,"Password");
                validateNotEmpty(gidF,"Guide ID"); validateNotEmpty(langF,"Language");
                validateNotEmpty(expF,"Expertise");
                if (!emailF.getText().contains("@")) throw new IllegalArgumentException("Invalid email.");
                if (passF.getText().length() < 6)    throw new IllegalArgumentException("Password too short.");

                guides.add(new TourGuide(pidF.getText().trim(), nameF.getText().trim(),
                        emailF.getText().trim(), passF.getText().trim(),
                        gidF.getText().trim(), langF.getText().trim(), expF.getText().trim()));
                refreshGuideTable();
                dlg.dispose();
                JOptionPane.showMessageDialog(this, "Guide added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(panel, BorderLayout.CENTER);
        dlg.add(save,  BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private void toggleGuide(int row) {
        TourGuide g = guides.get(row);
        g.updateAvailability(!g.isAvailable());
        refreshGuideTable();
        JOptionPane.showMessageDialog(this, g.getName() + " is now " + (g.isAvailable() ? "Available" : "Busy"));
    }

    // ── Transport Management ───────────────────────────────────────────────────
    private JPanel createTransportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(DARK_BG);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Type", "Capacity", "From", "To"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(model);
        refreshTransportTable(model);

        JPanel btnPanel = buttonRow();
        JButton addBtn    = styledButton("Add Transport",    ACCENT_ORANGE);
        JButton deleteBtn = styledButton("Delete Transport", ACCENT_PINK);
        JButton refresh   = styledButton("Refresh",          ACCENT_BLUE);

        addBtn   .addActionListener(e -> { showAddTransportDialog(model); refreshTransportTable(model); });
        deleteBtn.addActionListener(e -> { int r = table.getSelectedRow(); if (r >= 0 && confirm("Delete transport?")) { transports.remove(r); refreshTransportTable(model); } });
        refresh  .addActionListener(e -> refreshTransportTable(model));

        btnPanel.add(addBtn); btnPanel.add(deleteBtn); btnPanel.add(refresh);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshTransportTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Transport t : transports)
            model.addRow(new Object[]{t.getTransportID(), t.getTransportType(),
                t.getCapacity(), t.getRouteFrom(), t.getRouteTo()});
    }

    private void showAddTransportDialog(DefaultTableModel model) {
        JDialog dlg = darkDialog("Add Transport", 450, 330);
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(DARK_BG);

        JTextField idF = tf(), capF = tf(), fromF = tf(), toF = tf();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Bus","Van","Train","Flight","Car"});
        typeBox.setBackground(CARD_BG); typeBox.setForeground(TEXT_COLOR);

        addRow(panel,"Transport ID:", idF);
        addRow(panel,"Type:",         typeBox);
        addRow(panel,"Capacity:",     capF);
        addRow(panel,"Route From:",   fromF);
        addRow(panel,"Route To:",     toF);

        JButton save = styledButton("Save Transport", ACCENT_GREEN);
        save.addActionListener(e -> {
            try {
                validateNotEmpty(idF,"ID"); validateNotEmpty(fromF,"From"); validateNotEmpty(toF,"To");
                int cap = parsePositiveInt(capF.getText(), "Capacity");
                transports.add(new Transport(idF.getText().trim(), (String) typeBox.getSelectedItem(),
                        cap, fromF.getText().trim(), toF.getText().trim()));
                refreshTransportTable(model);
                dlg.dispose();
                JOptionPane.showMessageDialog(this, "Transport added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.add(panel, BorderLayout.CENTER);
        dlg.add(save,  BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    // ── Tourist Portal ─────────────────────────────────────────────────────────
    private JPanel createTouristPortalPanel() {
        JPanel portal = new JPanel(new BorderLayout(10, 10));
        portal.setBorder(new EmptyBorder(15, 15, 15, 15));
        portal.setBackground(DARK_BG);

        // Login card
        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(CARD_BG);
        loginCard.setBorder(titledBorder("Tourist Login", ACCENT_BLUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField  emailF = tf();   emailF.setPreferredSize(new Dimension(200, 35));
        JPasswordField passF = new JPasswordField();
        passF.setPreferredSize(new Dimension(200, 35));
        passF.setBackground(CARD_BG); passF.setForeground(TEXT_COLOR); passF.setCaretColor(TEXT_COLOR);
        passF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80,80,85),1),
                BorderFactory.createEmptyBorder(8,10,8,10)));

        JButton   loginBtn    = styledButton("Login", ACCENT_GREEN);
        JLabel    statusLabel = label("Please login to access tourist portal");

        gbc.gridx=0; gbc.gridy=0; loginCard.add(label("Email:"),    gbc);
        gbc.gridx=1;              loginCard.add(emailF,             gbc);
        gbc.gridx=0; gbc.gridy=1; loginCard.add(label("Password:"), gbc);
        gbc.gridx=1;              loginCard.add(passF,              gbc);
        gbc.gridx=1; gbc.gridy=2; loginCard.add(loginBtn,           gbc);

        // Dashboard card
        JPanel dash = new JPanel(new BorderLayout(10, 10));
        dash.setBackground(DARK_BG);
        dash.setBorder(titledBorder("Tourist Dashboard", ACCENT_GREEN));

        JTextArea info = new JTextArea(15, 45);
        info.setEditable(false);
        info.setBackground(CARD_BG); info.setForeground(TEXT_COLOR);
        info.setFont(new Font("Monospaced", Font.PLAIN, 12));
        info.setBorder(new EmptyBorder(10,10,10,10));

        JPanel acts = new JPanel(new GridLayout(2, 3, 10, 10));
        acts.setBackground(DARK_BG);
        JButton viewPkgBtn  = styledButton("View Packages",  ACCENT_BLUE);
        JButton bookPkgBtn  = styledButton("Book Package",   ACCENT_GREEN);
        JButton viewPrefBtn = styledButton("My Preferences", ACCENT_PURPLE);
        JButton addPrefBtn  = styledButton("Add Preference", ACCENT_PINK);
        JButton payBtn      = styledButton("Make Payment",   ACCENT_ORANGE);
        JButton logoutBtn   = styledButton("Logout",         new Color(149,165,166));

        acts.add(viewPkgBtn); acts.add(bookPkgBtn); acts.add(viewPrefBtn);
        acts.add(addPrefBtn); acts.add(payBtn);      acts.add(logoutBtn);

        dash.add(new JScrollPane(info), BorderLayout.CENTER);
        dash.add(acts, BorderLayout.SOUTH);

        JPanel cards = new JPanel(new CardLayout());
        cards.setBackground(DARK_BG);
        cards.add(loginCard, "login");
        cards.add(dash,      "dashboard");

        // Actions
        loginBtn.addActionListener(e -> {
            String email = emailF.getText().trim();
            String pass  = new String(passF.getPassword());
            boolean found = false;
            for (Tourist t : tourists) {
                if (t.login(email, pass)) {
                    loggedInTourist = t;
                    statusLabel.setText("Welcome, " + t.getName() + "!");
                    ((CardLayout) cards.getLayout()).show(cards, "dashboard");
                    updateTouristInfo(info);
                    found = true;
                    break;
                }
            }
            if (!found) statusLabel.setText("Invalid email or password.");
        });

        logoutBtn.addActionListener(e -> {
            loggedInTourist = null;
            emailF.setText(""); passF.setText("");
            statusLabel.setText("Please login to access tourist portal");
            ((CardLayout) cards.getLayout()).show(cards, "login");
        });

        viewPkgBtn .addActionListener(e -> displayPackages(info));
        bookPkgBtn .addActionListener(e -> bookPackageDialog(info));
        viewPrefBtn.addActionListener(e -> displayPreferences(info));
        addPrefBtn .addActionListener(e -> addPreferenceDialog(info));
        payBtn     .addActionListener(e -> makePaymentDialog(info));

        portal.add(cards,       BorderLayout.CENTER);
        portal.add(statusLabel, BorderLayout.SOUTH);
        return portal;
    }

    private void updateTouristInfo(JTextArea area) {
        if (loggedInTourist == null) return;
        area.setText("═══════════════════════════════════════════\n");
        area.append("     WELCOME " + loggedInTourist.getName().toUpperCase() + "!\n");
        area.append("═══════════════════════════════════════════\n\n");
        area.append("ID:          " + loggedInTourist.getPersonID()     + "\n");
        area.append("Email:       " + loggedInTourist.getEmail()         + "\n");
        area.append("Nationality: " + loggedInTourist.getNationality()   + "\n");
        area.append("Contact:     " + loggedInTourist.getContactNumber() + "\n");
        area.append("Group Size:  " + loggedInTourist.getGroupSize()     + "\n\n");
        area.append("Use the buttons below to manage your travel.\n");
        area.append("═══════════════════════════════════════════\n");
    }

    private void displayPackages(JTextArea area) {
        if (loggedInTourist == null) return;
        area.setText("═══════════════════════════════════════════\n");
        area.append("         AVAILABLE TOUR PACKAGES\n");
        area.append("═══════════════════════════════════════════\n\n");
        if (TourPackage.getPackageList().isEmpty()) {
            area.append("  No packages available.\n");
            return;
        }
        for (TourPackage p : TourPackage.getPackageList()) {
            area.append("ID:       " + p.getPackageID()  + "\n");
            area.append("Title:    " + p.getTitle()       + "\n");
            area.append("Price:    $" + p.getPrice()      + "\n");
            area.append("Duration: " + p.getDuration()    + " days\n");
            area.append("Dining:   " + (p.isDiningIncluded() ? "Included" : "Not included") + "\n");
            if (p.getHotel()  != null) area.append("Hotel:    " + p.getHotel().getName()  + "\n");
            if (p.getGuide()  != null) area.append("Guide:    " + p.getGuide().getName()  + "\n");
            area.append("─────────────────────────────────\n\n");
        }
    }

    private void displayPreferences(JTextArea area) {
        if (loggedInTourist == null) return;
        area.setText("═══════════════════════════════════════════\n");
        area.append("              MY PREFERENCES\n");
        area.append("═══════════════════════════════════════════\n\n");
        ArrayList<String> prefs = loggedInTourist.getPreferences();
        if (prefs.isEmpty()) {
            area.append("  No preferences added yet.\n");
        } else {
            for (int i = 0; i < prefs.size(); i++)
                area.append("  " + (i+1) + ". " + prefs.get(i) + "\n");
        }
        area.append("\n═══════════════════════════════════════════\n");
    }

    private void addPreferenceDialog(JTextArea area) {
        if (loggedInTourist == null) return;
        String pref = JOptionPane.showInputDialog(this, "Enter preference (e.g. Adventure, Beach, Cultural):");
        if (pref != null && !pref.trim().isEmpty()) {
            loggedInTourist.addPreference(pref.trim());
            displayPreferences(area);
            JOptionPane.showMessageDialog(this, "Preference added!");
        }
    }

    private void bookPackageDialog(JTextArea area) {
        if (loggedInTourist == null) return;
        ArrayList<TourPackage> list = TourPackage.getPackageList();
        if (list.isEmpty()) { JOptionPane.showMessageDialog(this, "No packages available."); return; }

        String[] names = list.stream().map(TourPackage::getDisplayString).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(this,
                "Select a package:", "Book Tour Package",
                JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
        if (selected != null) {
            area.setText("═══════════════════════════════════════════\n");
            area.append("           BOOKING CONFIRMATION\n");
            area.append("═══════════════════════════════════════════\n\n");
            area.append("Tourist: " + loggedInTourist.getName() + "\n");
            area.append("Package: " + selected + "\n");
            area.append("Status:  CONFIRMED\n\n");
            area.append("Thank you for booking with us!\n");
            area.append("═══════════════════════════════════════════\n");
            JOptionPane.showMessageDialog(this, "Package booked successfully!");
        }
    }

    private void makePaymentDialog(JTextArea area) {
        if (loggedInTourist == null) return;
        String amountStr = JOptionPane.showInputDialog(this, "Enter payment amount ($):");
        if (amountStr == null || amountStr.trim().isEmpty()) return;
        try {
            double amount = Double.parseDouble(amountStr.trim());
            if (amount <= 0) throw new NumberFormatException("Amount must be positive.");
            area.setText("═══════════════════════════════════════════\n");
            area.append("              PAYMENT RECEIPT\n");
            area.append("═══════════════════════════════════════════\n\n");
            area.append("Tourist:        " + loggedInTourist.getName() + "\n");
            area.append("Amount:         $" + amount + "\n");
            area.append("Payment Method: Credit Card\n");
            area.append("Status:         COMPLETED\n");
            area.append("Transaction ID: TXN" + System.currentTimeMillis() + "\n");
            area.append("\n═══════════════════════════════════════════\n");
            JOptionPane.showMessageDialog(this, "Payment of $" + amount + " successful!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Reports Dialog ─────────────────────────────────────────────────────────
    private void showReportsDialog() {
        JDialog dlg = new JDialog(this, "System Reports", true);
        dlg.setSize(700, 500);
        dlg.setLocationRelativeTo(this);
        dlg.getContentPane().setBackground(DARK_BG);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBackground(CARD_BG); area.setForeground(TEXT_COLOR);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setBorder(new EmptyBorder(15, 15, 15, 15));

        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("           TOURISM MANAGEMENT SYSTEM REPORT\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        sb.append(String.format("%-25s %d%n", "Total Tourists:",    tourists.size()));
        sb.append(String.format("%-25s %d%n", "Total Packages:",    TourPackage.getPackageList().size()));
        sb.append(String.format("%-25s %d%n", "Total Hotels:",      hotels.size()));
        sb.append(String.format("%-25s %d%n", "Total Tour Guides:", guides.size()));
        sb.append(String.format("%-25s %d%n", "Transport Options:", transports.size()));
        sb.append("\n─────────────────────────────────\n");
        sb.append("TOUR PACKAGES:\n");
        for (TourPackage p : TourPackage.getPackageList())
            sb.append("  • ").append(p.getDisplayString()).append("\n");
        sb.append("\nHOTELS:\n");
        for (Hotel h : hotels)
            sb.append("  • ").append(h.getName()).append(" (").append(h.getStarRating()).append(" stars, ").append(h.getLocation()).append(")\n");
        sb.append("\nTOUR GUIDES:\n");
        for (TourGuide g : guides)
            sb.append("  • ").append(g.getName()).append(" — ").append(g.getExpertise())
              .append(g.isAvailable() ? " [Available]" : " [Busy]").append("\n");

        area.setText(sb.toString());
        dlg.add(new JScrollPane(area));
        dlg.setVisible(true);
    }

    // ── About Dialog ───────────────────────────────────────────────────────────
    private void showAboutDialog() {
        JDialog dlg = darkDialog("About", 380, 260);
        JTextArea text = new JTextArea(
            "TOURISM MANAGEMENT SYSTEM\n\nVersion: 2.0\n\n"
          + "Features:\n• Tourist Management\n• Tour Packages\n"
          + "• Hotels & Transport\n• Tour Guide Assignment\n• Payment Processing\n\n"
          + "(c) 2024 Tourism Management System");
        text.setEditable(false);
        text.setBackground(DARK_BG); text.setForeground(TEXT_COLOR);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        text.setBorder(new EmptyBorder(20, 20, 20, 20));
        dlg.add(text);
        dlg.setVisible(true);
    }

    // ── UI Helpers ─────────────────────────────────────────────────────────────
    private JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg); btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(bg.darker()); }
            public void mouseExited (java.awt.event.MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setBackground(CARD_BG); t.setForeground(TEXT_COLOR);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        t.setRowHeight(28);
        t.setGridColor(new Color(60, 60, 65));
        t.getTableHeader().setBackground(DARKER_BG);
        t.getTableHeader().setForeground(ACCENT_BLUE);
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setSelectionBackground(ACCENT_BLUE);
        t.setSelectionForeground(Color.WHITE);
        return t;
    }

    private JTextField tf() {
        JTextField f = new JTextField();
        f.setBackground(CARD_BG); f.setForeground(TEXT_COLOR); f.setCaretColor(TEXT_COLOR);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80,80,85),1),
                BorderFactory.createEmptyBorder(7,9,7,9)));
        return f;
    }

    private JTextField tfWith(String value) { JTextField f = tf(); f.setText(value); return f; }

    private JLabel label(String text) {
        JLabel l = new JLabel(text); l.setForeground(TEXT_COLOR);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13)); return l;
    }

    private JLabel centeredLabel(String text, Font font, Color color) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(font); l.setForeground(color);
        l.setAlignmentX(Component.CENTER_ALIGNMENT); return l;
    }

    private void addRow(JPanel p, String labelText, JComponent field) {
        p.add(label(labelText)); p.add(field);
    }

    private JPanel buttonRow() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        p.setBackground(DARK_BG); return p;
    }

    private JDialog darkDialog(String title, int w, int h) {
        JDialog dlg = new JDialog(this, title, true);
        dlg.setSize(w, h); dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());
        dlg.getContentPane().setBackground(DARK_BG);
        return dlg;
    }

    private Border titledBorder(String title, Color color) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(color, 1), title,
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), color);
    }

    private JPanel statCard(String title, String value, Color color, String sub) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2), new EmptyBorder(12,18,12,18)));
        JLabel t = new JLabel(title, SwingConstants.CENTER); t.setFont(new Font("Segoe UI",Font.PLAIN,12)); t.setForeground(TEXT_LIGHT);
        JLabel v = new JLabel(value, SwingConstants.CENTER); v.setFont(new Font("Segoe UI",Font.BOLD, 30)); v.setForeground(color);
        JLabel s = new JLabel(sub,   SwingConstants.CENTER); s.setFont(new Font("Segoe UI",Font.PLAIN,10)); s.setForeground(TEXT_LIGHT);
        card.add(t, BorderLayout.NORTH); card.add(v, BorderLayout.CENTER); card.add(s, BorderLayout.SOUTH);
        return card;
    }

    // ── Validation helpers ─────────────────────────────────────────────────────
    private void validateNotEmpty(JTextField f, String name) {
        if (f.getText() == null || f.getText().trim().isEmpty())
            throw new IllegalArgumentException(name + " cannot be empty.");
    }

    private int parsePositiveInt(String s, String name) {
        try {
            int v = Integer.parseInt(s.trim());
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " must be a positive whole number.");
        }
    }

    private double parsePositiveDouble(String s, String name) {
        try {
            double v = Double.parseDouble(s.trim());
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " must be a positive number.");
        }
    }

    private boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // ── Entry point ────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TourManagementGUI().setVisible(true));
    }
}
