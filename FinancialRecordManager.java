/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

public class FinancialRecordManager {
    private DatabaseHandler dbHandler;

    public FinancialRecordManager(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void addFinancialRecord(String patientId, double totalCharge, double totalDeposited) {
        double totalToReturn = totalDeposited - totalCharge;
        if (dbHandler.addFinancialRecord(patientId, totalCharge, totalDeposited, totalToReturn)) {
            System.out.println("Financial record added for: " + patientId);
        }
    }

    public void displayFinancialRecords(String patientId) {
        String record = dbHandler.getFinancialRecord(patientId);
        System.out.println("Financial records for " + patientId + ":\n" + record);
    }

    static class FinancialRecord {
        double totalCharge;
        double totalDeposited;
        double totalMoneyToReturn;

        public FinancialRecord(double totalCharge, double totalDeposited) {
            this.totalCharge = totalCharge;
            this.totalDeposited = totalDeposited;
            this.totalMoneyToReturn = totalDeposited - totalCharge;
        }
    }
}