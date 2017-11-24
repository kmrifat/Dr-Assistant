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
public class TemplateDrug {
    private int id;
    private int drug_id;
    private String drug_name;
    private String type;
    private String strength;
    private String dose;
    private String duration;
    private String advice;

    public TemplateDrug() {
    }
    
    

    public TemplateDrug(int id, int drug_id, String type, String strength, String dose, String duration, String advice) {
        this.id = id;
        this.drug_id = drug_id;
        this.type = type;
        this.strength = strength;
        this.dose = dose;
        this.duration = duration;
        this.advice = advice;
    }

    public TemplateDrug(int id, int drug_id, String drug_name, String type, String strength, String dose, String duration, String advice) {
        this.id = id;
        this.drug_id = drug_id;
        this.drug_name = drug_name;
        this.type = type;
        this.strength = strength;
        this.dose = dose;
        this.duration = duration;
        this.advice = advice;
    }

  
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(int drug_id) {
        this.drug_id = drug_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }
    
    
    
    
    
}
