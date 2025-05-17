/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection conn;

    public DatabaseHandler() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }

    public boolean insertPatient(Patient p) {
        String sql = "INSERT INTO patients (id, name, age, diagnosis) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.setInt(3, p.getAge());
            stmt.setString(4, p.getDiagnosis());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Insert patient failed: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Patient(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("diagnosis")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Patient findPatientById(String id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("diagnosis")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletePatient(String id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // FIX: Change int to String for patientId
    public boolean addTreatment(String patientId, String description) {
        String sql = "INSERT INTO treatments (patient_id, description) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            stmt.setString(2, description);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getTreatment(String patientId) {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT * FROM treatments WHERE patient_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.append("Treatment: ").append(rs.getString("description")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public boolean updateTreatment(String patientId, String newDescription) {
        String sql = "UPDATE treatments SET description = ? WHERE patient_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newDescription);
            stmt.setString(2, patientId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTreatment(String patientId) {
        String sql = "DELETE FROM treatments WHERE patient_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFinancialRecord(String patientId, double totalCharge, double totalDeposited, double totalToReturn) {
        String sql = "INSERT INTO financial_records (patient_id, total_charge, total_deposited, total_return) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            stmt.setDouble(2, totalCharge);
            stmt.setDouble(3, totalDeposited);
            stmt.setDouble(4, totalToReturn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getFinancialRecord(String patientId) {
        String sql = "SELECT * FROM financial_records WHERE patient_id = ?";
        StringBuilder sb = new StringBuilder();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sb.append("Charge: ").append(rs.getDouble("total_charge"))
                  .append(", Deposited: ").append(rs.getDouble("total_deposited"))
                  .append(", Return: ").append(rs.getDouble("total_return")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public boolean addMedicine(String patientId, String medicine) {
        String sql = "INSERT INTO medicines (patient_id, medicine_name) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            stmt.setString(2, medicine);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getMedicines(String patientId) {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT * FROM medicines WHERE patient_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.append("Medicine: ").append(rs.getString("medicine_name")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public boolean deleteMedicine(String patientId, String medicine) {
        String sql = "DELETE FROM medicines WHERE patient_id = ? AND medicine_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            stmt.setString(2, medicine);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
