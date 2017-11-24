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
public class Template {

    private int id;
    private String templateName;
    private String note;
    private String cc;
    private String oe;
    private String pd;
    private String dd;
    private String lab_workup;
    private String advice;
    private int sl;
    private int totalDrug;

    public Template() {
    }

    public Template(int id, String templateName, String note, int sl) {
        this.id = id;
        this.templateName = templateName;
        this.note = note;
        this.sl = sl;
    }

    public Template(int id, String templateName, String note, int sl, int totalDrug) {
        this.id = id;
        this.templateName = templateName;
        this.note = note;
        this.sl = sl;
        this.totalDrug = totalDrug;
    }

    
    
    public Template(int id, String templateName, String note, String cc, String oe, String pd, String dd, String lab_workup, String advice) {
        this.id = id;
        this.templateName = templateName;
        this.note = note;
        this.cc = cc;
        this.oe = oe;
        this.pd = pd;
        this.dd = dd;
        this.lab_workup = lab_workup;
        this.advice = advice;
    }

    public Template(int id, String templateName, String note, String cc, String oe, String pd, String dd, String lab_workup, String advice, int sl) {
        this.id = id;
        this.templateName = templateName;
        this.note = note;
        this.cc = cc;
        this.oe = oe;
        this.pd = pd;
        this.dd = dd;
        this.lab_workup = lab_workup;
        this.advice = advice;
        this.sl = sl;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getOe() {
        return oe;
    }

    public void setOe(String oe) {
        this.oe = oe;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getLab_workup() {
        return lab_workup;
    }

    public void setLab_workup(String lab_workup) {
        this.lab_workup = lab_workup;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getTotalDrug() {
        return totalDrug;
    }

    public void setTotalDrug(int totalDrug) {
        this.totalDrug = totalDrug;
    }
    
    
    
    
}
