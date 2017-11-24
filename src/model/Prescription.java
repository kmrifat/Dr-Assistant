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
public class Prescription {
    private int sl;
    private int id;
    private int patientId;
    private String patientName;
    private String date;
    private String cc;
    private String oe;
    private String pd;
    private String dd;
    private String labWorkUp;
    private String advice;
    private String nextVisit;

    public Prescription() {
        
    }

    public Prescription(int id, String date) {
        this.id = id;
        this.date = date;
    }
    
    

    public Prescription(int id, int patientId, String date, String cc, String oe, String pd, String dd, String labWorkUp, String advice, String nextVisit) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.cc = cc;
        this.oe = oe;
        this.pd = pd;
        this.dd = dd;
        this.labWorkUp = labWorkUp;
        this.advice = advice;
        this.nextVisit = nextVisit;
    }
    
    

    public Prescription(int id, int patientId, String patientName, String date, String cc, String oe, String pd, String dd, String labWorkUp, String advice, String nextVisit) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.date = date;
        this.cc = cc;
        this.oe = oe;
        this.pd = pd;
        this.dd = dd;
        this.labWorkUp = labWorkUp;
        this.advice = advice;
        this.nextVisit = nextVisit;
    }

    public Prescription(int sl, int id, int patientId, String patientName, String date, String cc, String oe, String pd, String dd, String labWorkUp, String advice, String nextVisit) {
        this.sl = sl;
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.date = date;
        this.cc = cc;
        this.oe = oe;
        this.pd = pd;
        this.dd = dd;
        this.labWorkUp = labWorkUp;
        this.advice = advice;
        this.nextVisit = nextVisit;
    }
    
    

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getLabWorkUp() {
        return labWorkUp;
    }

    public void setLabWorkUp(String labWorkUp) {
        this.labWorkUp = labWorkUp;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getNextVisit() {
        return nextVisit;
    }

    public void setNextVisit(String nextVisit) {
        this.nextVisit = nextVisit;
    }

    
    
    
   
    
    
}
