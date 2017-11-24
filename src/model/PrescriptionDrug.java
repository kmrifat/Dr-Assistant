/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author RIfat
 */
public class PrescriptionDrug {
    int id;
    int prescriptionId;
    int drugId;
    String drugName;
    String drugType;
    String drugDose;
    String drugDuration;
    String drugStrength;
    String drugAdvice;

    public PrescriptionDrug() {
    }

    public PrescriptionDrug(int id, int prescriptionId, int drugId, String drugName, String drugType, String drugDose, String drugDuration, String drugStrength, String drugAdvice) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.drugId = drugId;
        this.drugName = drugName;
        this.drugType = drugType;
        this.drugDose = drugDose;
        this.drugDuration = drugDuration;
        this.drugStrength = drugStrength;
        this.drugAdvice = drugAdvice;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugDose() {
        return drugDose;
    }

    public void setDrugDose(String drugDose) {
        this.drugDose = drugDose;
    }

    public String getDrugDuration() {
        return drugDuration;
    }

    public void setDrugDuration(String drugDuration) {
        this.drugDuration = drugDuration;
    }

    public String getDrugStrength() {
        return drugStrength;
    }

    public void setDrugStrength(String drugStrength) {
        this.drugStrength = drugStrength;
    }

    public String getDrugAdvice() {
        return drugAdvice;
    }

    public void setDrugAdvice(String drugAdvice) {
        this.drugAdvice = drugAdvice;
    }
    
    
    
}
