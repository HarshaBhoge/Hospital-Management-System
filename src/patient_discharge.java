package src.hospital.management.system;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class patient_discharge extends JFrame  {


    JLabel roomNumberLabel, inTimeLabel, outTimeLabel;
    JButton dischargeBtn, checkBtn, backBtn;

    // ===== SAME COLOR PALETTE AS ROOM =====
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);

    patient_discharge () {

        // ===== MAIN PANEL WITH GRADIENT =====
        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 900, 80);

        JLabel icon = new JLabel("📋");
        icon.setBounds(25, 15, 50, 50);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        headerPanel.add(icon);

        JLabel title = new JLabel("Patient Discharge");
        title.setBounds(80, 18, 300, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        JLabel subtitle = new JLabel("Manage patient check-out records");
        subtitle.setBounds(80, 48, 300, 18);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(255, 255, 255, 180));
        headerPanel.add(subtitle);



        mainPanel.add(headerPanel);

        // ===== CARD PANEL FOR PATIENT INFO =====
        JPanel infoPanel = createCardPanel();
        infoPanel.setBounds(20, 110, 860, 200);
        infoPanel.setLayout(null);

        JLabel custLabel = new JLabel("Customer Id:");
        custLabel.setBounds(30, 30, 120, 25);
        custLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoPanel.add(custLabel);

        Choice idChoice = new Choice();
        idChoice.setBounds(150, 28, 200, 30);
        infoPanel.add(idChoice);

        try{
            conn c =new conn();
            ResultSet resultSet=c.statement.executeQuery("select * from patient_info");
            while(resultSet.next()){
                idChoice.add(resultSet.getString("Patient_ID"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }



        JLabel roomLabel = new JLabel("Room Number:");
        roomLabel.setBounds(30, 70, 120, 25);
        roomLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoPanel.add(roomLabel);

        roomNumberLabel = new JLabel("-");
        roomNumberLabel.setBounds(150, 70, 200, 25);
        roomNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        infoPanel.add(roomNumberLabel);

        JLabel inLabel = new JLabel("In Time:");
        inLabel.setBounds(30, 110, 120, 25);
        inLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoPanel.add(inLabel);

        inTimeLabel = new JLabel("-");
        inTimeLabel.setBounds(150, 110, 300, 25);
        inTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        infoPanel.add(inTimeLabel);

        JLabel outLabel = new JLabel("Out Time:");
        outLabel.setBounds(30, 150, 120, 25);
        outLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoPanel.add(outLabel);

        outTimeLabel = new JLabel(getCurrentTime());
        outTimeLabel.setBounds(150, 150, 300, 25);
        outTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        infoPanel.add(outTimeLabel);

        mainPanel.add(infoPanel);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = createCardPanel();
        buttonPanel.setBounds(20, 330, 860, 80);
        buttonPanel.setLayout(null);

        dischargeBtn = createButton("Discharge", 30, 20, 120, 38, SUCCESS_COLOR);
        dischargeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn c=new conn();
                try {
                    c.statement.executeUpdate("delete from patient_info where Patient_ID='"+idChoice.getSelectedItem()+"'");
                    c.statement.executeUpdate("update Room set Avalability ='Available' where room_no ='"+roomNumberLabel.getText()+"'");
                    JOptionPane.showMessageDialog(null,"Done");
                    setVisible(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        checkBtn = createButton("Check", 180, 20, 120, 38, new Color(59, 130, 246));
        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn c = new conn();
                try {
                    ResultSet resultSet = c.statement.executeQuery("select * from patient_info where Patient_ID = '"+idChoice.getSelectedItem()+"'");
                    while(resultSet.next()){
                        roomNumberLabel.setText(resultSet.getString("Room_Number"));
                        inTimeLabel.setText(resultSet.getString("Admit_Date"));
                    }
                }
                catch (Exception E){
                    E.printStackTrace();
                }
            }
        });
        backBtn = createButton("Back", 330, 20, 120, 38, new Color(107, 114, 128));
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        buttonPanel.add(dischargeBtn);
        buttonPanel.add(checkBtn);
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel);



        add(mainPanel);
        setTitle("Patient Discharge - Hospital Management System");
        setSize(915, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // ===== STYLING METHODS =====
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

    private JButton createButton(String text, int x, int y, int w, int h, Color bg) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, w, h);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }







    public static void main(String[] args) {
        SwingUtilities.invokeLater(patient_discharge ::new);
    }
}
