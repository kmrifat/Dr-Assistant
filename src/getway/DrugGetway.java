/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getway;

import database.DBConnection;
import getway.bll.DrugBLL;
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

/**
 *
 * @author RIfat
 */
public class DrugGetway {

    DBConnection connection = new DBConnection();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    DrugBLL dbll = new DrugBLL();

    public boolean save(Drug drug) {
        if (dbll.isValid(drug)) {
            if (dbll.isUnique(drug, 0)) {
                con = connection.geConnection();
                try {
                    pst = con.prepareStatement("insert into drug values(?,?,?,?,?)");
                    pst.setString(1, null);
                    pst.setString(2, drug.getName());
                    pst.setString(3, drug.getGenricName());
                    pst.setString(4, drug.getNote());
                    pst.setString(5, drug.getCreatedAt());
                    pst.executeUpdate();
                    pst.close();
                    con.close();
                    connection.con.close();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return false;
    }

    public boolean update(Drug drug) {
        if (dbll.isValid(drug)) {
            if (dbll.isUnique(drug, drug.getId())) {
                con = connection.geConnection();
                try {
                    pst = con.prepareStatement("update drug set name=?,genric_name=?,note=? where id=?");
                    pst.setString(1, drug.getName());
                    pst.setString(2, drug.getGenricName());
                    pst.setString(3, drug.getNote());
                    pst.setInt(4, drug.getId());
                    pst.executeUpdate();
                    pst.close();
                    con.close();
                    connection.con.close();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return false;
    }

    ;
    
    
    public Drug selectedDrug(int id) {
        Drug drug = new Drug();
        DBConnection bConnection = new DBConnection();
        Connection nCon = bConnection.geConnection();
        try {
            PreparedStatement nPst = nCon.prepareStatement("select id,name from drug where id=?");
            nPst.setInt(1, id);
            ResultSet nRs = nPst.executeQuery();
            if(nRs.next()){
                drug.setId(nRs.getInt(1));
                drug.setName(nRs.getString(2));
            }
            nRs.close();
            nPst.close();
            nCon.close();
            bConnection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return drug;
    }

    public boolean delete(int id) {
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("delete from drug where id=" + id);
            pst.executeUpdate();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int totalDrug() {
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(id) from drug");
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

    public int totalSearchDrug(String query) {
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(id) from drug where name like ? or genric_name like ?");
            pst.setString(1, query + "%");
            pst.setString(2, query + "%");
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

    public ObservableList<Drug> drugs(Paginate paginate) {
        ObservableList<Drug> listData = FXCollections.observableArrayList();
        int sl = 1;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from drug limit " + paginate.getStart() + "," + paginate.getEnd());
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Drug(
                        rs.getInt(1), 
                        (paginate.getStart() + sl++), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5),
                        totalDrugInPrescription(rs.getInt(1)),
                        totalDrugInTemplate(rs.getInt(1))
                ));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    public ObservableList<Drug> searchDrug(Paginate paginate, String query) {
        ObservableList<Drug> listData = FXCollections.observableArrayList();
        int sl = 1;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from drug where name like ? or genric_name like ? limit " + paginate.getStart() + "," + paginate.getEnd());
            pst.setString(1, query + "%");
            pst.setString(2, query + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Drug(rs.getInt(1), (paginate.getStart() + sl++), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
                        totalDrugInPrescription(rs.getInt(1)),
                        totalDrugInTemplate(rs.getInt(1))));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    public ObservableList<Drug> allDrugs() {
        ObservableList<Drug> listData = FXCollections.observableArrayList();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select id, name from drug order by name");
            rs = pst.executeQuery();
            while (rs.next()) {
                listData.add(new Drug(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DrugGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listData;
    }

    private int totalDrugInPrescription(int drugId) {
        int total = 0;
        DBConnection bConnection = new DBConnection();
        Connection conn = bConnection.geConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select count(id) from prescription_drug where drug_id=?");
            preparedStatement.setInt(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
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
    
     private int totalDrugInTemplate(int drugId) {
        int total = 0;
        DBConnection bConnection = new DBConnection();
        Connection conn = bConnection.geConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select count(id) from template_drug where drug_id=?");
            preparedStatement.setInt(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
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
