
package src.hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Reception extends JFrame {

    JButton addPatientBtn, roomBtn, departmentBtn, employeeInfoBtn,
            patientInfoBtn, patientDischargeBtn, updatePatientBtn,
            ambulanceBtn, searchRoomBtn, logoutBtn;

    // ===== BLUISH THEME COLORS =====
    private static final Color PRIMARY = new Color(52, 152, 219);
    private static final Color PRIMARY_DARK = new Color(44, 62, 80, 232);
    private static final Color SIDEBAR_DARK = new Color(44, 62, 80);
    private static final Color SIDEBAR_LIGHT = new Color(52, 152, 219);
    private static final Color BG_LIGHT = new Color(245, 247, 250);

    Reception() {
        setLayout(null);
        setTitle("Reception Desk - HB Medicare Hospital");
        getContentPane().setBackground(BG_LIGHT);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== HEADER (GRADIENT) =====
        JPanel headPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY,
                        getWidth(), 0, PRIMARY_DARK
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };



        headPanel.setLayout(null);
        headPanel.setBounds(0, 0, 2000, 90);
        add(headPanel);

        JLabel titleLabel = new JLabel("RECEPTION DASHBOARD");
        titleLabel.setBounds(25, 22, 600, 45);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        headPanel.add(titleLabel);

        // ===== SIDEBAR (GRADIENT) =====
        JPanel sidePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, SIDEBAR_DARK,
                        0, getHeight(), SIDEBAR_LIGHT
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidePanel.setLayout(null);
        sidePanel.setBounds(0, 90, 330, 1800);
        add(sidePanel);



        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setBounds(25, 20, 260, 30);
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuLabel.setForeground(new Color(189, 195, 199));
        sidePanel.add(menuLabel);

        int y = 70;

        JLabel patientSection = new JLabel("📋 PATIENT MANAGEMENT");
        patientSection.setBounds(25, y, 300, 25);
        patientSection.setFont(new Font("Segoe UI", Font.BOLD, 13));
        patientSection.setForeground(PRIMARY);
        sidePanel.add(patientSection);

        y += 30;
        addPatientBtn = createMenuButton("Add New Patient", y);
        sidePanel.add(addPatientBtn);
        addPatientBtn.addActionListener(e -> new AddPatient());

        y += 55;
        patientInfoBtn = createMenuButton("Patient Information", y);
        sidePanel.add(patientInfoBtn);
        patientInfoBtn.addActionListener(e -> new ALL_Patient_Info());

        y += 55;
        updatePatientBtn = createMenuButton("Update Patient Details", y);
        sidePanel.add(updatePatientBtn);
        updatePatientBtn.addActionListener(e-> new update_patient_details());

        y += 55;
        patientDischargeBtn = createMenuButton("Patient Discharge", y);
        sidePanel.add(patientDischargeBtn);
        patientDischargeBtn.addActionListener(e-> new patient_discharge());

        y += 70;
        JLabel roomSection = new JLabel("📋 ROOM MANAGEMENT");
        roomSection.setBounds(25, y, 300, 25);
        roomSection.setFont(new Font("Segoe UI", Font.BOLD, 13));
        roomSection.setForeground(PRIMARY);
        sidePanel.add(roomSection);

        y += 30;
        roomBtn = createMenuButton("Room Status", y);
        sidePanel.add(roomBtn);
        roomBtn.addActionListener(e->new Room());

        y += 55;
        searchRoomBtn = createMenuButton("Search Room", y);
        sidePanel.add(searchRoomBtn);
        searchRoomBtn.addActionListener(e-> new SearchRoom());

        y += 70;
        JLabel infoSection = new JLabel("📋 INFORMATION");
        infoSection.setBounds(25, y, 300, 25);
        infoSection.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoSection.setForeground(PRIMARY);
        sidePanel.add(infoSection);

        y += 30;
        departmentBtn = createMenuButton("Department Information", y);
        sidePanel.add(departmentBtn);
        departmentBtn.addActionListener(e->new Department());

        y += 55;
        employeeInfoBtn = createMenuButton("Employee Information", y);
        sidePanel.add(employeeInfoBtn);
        employeeInfoBtn.addActionListener(e-> new Employee_info());

        y += 55;
        ambulanceBtn = createMenuButton("Ambulance Details", y);
        sidePanel.add(ambulanceBtn);
        ambulanceBtn.addActionListener(e->new Ambulance());

        // ===== LOGOUT =====
        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(1400, 25, 120, 35);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            new Login();
            setVisible(false);
        });
        add(logoutBtn);

        logoutBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(new Color(192, 57, 43));
            }
            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(new Color(231, 76, 60));
            }
        });


        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(320, 120, 1500, 800);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(null);
        add(contentPanel);

        // ================= WELCOME =================
        JLabel welcome = new JLabel("Welcome to Reception Panel");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcome.setForeground(new Color(44, 62, 80));
        welcome.setBounds(40, 30, 700, 40);
        contentPanel.add(welcome);

        JLabel desc = new JLabel("Manage patients, rooms, staff and hospital records efficiently.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        desc.setBounds(40, 80, 900, 25);
        contentPanel.add(desc);

// ================= ABOUT HOSPITAL =================
        JLabel aboutTitle = new JLabel("About HB Medicare Hospital");
        aboutTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        aboutTitle.setForeground(new Color(52, 152, 219));
        aboutTitle.setBounds(40, 140, 400, 30);
        contentPanel.add(aboutTitle);

// Paragraph 1
        JLabel para1 = new JLabel("<html>HB Medicare Hospital is a state-of-the-art healthcare facility committed to providing high-quality, compassionate, and patient-focused medical services. " +
                "We aim to combine advanced technology with experienced medical professionals to ensure accurate diagnoses, effective treatments, and holistic care for every patient.</html>");
        para1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        para1.setForeground(new Color(33, 51, 59));
        para1.setBounds(40, 180, 1150, 50);
        contentPanel.add(para1);

// Paragraph 2
        JLabel para2 = new JLabel("<html>Our hospital operates with modern infrastructure, well-equipped departments, and a dedicated staff of doctors, nurses, and support personnel. " +
                "We prioritize patient safety, ethical medical practices, and transparency in all healthcare operations.</html>");
        para2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        para2.setForeground(new Color(33, 51, 59));
        para2.setBounds(40, 240, 1150, 50);
        contentPanel.add(para2);

// Paragraph 3
        JLabel para3 = new JLabel("<html>The Hospital Management System supports efficient workflow by digitizing patient records, room allocation, staff management, and departmental coordination. " +
                "This helps reduce manual errors, streamline operations, and provide timely healthcare services.</html>");
        para3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        para3.setForeground(new Color(33, 51, 59));
        para3.setBounds(40, 290, 1150, 50);
        contentPanel.add(para3);

// ================= BULLET POINTS =================
        JLabel bulletTitle = new JLabel("Key Features and Services:");
        bulletTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));
        bulletTitle.setForeground(new Color(52, 152, 219));
        bulletTitle.setBounds(40, 360, 400, 25);
        contentPanel.add(bulletTitle);

        Font bulletFont = new Font("Segoe UI", Font.PLAIN, 16);
        Color bulletColor = new Color(33, 51, 59);

