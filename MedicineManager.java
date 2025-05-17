/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

import java.util.ArrayList;
import java.util.HashMap;

public class MedicineManager {
    private final HashMap<Integer, ArrayList<String>> medicineMap = new HashMap<>();

    public void addMedicine(int recordNumber, String medicine) {
        medicineMap.putIfAbsent(recordNumber, new ArrayList<>());
        medicineMap.get(recordNumber).add(medicine);
        System.out.println("Medicine added for " + recordNumber + ": " + medicine);
    }

    public void displayMedicine(int recordNumber) {
        ArrayList<String> list = medicineMap.get(recordNumber);
        if (list == null || list.isEmpty()) {
            System.out.println("No medicines for patient " + recordNumber);
        } else {
            System.out.println("Medicines for patient " + recordNumber + ": " + list);
        }
    }

    public void deleteMedicine(int recordNumber, String medicine) {
        ArrayList<String> list = medicineMap.get(recordNumber);
        if (list != null) {
            list.remove(medicine);
            System.out.println("Deleted " + medicine + " for patient " + recordNumber);
        }
    }
}