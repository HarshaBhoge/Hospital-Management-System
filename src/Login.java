
package src.hospital.management.system;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn, backBtn;

    // ===== SAME COLOR PALETTE =====
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color BORDER_COLOR = new Color(170, 170, 176);

    Login() {
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


        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("src/icon/Login.png"));
        Image i1 = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon imageIcon1 = new ImageIcon(i1);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(250,50,250,200);
        mainPanel.add(label);

        // ===== LOGIN FORM CARD =====
        JPanel formPanel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 0, 10));
                g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 15, 15);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        formPanel.setBounds(100, 60, 600, 440);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(90, 160, 100, 25);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(90, 190, 400, 40);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        formPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(90, 250, 100, 25);
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(90, 280, 400, 40);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        formPanel.add(passwordField);

        loginBtn = createButton("Login", 120, 360, 150, 45, SUCCESS_COLOR);
        formPanel.add(loginBtn);

        backBtn = createButton(" Back", 310, 360, 150, 45, new Color(231, 76, 60));

        formPanel.add(backBtn);

        mainPanel.add(formPanel);

        add(mainPanel);
        setTitle("Login - Hospital Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) dispose();
        if (e.getSource() == loginBtn) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (e.getSource() == loginBtn) {
                try {

                    conn c = new conn();
                    String user = usernameField.getText();
                    String pass = passwordField.getText();

                    String q = " select * from login where ID= '" + user + "' and PW ='" + pass + "'";
                    ResultSet resultSet = c.statement.executeQuery(q);

                    if (resultSet.next()) {
                        new Reception();
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
