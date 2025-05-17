/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HospitalGUI extends JFrame {
    private final DatabaseHandler db = new DatabaseHandler();
    private final Map<Integer, ArrayList<String>> treatments = new HashMap<>();
    private final Map<Integer, ArrayList<String>> medicines = new HashMap<>();
    private final Map<Integer, ArrayList<String>> finances = new HashMap<>();

    private final JTextField idField = new JTextField(10);
    private final JTextField nameField = new JTextField(10);
    private final JTextField ageField = new JTextField(5);
    private final JTextField diagnosisField = new JTextField(15);
    private final JTextField searchField = new JTextField(10);
    private final JTextArea outputArea = new JTextArea(12, 50);

    public HospitalGUI() {
        setTitle("Hospital Management System");
        setSize(750, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Patient"));
        inputPanel.add(new JLabel("Patient ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Diagnosis:"));
        inputPanel.add(diagnosisField);

        JPanel actionPanel = new JPanel();
        JButton addButton = new JButton("Add Patient");
        JButton viewButton = new JButton("View All");
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton addTreatmentBtn = new JButton("Add Treatment");
        JButton viewTreatmentBtn = new JButton("View Treatment");
        JButton addMedicineBtn = new JButton("Add Medicine");
        JButton viewMedicineBtn = new JButton("View Medicine");
        JButton addFinancialBtn = new JButton("Add Financial");
        JButton viewFinancialBtn = new JButton("View Financial");

        actionPanel.add(addButton);
        actionPanel.add(viewButton);
        actionPanel.add(new JLabel("Patient ID:"));
        actionPanel.add(searchField);
        actionPanel.add(searchButton);
        actionPanel.add(deleteButton);
        actionPanel.add(addTreatmentBtn);
        actionPanel.add(viewTreatmentBtn);
        actionPanel.add(addMedicineBtn);
        actionPanel.add(viewMedicineBtn);
        actionPanel.add(addFinancialBtn);
        actionPanel.add(viewFinancialBtn);

        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Patient Records"));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addPatient());
        viewButton.addActionListener(e -> viewPatients());
        searchButton.addActionListener(e -> searchPatient());
        deleteButton.addActionListener(e -> deletePatient());
        addTreatmentBtn.addActionListener(e -> addTreatment());
        viewTreatmentBtn.addActionListener(e -> viewTreatment());
        addMedicineBtn.addActionListener(e -> addMedicine());
        viewMedicineBtn.addActionListener(e -> viewMedicine());
        addFinancialBtn.addActionListener(e -> addFinancial());
        viewFinancialBtn.addActionListener(e -> viewFinancial());

        setVisible(true);
    }

    private void addPatient() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String diagnosis = diagnosisField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) {
                JOptionPane.showMessageDialog(this, "Age must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age.");
            return;
        }

        if (db.findPatientById(id) != null) {
            JOptionPane.showMessageDialog(this, "Patient ID already exists.");
            return;
        }

        Patient p = new Patient(id, name, age, diagnosis);
        if (db.insertPatient(p)) {
            clearFields();
            JOptionPane.showMessageDialog(this, "Patient added successfully.");
        }
    }

    private void viewPatients() {
        outputArea.setText("");
        ArrayList<Patient> list = db.getAllPatients();
        if (list.isEmpty()) {
            outputArea.setText("No patients found.");
        } else {
            for (Patient p : list) {
                outputArea.append(p.toString() + "\n");
            }
        }
    }

    private void searchPatient() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID to search.");
            return;
        }
        Object p = db.findPatientById(id);
        outputArea.setText((p != null) ? "Patient Found:\n" + p : "Patient not found.");
    }

    private void deletePatient() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID to delete.");
            return;
        }
        boolean success = db.deletePatient(id);
        outputArea.setText(success ? "Patient with ID " + id + " has been deleted." : "Patient not found.");
    }

    private void addTreatment() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID for treatment.");
            return;
        }
        String desc = JOptionPane.showInputDialog(this, "Enter treatment description:");
        if (desc != null && !desc.trim().isEmpty()) {
            try {
                int patientId = Integer.parseInt(id);
                treatments.computeIfAbsent(patientId, k -> new ArrayList<>()).add(desc);
                outputArea.setText("Treatment added for patient " + id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            }
        }
    }

    private void viewTreatment() {
        String id = searchField.getText().trim();
        try {
            int patientId = Integer.parseInt(id);
            ArrayList<String> treatmentList = treatments.getOrDefault(patientId, new ArrayList<>());
            if (treatmentList.isEmpty()) {
                outputArea.setText("No treatments found for patient " + id);
            } else {
                outputArea.setText("Treatments for patient " + id + ":\n");
                for (String treatment : treatmentList) {
                    outputArea.append("- " + treatment + "\n");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
        }
    }

    private void addMedicine() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID for medicine.");
            return;
        }
        String med = JOptionPane.showInputDialog(this, "Enter medicine name:");
        if (med != null && !med.trim().isEmpty()) {
            try {
                int patientId = Integer.parseInt(id);
                medicines.computeIfAbsent(patientId, k -> new ArrayList<>()).add(med);
                outputArea.setText("Medicine added for patient " + id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            }
        }
    }

    private void viewMedicine() {
        String id = searchField.getText().trim();
        try {
            int patientId = Integer.parseInt(id);
            ArrayList<String> medicineList = medicines.getOrDefault(patientId, new ArrayList<>());
            if (medicineList.isEmpty()) {
                outputArea.setText("No medicines found for patient " + id);
            } else {
                outputArea.setText("Medicines for patient " + id + ":\n");
                for (String medicine : medicineList) {
                    outputArea.append("- " + medicine + "\n");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
        }
    }

    private void addFinancial() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID for financial record.");
            return;
        }
        String fee = JOptionPane.showInputDialog(this, "Enter financial detail (e.g., 'Consultation - $100'):");
        if (fee != null && !fee.trim().isEmpty()) {
            try {
                int patientId = Integer.parseInt(id);
                finances.computeIfAbsent(patientId, k -> new ArrayList<>()).add(fee);
                outputArea.setText("Financial record added for patient " + id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            }
        }
    }

    private void viewFinancial() {
        String id = searchField.getText().trim();
        try {
            int patientId = Integer.parseInt(id);
            ArrayList<String> financialList = finances.getOrDefault(patientId, new ArrayList<>());
            if (financialList.isEmpty()) {
                outputArea.setText("No financial records found for patient " + id);
            } else {
                outputArea.setText("Financial records for patient " + id + ":\n");
                for (String record : financialList) {
                    outputArea.append("- " + record + "\n");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        diagnosisField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HospitalGUI::new);
    }
}



