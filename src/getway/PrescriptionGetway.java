/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getway;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Drug;
import model.Paginate;
import model.Patient;
import model.Prescription;
import model.PrescriptionDrug;
import model.TemplateDrug;

/**
 *
 * @author RIfat
 */
public class PrescriptionGetway {

    DBConnection connection = new DBConnection();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    DrugGetway drugGetway = new DrugGetway();
    PatientGetway patientGetway = new PatientGetway();

    public boolean save(Prescription prescription, ObservableList<TemplateDrug> templateDrugList) {
        boolean saved = false;
        int prescriptionId = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("insert into prescription values(?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setInt(2, prescription.getPatientId());
            pst.setString(3, prescription.getDate());
            pst.setString(4, prescription.getCc());
            pst.setString(5, prescription.getOe());
            pst.setString(6, prescription.getPd());
            pst.setString(7, prescription.getDd());
            pst.setString(8, prescription.getLabWorkUp());
            pst.setString(9, prescription.getAdvice());
            pst.setString(10, prescription.getNextVisit());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                prescription.setId(rs.getInt(1));
                prescriptionId = rs.getInt(1);
                saved = true;
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();

            for (int i = 0; i < templateDrugList.size(); i++) {
                savePrescriptionDrug(templateDrugList.get(i), prescriptionId);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saved;
    }

    private void savePrescriptionDrug(TemplateDrug templateDrug, int prescriptionId) {
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("insert into prescription_drug values(?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setInt(2, prescriptionId);
            pst.setInt(3, templateDrug.getDrug_id());
            pst.setString(4, templateDrug.getType());
            pst.setString(5, templateDrug.getDose());
            pst.setString(6, templateDrug.getDuration());
            pst.setString(7, templateDrug.getStrength());
            pst.setString(8, templateDrug.getAdvice());
            pst.executeUpdate();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int totalPrescription() {
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(id) from prescription");
            rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;
    }

    public int totalSearchPrescription(String query) {
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(prescription.id) from prescription "
                    + " inner join patient on prescription.patient_id=patient.id "
                    + " where patient.name like ? or  patient.phone like ? or  prescription.date =? ");
            pst.setString(1, query + "%");
            pst.setString(1, query + "%");
            pst.setString(1, query + "%");
            rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public ObservableList<Prescription> prescriptions(Paginate paginate) {
        ObservableList<Prescription> listData = FXCollections.observableArrayList();
        int sl = 1;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from prescription limit " + paginate.getStart() + "," + paginate.getEnd());
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Prescription(
                        (paginate.getStart() + sl++),
                        rs.getInt(1),
                        rs.getInt(2),
                        "Name",
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10)
                ));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listData;
    }

    public ObservableList<Prescription> searchPrescriptions(Paginate paginate, String query) {
        ObservableList<Prescription> listData = FXCollections.observableArrayList();
        int sl = 1;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from prescription"
                    + " inner join patient on prescription.patient_id=patient.id"
                    + " where patient.name like ? or  patient.phone like ? or prescription.date like ?"
                    + " limit " + paginate.getStart() + "," + paginate.getEnd());
            pst.setString(1, query + "%");
            pst.setString(1, "%" + query + "%");
            pst.setString(1, query + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Prescription(
                        (paginate.getStart() + sl++),
                        rs.getInt(1),
                        rs.getInt(2),
                        "Name",
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10)
                ));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listData;
    }

    public ObservableList<Prescription> patientPrescriptions(Patient patient) {
        ObservableList<Prescription> listData = FXCollections.observableArrayList();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select id,date from prescription where patient_id=?");
            pst.setInt(1, patient.getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Prescription(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    public Prescription getPrescription(int id) {
        Prescription prescription = new Prescription();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from prescription where id=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                prescription.setId(rs.getInt(1));
                prescription.setPatientId(rs.getInt(2));
                prescription.setPatientName(patientGetway.selectedPatient(rs.getInt(2)).getName());
                prescription.setDate(rs.getString(3));
                prescription.setCc(rs.getString(4));
                prescription.setOe(rs.getString(5));
                prescription.setPd(rs.getString(6));
                prescription.setDd(rs.getString(7));
                prescription.setLabWorkUp(rs.getString(8));
                prescription.setAdvice(rs.getString(9));
                prescription.setNextVisit(rs.getString(10));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prescription;
    }

    public ObservableList<PrescriptionDrug> getSelectedPrescriptionDrugs(int prescriptionId) {
        ObservableList<PrescriptionDrug> listData = FXCollections.observableArrayList();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from prescription_drug where prescription_id=?");
            pst.setInt(1, prescriptionId);
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new PrescriptionDrug(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        drugGetway.selectedDrug(rs.getInt(2)).getName(),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    public boolean deletePrescription(Prescription prescription) {
        boolean delete = false;
        deleteAllDrugFromPrescription(prescription.getId());
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("delete from prescription where id=" + prescription.getId());
            pst.executeUpdate();
            pst.close();
            con.close();
            connection.con.close();
            delete = true;
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return delete;
    }

    public boolean deleteAllDrugFromPrescription(int prescriptionId) {
        boolean delete = false;
        DBConnection bConnection = new DBConnection();
        Connection connection = bConnection.geConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from prescription_drug where prescription_id=" + prescriptionId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            bConnection.con.close();
            delete = true;
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return delete;
    }

}
