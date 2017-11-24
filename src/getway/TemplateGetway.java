/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getway;

import database.DBConnection;
import getway.bll.TemplateBLL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Paginate;
import model.Template;
import model.TemplateDrug;

/**
 *
 * @author RIfat
 */
public class TemplateGetway {

    DBConnection connection = new DBConnection();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    TemplateBLL templateBLL = new TemplateBLL();

    public Template selectedTemplate(int templateId) {
        Template template = new Template();
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from template where id=?");
            pst.setInt(1, templateId);
            rs = pst.executeQuery();
            if (rs.next()) {
                template.setId(rs.getInt(1));
                template.setTemplateName(rs.getString(2));
                template.setNote(rs.getString(3));
                template.setCc(rs.getString(4));
                template.setOe(rs.getString(5));
                template.setPd(rs.getString(6));
                template.setDd(rs.getString(7));
                template.setLab_workup(rs.getString(8));
                template.setAdvice(rs.getString(9));
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }

        return template;
    }

    public ObservableList<TemplateDrug> getSelectedTemplateDrugs(int templateId) {
        ObservableList<TemplateDrug> dataList = FXCollections.observableArrayList();
        int sl = 1;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select * from template_drug where template_id=?");
            pst.setInt(1, templateId);
            rs = pst.executeQuery();
            while (rs.next()) {
                dataList.add(new TemplateDrug(sl++, rs.getInt(3), getDrugName(rs.getInt(3)), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataList;
    }

    public boolean save(Template template, ObservableList<TemplateDrug> templateDrugList) {
        boolean save = false;
        int templateId = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("insert into template values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, template.getTemplateName());
            pst.setString(3, template.getNote());
            pst.setString(4, template.getCc());
            pst.setString(5, template.getOe());
            pst.setString(6, template.getPd());
            pst.setString(7, template.getDd());
            pst.setString(8, template.getLab_workup());
            pst.setString(9, template.getAdvice());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                templateId = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
            connection.con.close();
            for (int i = 0; i < templateDrugList.size(); i++) {
                saveTemplateDrug(templateDrugList.get(i), templateId);
            }
            save = true;
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return save;
    }

    public boolean update(Template template, ObservableList<TemplateDrug> templateDrugList) {
        boolean save = false;
        int templateId = 0;
        if (deleteAllDrugFromTemplate(template.getId())) {
            con = connection.geConnection();
            try {
                pst = con.prepareStatement("update template set name=?,note=?,cc=?,oe=?,pd=?,dd=?,lab_workup=?,advices=? where id=?");
                pst.setString(1, template.getTemplateName());
                pst.setString(2, template.getNote());
                pst.setString(3, template.getCc());
                pst.setString(4, template.getOe());
                pst.setString(5, template.getPd());
                pst.setString(6, template.getDd());
                pst.setString(7, template.getLab_workup());
                pst.setString(8, template.getAdvice());
                pst.setInt(9, template.getId());
                pst.executeUpdate();
                pst.close();
                con.close();
                connection.con.close();
                for (int i = 0; i < templateDrugList.size(); i++) {
                    saveTemplateDrug(templateDrugList.get(i), template.getId());
                }
                save = true;
            } catch (SQLException ex) {
                Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return save;
    }

    private void saveTemplateDrug(TemplateDrug drug, int templateId) {
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("insert into template_drug values(?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setInt(2, templateId);
            pst.setInt(3, drug.getDrug_id());
            pst.setString(4, drug.getType());
            pst.setString(5, drug.getStrength());
            pst.setString(6, drug.getDose());
            pst.setString(7, drug.getDuration());
            pst.setString(8, drug.getAdvice());
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int total() {
        int total = 0;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select count(id) from template");
            rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public int getTotalDrug(int templateId) {
        int toal = 0;
        DBConnection bConnection = new DBConnection();
        Connection connection = bConnection.geConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(id) from template_drug where template_id = ?");
            preparedStatement.setInt(1, templateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                toal = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toal;
    }

    public ObservableList<Template> templates() {
        ObservableList<Template> dataList = FXCollections.observableArrayList();
        int sl = 1;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select id,name,note from template");
            rs = pst.executeQuery();
            while (rs.next()) {
                dataList.add(new Template(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        sl++,
                        getTotalDrug(rs.getInt(1))
                ));
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataList;
    }
    
    public ObservableList<Template> searchTemplates(String query) {
        ObservableList<Template> dataList = FXCollections.observableArrayList();
        int sl = 1;
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("select id,name,note from template where name like ? or note like ?");
            pst.setString(1, query + "%");
            pst.setString(2, query + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                dataList.add(new Template(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        sl++,
                        getTotalDrug(rs.getInt(1))
                ));
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataList;
    }
    
    

    private String getDrugName(int drugId) {
        String drugName = "";
        DBConnection bConnection = new DBConnection();
        Connection c = bConnection.geConnection();
        try {
            PreparedStatement preparedStatement = c.prepareStatement("select name from drug where id=?");
            preparedStatement.setInt(1, drugId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                drugName = resultSet.getString(1);
            }
            resultSet.close();
            preparedStatement.close();
            c.close();
            bConnection.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TemplateGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return drugName;
    }

    public boolean deleteTemplate(Template template) {
        boolean delete = false;
        deleteAllDrugFromTemplate(template.getId());
        con = connection.geConnection();
        try {
            pst = con.prepareStatement("delete from template where id=" + template.getId());
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

    private boolean deleteAllDrugFromTemplate(int templateId) {
        boolean delete = false;
        DBConnection bConnection = new DBConnection();
        Connection connection = bConnection.geConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from template_drug where template_id=" + templateId);
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
