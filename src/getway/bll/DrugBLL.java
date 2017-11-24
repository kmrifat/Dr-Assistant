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
import model.Drug;

/**
 *
 * @author RIfat
 */
public class DrugBLL {

    DBConnection connection = new DBConnection();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public boolean isValid(Drug drug) {
        if (drug.getName() == null || drug.getGenricName() == null) {
            showRequired();
            return false;
        } else if (drug.getName().length() == 0 || drug.getGenricName().length() == 0) {
            showRequired();
            return false;
        }
        return true;
    }

    public boolean isUnique(Drug drug, int id) {
        boolean unique = true;

        if (id == 0) {
            try {
                con = connection.geConnection();
                pst = con.prepareStatement("select * from drug where name=?");
                pst.setString(1, drug.getName());
                rs = pst.executeQuery();
                if (rs.next()) {
                    showUniqueMessage(drug);
                    unique = false;
                }
                rs.close();
                pst.close();
                con.close();
                connection.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DrugBLL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                con = connection.geConnection();
                pst = con.prepareStatement("select * from drug where name=? and id=?");
                pst.setString(1, drug.getName());
                pst.setInt(2, id);
                rs = pst.executeQuery();
                if (!rs.next()) {
                    showUniqueMessage(drug);
                    unique = false;
                }
                rs.close();
                pst.close();
                con.close();
                connection.con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DrugBLL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return unique;
    }

    public void showRequired() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation error");
        alert.setHeaderText("Validation error");
        alert.setContentText("Trade Name is Required \nGenric Name is Required");
        alert.showAndWait();
    }

    public void showUniqueMessage(Drug drug) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Drug Exist");
        alert.setHeaderText(drug.getName() + " Already exist in database");
        alert.setContentText("Drug name already exist in database. try another one");
        alert.show();
    }

}
