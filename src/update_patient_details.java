package src.hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class update_patient_details extends JFrame {

    Choice patientIdChoice;
    JTextField nameField, roomField, paidField, pendingField;
    JLabel inTimeLabel;

    JButton checkBtn, updateBtn, backBtn;

    // ===== SAME COLOR PALETTE =====
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color CARD_BG = Color.WHITE;

    public update_patient_details() {

        JPanel mainPanel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, SECONDARY_COLOR));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ===== HEADER =====
        JLabel icon = new JLabel("📋");
        icon.setBounds(25, 25, 50, 50);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        mainPanel.add(icon);


        JLabel title = new JLabel("Update Patient Details");
        title.setBounds(75, 20, 400, 35);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        mainPanel.add(title);

        JLabel subtitle = new JLabel("Edit patient information and payment details");
        subtitle.setBounds(80, 55, 450, 18);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(255, 255, 255, 180));
        mainPanel.add(subtitle);


        // ===== CARD PANEL =====
        JPanel card = createCardPanel();
        card.setBounds(30, 100, 820, 330);
        card.setLayout(null);
        mainPanel.add(card);

        // ===== LABELS & FIELDS =====
        addLabel(card, "Patient ID:", 40, 30);
        patientIdChoice = new Choice();
        patientIdChoice.setBounds(180, 30, 200, 25);
        card.add(patientIdChoice);

        addLabel(card, "Patient Name:", 40, 70);
        nameField = addField(card, 180, 70);

        addLabel(card, "Room Number:", 40, 110);
        roomField = addField(card, 180, 110);

        addLabel(card, "In Time:", 40, 150);
        inTimeLabel = new JLabel("-");
        inTimeLabel.setBounds(180, 150, 300, 25);
        card.add(inTimeLabel);

        addLabel(card, "Amount Paid (₹):", 420, 70);
        paidField = addField(card, 580, 70);

        addLabel(card, "Pending Amount (₹):", 420, 110);
        pendingField = addField(card, 580, 110);


        // Load Patient_ID
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("select Patient_ID from patient_info");
            while (rs.next()) {
                patientIdChoice.add(rs.getString("Patient_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== BUTTONS =====
        checkBtn = createButton("Check", 180, 230, SUCCESS_COLOR);
        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn c = new conn();
                try {
                    ResultSet resultSet = c.statement.executeQuery("select * from patient_info where Patient_ID = '"+patientIdChoice.getSelectedItem()+"'");
                    while(resultSet.next()){
                        nameField.setText(resultSet.getString("Patient_Name"));
                        roomField.setText(resultSet.getString("Room_Number"));
                        inTimeLabel.setText(resultSet.getString("Admit_Date"));
                        paidField.setText(resultSet.getString("Deposit"));
                    }
                    ResultSet resultSet1 = c.statement.executeQuery("select * from Room where room_no ='"+roomField.getText()+"'");
                    while (resultSet1.next()){
                        String price = resultSet1.getString("Price");
                        int pending = Integer.parseInt(price)-Integer.parseInt(paidField.getText());
                        pendingField.setText(""+pending);
                    }
                }
                catch (Exception E){
                    E.printStackTrace();
                }
            }
        });
        updateBtn = createButton("Update", 320, 230, new Color(59, 130, 246));
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn c = new conn();

                    String patientId = patientIdChoice.getSelectedItem();
                    String room = roomField.getText();
                    String time = inTimeLabel.getText();
                    String amount = paidField.getText();

                    String sql =
                            "UPDATE patient_info SET " +
                                    "Room_Number='" + room + "', " +
                                    "Admit_Date='" + time + "', " +
                                    "Deposit='" + amount + "' " +
                                    "WHERE Patient_ID='" + patientId + "'";

                    c.statement.executeUpdate(sql);

                    JOptionPane.showMessageDialog(null, "Updated Successfully!");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        backBtn = createButton("Back", 460, 230, new Color(107, 114, 128));
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
            }
        });


        card.add(checkBtn);
        card.add(updateBtn);
        card.add(backBtn);







        add(mainPanel);
        setTitle(" Update Patient Details");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private JPanel createCardPanel() {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(CARD_BG);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
    }

    private void addLabel(JPanel panel, String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 140, 25);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lbl);
    }

    private JTextField addField(JPanel panel, int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 200, 25);
        panel.add(tf);
        return tf;
    }

    private JButton createButton(String text, int x, int y, Color bg) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 120, 35);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(update_patient_details::new);
    }
}
