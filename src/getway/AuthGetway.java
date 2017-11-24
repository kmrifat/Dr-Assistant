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
import model.User;

/**
 *
 * @author RIfat
 */
public class AuthGetway {

    DBConnection connection = new DBConnection();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public boolean save(User user) {
        boolean saved = false;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("insert into user values(?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, user.getUserName());
            pst.setString(3, user.getEmailAddress());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getFullName());
            pst.setString(6, user.getAddress());
            pst.setString(7, user.getPhoneNumber());
            pst.setString(8, user.getInfo());
            pst.setInt(9, user.getShoePhoneInPrescription());
            pst.setInt(10, user.getShowAddressInPrescription());
            pst.setString(11, null);
            pst.executeUpdate();
            pst.close();
            con.close();
            connection.con.close();
            saved = true;
        } catch (SQLException ex) {
            Logger.getLogger(AuthGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saved;
    }

    public boolean update(User user) {
        boolean saved = false;

        con = connection.geConnection();
        try {
            pst = con.prepareStatement("update user set user_name=?,email=?,full_name=?,address=?,phone_number=?,info=?,phone_show=?,address_show=?  where id=?");
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getEmailAddress());
            pst.setString(3, user.getFullName());
            pst.setString(4, user.getAddress());
            pst.setString(5, user.getPhoneNumber());
            pst.setString(6, user.getInfo());
            pst.setInt(7, user.getShoePhoneInPrescription());
            pst.setInt(8, user.getShowAddressInPrescription());
            pst.setInt(9, 1);

            pst.executeUpdate();
            pst.close();
            con.close();
            connection.con.close();
            saved = true;
        } catch (SQLException ex) {
            Logger.getLogger(AuthGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saved;
    }

    public boolean changePassword(String newPass) {
        boolean saved = false;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("update user set password=? where id=?");
            pst.setString(1, newPass);
            pst.setInt(2, 1);

            pst.executeUpdate();
            pst.close();
            con.close();
            connection.con.close();
            saved = true;
        } catch (SQLException ex) {
            Logger.getLogger(AuthGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saved;
    }

    public User getUser() {
        User user = new User();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from user where id=1");
            rs = pst.executeQuery();
            if (rs.next()) {
                user.setUserName(rs.getString(2));
                user.setEmailAddress(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setFullName(rs.getString(5));
                user.setAddress(rs.getString(6));
                user.setPhoneNumber(rs.getString(7));
                user.setInfo(rs.getString(8));
                user.setShoePhoneInPrescription(rs.getInt(9));
                user.setShowAddressInPrescription(rs.getInt(10));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public int totalUser() {
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(id) from user");
            rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public boolean authenticate(String userName, String password) {
        boolean isAuthenticate = false;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from user where user_name=? and password=?");
            pst.setString(1, userName);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if (rs.next()) {
                isAuthenticate = true;
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isAuthenticate;
    }

}
