/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

public class TreatmentManager {
    private DatabaseHandler dbHandler;

    public TreatmentManager(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void addTreatment(String patientId, String treatmentDescription) {
        if (dbHandler.addTreatment(patientId, treatmentDescription)) {
            System.out.println("Treatment added for " + patientId);
        }
    }

    public void displayTreatment(String patientId) {
        String treatment = dbHandler.getTreatment(patientId);
        System.out.println("Treatment for " + patientId + ":\n" + treatment);
    }

    public void editTreatment(String patientId, String newTreatmentDescription) {
        if (dbHandler.updateTreatment(patientId, newTreatmentDescription)) {
            System.out.println("Treatment updated for " + patientId);
        }
    }

    public void deleteTreatment(String patientId) {
        if (dbHandler.deleteTreatment(patientId)) {
            System.out.println("Treatment deleted for " + patientId);
        }
    }
}