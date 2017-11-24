/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getway;

import database.DBConnection;
import getway.bll.PatientBLL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Paginate;
import model.Patient;

/**
 *
 * @author RIfat
 */
public class PatientGetway {

    PatientBLL patientBLL = new PatientBLL();

    public boolean save(Patient patient) {
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        if (patientBLL.isValid(patient) && patientBLL.isUnique(patient)) {
            con = connection.geConnection();
            try {
                pst = con.prepareStatement("insert into patient values(?,?,?,?,?,?,?,?,?)");
                pst.setString(1, null);
                pst.setString(2, patient.getThumbnail());
                pst.setString(3, patient.getName());
                pst.setString(4, String.valueOf(patient.getDateOrBirth()));
                pst.setInt(5, patient.getSex());
                pst.setString(6, patient.getEmail());
                pst.setString(7, patient.getPhone());
                pst.setString(8, patient.getAddress());
                pst.setString(9, patient.getCreatedAt());
                pst.executeUpdate();
                pst.close();
                con.close();
                connection.con.close();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(PatientGetway.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean update(Patient patient) {
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean isUpdated = false;
        if (patientBLL.isValid(patient) && patientBLL.isUnique(patient)) {
            System.out.println(connection.geConnection());
            con = connection.geConnection();
            try {
                pst = con.prepareStatement("update patient set thumbnail=?,name=?,date_of_birth=?,sex=?,email=?,phone=?,address=? where id=?");
                pst.setString(1, patient.getThumbnail());
                pst.setString(2, patient.getName());
                pst.setString(3, String.valueOf(patient.getDateOrBirth()));
                pst.setInt(4, patient.getSex());
                pst.setString(5, patient.getEmail());
                pst.setString(6, patient.getPhone());
                pst.setString(7, patient.getAddress());
                pst.setInt(8, patient.getId());
                pst.executeUpdate();
                pst.close();
                con.close();
                connection.con.close();
                isUpdated = true;
            } catch (SQLException ex) {
                Logger.getLogger(PatientGetway.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isUpdated;
    }

    public boolean delete(int id) {
        
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("delete from patient where id=" + id);
            pst.executeUpdate();
            pst.close();
            con.close();
            connection.con.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PatientGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int totalPatient() {
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(id) from patient");
            rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public int totalSearchPatient(String query) {
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(id) from patient where name like ? or phone like ? or email like ?");
            pst.setString(1, query + "%");
            pst.setString(2, query + "%");
            pst.setString(3, query + "%");
            rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public Patient selectedPatient(int patientId) {
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        con = connection.geConnection();
        Patient patient = new Patient();
        try {
            pst = con.prepareStatement("select * from patient where id=" + patientId);
            rs = pst.executeQuery();
            if (rs.next()) {
                patient = new Patient(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        LocalDate.parse(rs.getString(4)),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)
                );
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PatientGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;
    }

    public ObservableList<Patient> patients(Paginate paginate) {
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<Patient> listData = FXCollections.observableArrayList();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from patient limit " + paginate.getStart() + "," + paginate.getEnd());
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Patient(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        LocalDate.parse(rs.getString(4)),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        totalPrescriptionByPatient(rs.getInt(1))
                ));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PatientGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    public ObservableList<Patient> searchPatient(Paginate paginate, String query) {
        DBConnection connection = new DBConnection();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ObservableList<Patient> listData = FXCollections.observableArrayList();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from patient where name like ? or phone like ? or email like ? limit " + paginate.getStart() + "," + paginate.getEnd());
            pst.setString(1, query + "%");
            pst.setString(2, query + "%");
            pst.setString(3, query + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Patient(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        LocalDate.parse(rs.getString(4)),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        totalPrescriptionByPatient(rs.getInt(1))
                ));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PatientGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    private int totalPrescriptionByPatient(int patientId) {
        int total = 0;
        DBConnection bConnection = new DBConnection();
        Connection conn = bConnection.geConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select count(id) from prescription where patient_id=?");
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
            bConnection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    
   

}
