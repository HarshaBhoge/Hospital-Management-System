package src.hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPatient extends JFrame implements ActionListener {


    JTextField idField, patientNameField, ageField, addressField, phoneField, dateField, depositField, roomNoField,digField;
    JRadioButton maleRadio, femaleRadio, otherRadio;
    JComboBox<String> bloodGroupCombo;
    JButton submitBtn, cancelBtn, clrBtn;

    Choice c1;

    AddPatient() {
        // main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(52, 73, 94);
                Color color2 = new Color(52, 152, 219);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 550, 70);
        headerPanel.setBackground(new Color(52, 73, 94));


        JLabel titleLabel = new JLabel("⚕ ADD NEW PATIENT");
        titleLabel.setBounds(20, 15, 400, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);


        // Back button in header
        clrBtn = new JButton("Clear Form");
        clrBtn.setBounds(410, 20, 100, 30);
        clrBtn.setFont(new Font("Arial", Font.BOLD, 13));
        clrBtn.setBackground(new Color(52, 73, 94));
        clrBtn.setForeground(Color.WHITE);
        clrBtn.setFocusPainted(false);
        clrBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        clrBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clrBtn.addActionListener(this);
        headerPanel.add(clrBtn);
        mainPanel.add(headerPanel);


        JPanel formPanel=new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20,80,500,695);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199),3));

        JLabel formTitle = new JLabel("Patient Registration Form");
        formTitle.setBounds(0, 5, 500, 38);
        formTitle.setFont(new Font("Arial", Font.BOLD, 20));

        formTitle.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(formTitle);

        int yPos=40;
        int gap=55;


        // Patient Name
        addLabel(formPanel, "Patient ID *", 30, yPos);
        idField = addTextField(formPanel, 30, yPos + 22);

        // Father's Name
        addLabel(formPanel, "Patient Name *", 30, yPos + gap);
        patientNameField = addTextField(formPanel, 30, yPos + gap + 22);

        // Age
        addLabel(formPanel, "Age *", 30, yPos + gap * 2);
        ageField = addTextField(formPanel, 30, yPos + gap * 2 + 22);

        // Gender
        addLabel(formPanel, "Gender *", 30, yPos + gap * 3);
        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        genderPanel.setBounds(30, yPos + gap * 3 + 22, 440, 30);
        genderPanel.setBackground(Color.WHITE);

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);

        maleRadio.setBackground(Color.WHITE);
        femaleRadio.setBackground(Color.WHITE);
        otherRadio.setBackground(Color.WHITE);
        maleRadio.setFont(new Font("Arial", Font.PLAIN, 13));
        femaleRadio.setFont(new Font("Arial", Font.PLAIN, 13));
        otherRadio.setFont(new Font("Arial", Font.PLAIN, 13));

        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);
        formPanel.add(genderPanel);

        // Blood Group
        addLabel(formPanel, "Blood Group *", 30, yPos + gap * 4);
        String[] bloodGroups = {"Select", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        bloodGroupCombo = new JComboBox<>(bloodGroups);
        bloodGroupCombo.setBounds(30, yPos + gap * 4 + 22, 440, 30);
        bloodGroupCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        bloodGroupCombo.setBackground(Color.WHITE);
        formPanel.add(bloodGroupCombo);

        // diagnosis
        addLabel(formPanel, "Diagnosis *", 30, yPos + gap * 5);
        digField = addTextField(formPanel, 30, yPos + gap * 5 + 22);


        //create formatted date
        String todayDate=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        // Admit Date
        addLabel(formPanel, "Admit Date * (DD-MM-YYYY)", 30, yPos + gap * 6);
        dateField = addTextField(formPanel, 30, yPos + gap * 6 + 22);
        dateField.setText(todayDate);
        dateField.setEditable(false);
        formPanel.add(dateField);


        // Address
        addLabel(formPanel, "Phone Number *", 30, yPos + gap * 7);
        phoneField = addTextField(formPanel, 30, yPos + gap * 7+ 22);

        // phone no
        addLabel(formPanel, "Room Number *", 30, yPos + gap * 8);
//        roomNoField = addTextField(formPanel, 30, yPos + gap * 8 + 22);

        //----- Room Choice -----//
        c1 = new Choice();
        try {
            conn c= new conn();
            ResultSet resultSet= c.statement.executeQuery("Select * from Room");
            while (resultSet.next()){
                c1.add(resultSet.getString("room_no"));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        c1.setBounds(30,502,440,50);
        c1.setFont(new Font("Arial", Font.PLAIN, 13));
        c1.setBackground(Color.WHITE);

        formPanel.add(c1);


        // Deposit
        addLabel(formPanel, "Address *", 30, yPos + gap * 9);
        addressField = addTextField(formPanel, 30, yPos + gap * 9 + 22);

        addLabel(formPanel, "Deposit *", 30, yPos + gap * 10);
        depositField = addTextField(formPanel, 30, yPos + gap * 10 + 22);

        //Buttons
        submitBtn = new JButton("Submit");
        submitBtn.setBounds(120, 650, 110, 33);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 15));
        submitBtn.setBackground(new Color(46, 204, 113));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createEmptyBorder());
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.addActionListener(this);

        submitBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                submitBtn.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(MouseEvent e) {
                submitBtn.setBackground(new Color(46, 204, 113));
            }
        });
        formPanel.add(submitBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(270, 650, 110, 33);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 15));
        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder());
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(this);

        cancelBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                cancelBtn.setBackground(new Color(192, 57, 43));
            }
            public void mouseExited(MouseEvent e) {
                cancelBtn.setBackground(new Color(231, 76, 60));
            }
        });
        formPanel.add(cancelBtn);


        mainPanel.add(formPanel);
        add(mainPanel);

        setTitle("Add New Patient - Hospital Management System");
        setSize(550, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void addLabel(JPanel panel, String text, int x,int y){
        JLabel label=new JLabel(text);
        label.setBounds(x,y,440,18);
        label.setFont(new Font("Arial",Font.BOLD,14));

        panel.add(label);
    }


    private JTextField addTextField(JPanel panel, int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 440, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(textField);
        return textField;
    }



    public static void main(String[] args) {

        new AddPatient();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submitBtn) {
            // validation
            if (idField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Patient ID", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (patientNameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Patient name", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (ageField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Age", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!maleRadio.isSelected() && !femaleRadio.isSelected() && !otherRadio.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select Gender", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bloodGroupCombo.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Please select Blood group", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (digField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Diagnosis", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (phoneField.getText().trim().isEmpty() || phoneField.getText().trim().length()<10){
                JOptionPane.showMessageDialog(this, "Please enter Valid Phone Number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (addressField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Address", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (depositField.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Please enter Deposit","Validation Error",JOptionPane.ERROR_MESSAGE);
            }


            String Patient_ID = idField.getText();
            String Patient_Name = patientNameField.getText();
            String Age = ageField.getText();

            // Radio button se gender get karna
            String Gender = "";
            if (maleRadio.isSelected()) {
                Gender = "Male";
            } else if (femaleRadio.isSelected()) {
                Gender = "Female";
            } else if (otherRadio.isSelected()) {
                Gender = "Other";
            }
            String Diagnosis = digField.getText();
            String  Blood_Group= (String) bloodGroupCombo.getSelectedItem();
            String Admit_Date= dateField.getText();
            String  Phone_Number= phoneField.getText();
            String Room_Number = c1.getSelectedItem();
            String  Address= addressField.getText();
            String  Deposit  = depositField.getText();


            try {
                conn c= new conn();
                String q = "Insert into patient_info values('"+ Patient_ID+"','"+Patient_Name +"','"+ Age +"','"+ Gender+"','"+ Blood_Group +"','"+ Diagnosis+"','"+ Admit_Date+"','"+ Address+"','"+Phone_Number +"','"+Deposit +"','"+ Room_Number+"')";
                String q1 = "update Room set Avalability ='Occupied' where room_no ="+Room_Number;
                c.statement.executeUpdate(q);
                c.statement.executeUpdate(q1);
                JOptionPane.showMessageDialog(null, "Patient Added Successfully!");
                clearForm();
            }
            catch (Exception E){
                E.printStackTrace();
            }



        }
        else if (e.getSource() == cancelBtn ) {
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to close this form?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                new Reception();
                dispose();
            }
        } else if (e.getSource() == clrBtn) {
            clearForm();
        }



    }
    private void clearForm() {
        idField.setText("");
        patientNameField.setText("");
        ageField.setText("");
        digField.setText("");
        addressField.setText("");
        phoneField.setText("");
        depositField.setText("");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);
        genderGroup.clearSelection();

        bloodGroupCombo.setSelectedIndex(0);

    }
}










