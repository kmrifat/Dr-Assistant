/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getway.bll;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import model.Patient;

/**
 *
 * @author RIfat
 */
public class PatientBLL {

    DBConnection connection = new DBConnection();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public boolean isValid(Patient patient) {
        if (patient.getName() == null || patient.getPhone() == null || patient.getDateOrBirth() == null) {
            showRequired();
            return false;
        } else if (patient.getName().length() == 0 || patient.getPhone().length() == 0) {
            showRequired();
            return false;
        }
        return true;
    }

    public boolean isUnique(Patient patient) {
        boolean unique = true;

        if (patient.getId() == 0) {
            System.out.println("id is zeo");
            con = connection.geConnection();
            try {
                pst = con.prepareStatement("select * from patient where phone=?");
                pst.setString(1, patient.getPhone());
                rs = pst.executeQuery();
                if (rs.next()) {
                    unique = false;
                    showUniqueMessage(patient);
                }
                rs.close();
                pst.close();
                con.close();
                connection.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PatientBLL.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("have id");
            con = connection.geConnection();
            try {
                pst = con.prepareStatement("select * from patient where phone=? and id=?");
                pst.setString(1, patient.getPhone());
                pst.setInt(2, patient.getId());
                rs = pst.executeQuery();
                if (!rs.next()) {
                    unique = false;
                    showUniqueMessage(patient);
                }
                rs.close();
                pst.close();
                con.close();
                connection.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PatientBLL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return unique;
    }

    public void showRequired() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation error");
        alert.setHeaderText("Validation error");
        alert.setContentText("Patient Name is Required \nPhone Number is Required \nDate or birth is required");
        alert.show();
    }

    public void showUniqueMessage(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Phone number Exist");
        alert.setHeaderText(patient.getPhone() + " Already exist in database");
        alert.setContentText("Patient phone number already exist in database. try another one");
        alert.show();
    }

}
