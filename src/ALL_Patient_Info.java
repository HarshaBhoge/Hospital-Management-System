
package src.hospital.management.system;

        import net.proteanit.sql.DbUtils;

        import javax.swing.*;
        import javax.swing.border.*;
        import javax.swing.table.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.sql.ResultSet;

public class ALL_Patient_Info extends JFrame implements ActionListener {
    Label countLabel;
    JTable table;
    JButton searchBtn, refreshBtn, backBtn, exportBtn;
    JTextField searchField;
    JComboBox<String> filterCombo;
    DefaultTableModel tableModel;
    TableRowSorter<TableModel> sorter;

    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color PRIMARY_DARK = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color INFO_COLOR = new Color(59, 130, 246);
    private static final Color BACKGROUND = new Color(249, 250, 251);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);
    private static final Color HOVER_COLOR = new Color(243, 244, 246);

    ALL_Patient_Info() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY_COLOR,
                        getWidth(), 0, SECONDARY_COLOR
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);

        // Modern header with gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1400, 80);

        // Medical icon and title
        JLabel iconLabel = new JLabel("📋");
        iconLabel.setBounds(30, 15, 50, 50);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        headerPanel.add(iconLabel);

        JLabel titleLabel = new JLabel("All Patient Details");
        titleLabel.setBounds(85, 18, 350, 28);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("View and manage patient records");
        subtitleLabel.setBounds(85, 46, 300, 18);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        headerPanel.add(subtitleLabel);

        // Back button

        backBtn = createStyledButton("Back", 1260, 20, 110, 40,new Color(231, 76, 60) , Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        headerPanel.add(backBtn);



        mainPanel.add(headerPanel);

        // Search and filter panel
        JPanel controlPanel = createCardPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(30, 110, 1340, 80);

        // Search section
        JLabel searchLabel = new JLabel("Search Patient:");
        searchLabel.setBounds(20, 20, 120, 25);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchLabel.setForeground(TEXT_PRIMARY);
        controlPanel.add(searchLabel);

        searchField = createModernTextField(140, 18, 280, 38);
        searchField.setToolTipText("Search by name, phone, or patient ID");
        controlPanel.add(searchField);

        searchBtn = createStyledButton("Search", 440, 18, 100, 38, PRIMARY_COLOR, Color.WHITE);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        controlPanel.add(searchBtn);

        // Filter section
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setBounds(570, 20, 80, 25);
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        filterLabel.setForeground(TEXT_PRIMARY);
        controlPanel.add(filterLabel);

        String[] filters = {
                "All Patients",
                "Male", "Female"};

        filterCombo = createModernComboBox(filters, 650, 18, 200, 38);
        controlPanel.add(filterCombo);
        filterCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });

        // Action buttons
        refreshBtn = createStyledButton("🔄 Refresh", 880, 18, 120, 38, INFO_COLOR, Color.WHITE);
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        controlPanel.add(refreshBtn);

        exportBtn = createStyledButton("📊 Export", 1020, 18, 120, 38, SUCCESS_COLOR, Color.WHITE);
        exportBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        controlPanel.add(exportBtn);

        // Patient count label
        countLabel = new Label("Total: 0 Patients"); // remove 'JLabel' here

        countLabel.setBounds(1180, 20, 140, 25);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        countLabel.setForeground(PRIMARY_COLOR);
        controlPanel.add(countLabel);

        mainPanel.add(controlPanel);

        // Table panel with shadow
        JPanel tablePanel = createCardPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBounds(30, 210, 1340, 510);

        // Create table
        String[] columns = {"Patient ID", "Name", "Age", "Gender", "Blood Group", "Phone", "Admission Date", "Address", "Father's Name", "Email", "Aadhar"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable();

        try{
            conn c =new conn();
            String q ="Select * from patient_info";
            ResultSet resultSet =c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

             sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilters();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel);

        // Footer info panel
        JPanel footerPanel = createCardPanel();
        footerPanel.setLayout(null);
        footerPanel.setBounds(30, 740, 1340, 50);

        JLabel infoLabel = new JLabel("💡 Tip: Click on any row to view detailed patient information");
        infoLabel.setBounds(20, 12, 500, 25);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(TEXT_SECONDARY);
        footerPanel.add(infoLabel);

        JLabel updateLabel = new JLabel("Last Updated: " + new java.util.Date());
        updateLabel.setBounds(1000, 12, 320, 25);
        updateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        updateLabel.setForeground(TEXT_SECONDARY);
        updateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        footerPanel.add(updateLabel);

        mainPanel.add(footerPanel);

        add(mainPanel);

        // Table row click listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        showPatientDetails(row);
                    }
                }
            }
        });

        setTitle("All Patient Details - Hospital Management System");
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void styleTable() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(45);
        table.setSelectionBackground(new Color(224, 242, 241));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(BORDER_COLOR);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_PRIMARY);
        header.setPreferredSize(new Dimension(header.getWidth(), 50));
        header.setBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR)
        );

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(100);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150);  // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(60);   // Age
        table.getColumnModel().getColumn(3).setPreferredWidth(80);   // Gender
        table.getColumnModel().getColumn(4).setPreferredWidth(100);  // Blood
        table.getColumnModel().getColumn(5).setPreferredWidth(120);  // Phone
        table.getColumnModel().getColumn(6).setPreferredWidth(120);  // Date
        table.getColumnModel().getColumn(7).setPreferredWidth(200);  // Address
        table.getColumnModel().getColumn(8).setPreferredWidth(150);  // Father
        table.getColumnModel().getColumn(9).setPreferredWidth(180);  // Email
        table.getColumnModel().getColumn(10).setPreferredWidth(120); // Aadhar

        // Center align some columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        // Custom cell renderer for alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(249, 250, 251));
                    }
                }

                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        });
    }



    private void showPatientDetails(int row) {
        String patientId = (String) table.getValueAt(row, 0);
        String name = (String) table.getValueAt(row, 1);
        String details = String.format(
                "Patient ID: %s\nName: %s\nAge: %s\nGender: %s\nBlood Group: %s\nPhone: %s\nAdmission Date: %s\nAddress: %s",
                table.getValueAt(row, 0), table.getValueAt(row, 1), table.getValueAt(row, 2),
                table.getValueAt(row, 3), table.getValueAt(row, 4), table.getValueAt(row, 5),
                table.getValueAt(row, 6), table.getValueAt(row, 7)
        );

        JOptionPane.showMessageDialog(this, details, "Patient Details - " + name, JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow effect
                g2d.setColor(new Color(0, 0, 0, 8));
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);

                // Card background
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        return panel;
    }

    private JTextField createModernTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBackground(BACKGROUND);
        textField.setForeground(TEXT_PRIMARY);
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(PRIMARY_COLOR, 2, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER_COLOR, 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        return textField;
    }

    private JComboBox<String> createModernComboBox(String[] items, int x, int y, int width, int height) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBounds(x, y, width, height);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setBackground(BACKGROUND);
        comboBox.setForeground(TEXT_PRIMARY);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return comboBox;
    }

    private JButton createStyledButton(String text, int x, int y, int width, int height, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        button.addMouseListener(new MouseAdapter() {
            Color original = bg;
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bg.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(original);
            }
        });

        return button;
    }

    private JButton createHeaderButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(255, 255, 255, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(new LineBorder(new Color(255, 255, 255, 80), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 30));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 20));
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Searching for: " + searchText, "Search", JOptionPane.INFORMATION_MESSAGE);
                // Add search logic here
            }
        } else if (e.getSource() == refreshBtn) {
            JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
            // Add refresh logic here
        } else if (e.getSource() == exportBtn) {
            JOptionPane.showMessageDialog(this, "Exporting patient data...", "Export", JOptionPane.INFORMATION_MESSAGE);
            // Add export logic here
        } else if (e.getSource() == backBtn) {
            // new Reception();
            dispose();
        }
    }
    private void applyFilters() {
        try {
            String searchText = searchField.getText().trim();
            String selectedGender = filterCombo.getSelectedItem().toString();

            java.util.List<RowFilter<TableModel, Object>> filters = new java.util.ArrayList<>();

            // Search filter (optional: search by ID, Name, Phone)
            if (!searchText.isEmpty()) {
                RowFilter<TableModel, Object> searchFilter = RowFilter.regexFilter("(?i)" + searchText, 0, 1, 5);
                filters.add(searchFilter);
            }

            // Gender filter
            if (!selectedGender.equals("All Patients")) {
                RowFilter<TableModel, Object> genderFilter = RowFilter.regexFilter("^" + selectedGender + "$", 3);
                filters.add(genderFilter);
            }

            // Apply combined filters
            sorter.setRowFilter(RowFilter.andFilter(filters));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new ALL_Patient_Info());
    }
}




