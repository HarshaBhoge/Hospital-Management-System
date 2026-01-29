package src.hospital.management.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Ambulance extends JFrame implements ActionListener {

    JTable table;
    JButton refreshBtn, backBtn;
    JTextField searchField;
    JComboBox<String> filterCombo;
    TableRowSorter<TableModel> sorter;

    // ===== SAME COLOR PALETTE =====
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color INFO_COLOR = new Color(59, 130, 246);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);

    Ambulance() {
        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY_COLOR,
                        getWidth(), 0, SECONDARY_COLOR
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY_COLOR,
                        getWidth(), 0, SECONDARY_COLOR
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBounds(0, 0, 900, 80);

        JLabel icon = new JLabel("🚑");
        icon.setBounds(25, 15, 50, 50);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        headerPanel.add(icon);

        JLabel title = new JLabel("Ambulance Information");
        title.setBounds(80, 18, 300, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        JLabel subtitle = new JLabel("View and manage hospital ambulances");
        subtitle.setBounds(80, 48, 300, 18);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(255, 255, 255, 180));
        headerPanel.add(subtitle);

        backBtn = createButton("← Back", 760, 20, 110, 40, new Color(229, 231, 235));
        backBtn.setForeground(new Color(31, 58, 95));
        headerPanel.add(backBtn);

        mainPanel.add(headerPanel);

        // ===== CONTROL PANEL =====
        JPanel controlPanel = createCardPanel();
        controlPanel.setBounds(20, 110, 860, 80);
        controlPanel.setLayout(null);

        JLabel searchLabel = new JLabel("Search Ambulance:");
        searchLabel.setBounds(20, 20, 140, 25);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        controlPanel.add(searchLabel);

        searchField = createTextField(160, 18, 220, 38);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilters();
            }
        });
        controlPanel.add(searchField);

        JLabel filterLabel = new JLabel("Status:");
        filterLabel.setBounds(400, 20, 60, 25);
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        controlPanel.add(filterLabel);

        filterCombo = createComboBox(
                new String[]{"All", "Available", "On Duty"},
                460, 18, 160, 38
        );
        filterCombo.addActionListener(e -> applyFilters());
        controlPanel.add(filterCombo);

        refreshBtn = createButton("🔄 Refresh", 630, 18, 120, 38, SUCCESS_COLOR);
        controlPanel.add(refreshBtn);

        mainPanel.add(controlPanel);

        // ===== TABLE PANEL =====
        JPanel tablePanel = createCardPanel();
        tablePanel.setBounds(20, 210, 860, 500);
        tablePanel.setLayout(new BorderLayout());

        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(42);
        table.setGridColor(BORDER_COLOR);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_PRIMARY);
        header.setPreferredSize(new Dimension(100, 45));

        try {
            conn c = new conn();
            String q = "SELECT * FROM ambulance";
            ResultSet resultSet = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
            sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(sp, BorderLayout.CENTER);
        mainPanel.add(tablePanel);

        add(mainPanel);

        setTitle("Ambulance Information - Hospital Management System");
        setSize(915, 780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // ===== HELPER METHODS =====
    private JPanel createCardPanel() {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 0, 10));
                g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 15, 15);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
    }

    private JTextField createTextField(int x, int y, int w, int h) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, w, h);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        return tf;
    }

    private JComboBox<String> createComboBox(String[] data, int x, int y, int w, int h) {
        JComboBox<String> cb = new JComboBox<>(data);
        cb.setBounds(x, y, w, h);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        return cb;
    }

    private JButton createButton(String text, int x, int y, int w, int h, Color bg) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, w, h);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        return btn;
    }

    private void applyFilters() {
        RowFilter<TableModel, Object> rf;
        try {
            java.util.List<RowFilter<TableModel, Object>> filters = new java.util.ArrayList<>();

            if (!searchField.getText().trim().isEmpty()) {
                filters.add(RowFilter.regexFilter("(?i)" + searchField.getText()));
            }

            if (!filterCombo.getSelectedItem().equals("All")) {
                filters.add(RowFilter.regexFilter(filterCombo.getSelectedItem().toString(), 4));
            }

            rf = RowFilter.andFilter(filters);
        } catch (Exception e) {
            rf = null;
        }
        sorter.setRowFilter(rf);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) dispose();
        if (e.getSource() == refreshBtn) JOptionPane.showMessageDialog(this, "Ambulance data refreshed!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ambulance::new);
    }
}