// Feature 1
        JLabel bullet1 = new JLabel("• Advanced Medical Departments:");
        bullet1.setFont(bulletFont);
        bullet1.setForeground(bulletColor);
        bullet1.setBounds(60, 390, 500, 25);
        contentPanel.add(bullet1);

        JLabel sub1 = new JLabel("   - Cardiology, Neurology, Orthopedics, Pediatrics");
        sub1.setFont(bulletFont);
        sub1.setForeground(bulletColor);
        sub1.setBounds(80, 415, 600, 25);
        contentPanel.add(sub1);

        JLabel sub2 = new JLabel("   - Diagnostic and Imaging services with modern equipment");
        sub2.setFont(bulletFont);
        sub2.setForeground(bulletColor);
        sub2.setBounds(80, 440, 600, 25);
        contentPanel.add(sub2);

// Feature 2
        JLabel bullet2 = new JLabel("• Experienced Medical Team:");
        bullet2.setFont(bulletFont);
        bullet2.setForeground(bulletColor);
        bullet2.setBounds(60, 480, 500, 25);
        contentPanel.add(bullet2);

        JLabel sub3 = new JLabel("   - Skilled doctors with specialization in multiple fields");
        sub3.setFont(bulletFont);
        sub3.setForeground(bulletColor);
        sub3.setBounds(80, 505, 600, 25);
        contentPanel.add(sub3);

        JLabel sub4 = new JLabel("   - Supportive nursing and administrative staff ensuring smooth operations");
        sub4.setFont(bulletFont);
        sub4.setForeground(bulletColor);
        sub4.setBounds(80, 530, 600, 25);
        contentPanel.add(sub4);

// Feature 3
        JLabel bullet3 = new JLabel("• Patient-Centric Services:");
        bullet3.setFont(bulletFont);
        bullet3.setForeground(bulletColor);
        bullet3.setBounds(60, 570, 500, 25);
        contentPanel.add(bullet3);

        JLabel sub5 = new JLabel("   - 24/7 Emergency and Ambulance services");
        sub5.setFont(bulletFont);
        sub5.setForeground(bulletColor);
        sub5.setBounds(80, 595, 600, 25);
        contentPanel.add(sub5);

        JLabel sub6 = new JLabel("   - Digital record-keeping for faster and accurate treatment");
        sub6.setFont(bulletFont);
        sub6.setForeground(bulletColor);
        sub6.setBounds(80, 620, 600, 25);
        contentPanel.add(sub6);


        setVisible(true);



    }

    private JButton createMenuButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(25, y, 280, 45);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(SIDEBAR_DARK);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMargin(new Insets(0, 15, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(PRIMARY);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(SIDEBAR_DARK);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        new Reception();
    }



}

