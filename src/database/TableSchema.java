/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RIfat
 */
public class TableSchema {

    public static void connect() {
        Connection conn = null;
        Statement stmt = null;

        String url = "jdbc:sqlite:dr_assistant.db";

        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS `user` (\n"
                    + "	`id`            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	`user_name`	TEXT,\n"
                    + "	`email`         TEXT NOT NULL,\n"
                    + "	`password`	TEXT NOT NULL,\n"
                    + " `full_name`     TEXT,\n"
                    + " `address`       TEXT,\n"
                    + " `phone_number`  TEXT,\n"
                    + " `info`          TEXT,\n"
                    + " `phone_show`    INTEGER,\n"
                    + " `address_show`  INTEGER,\n"
                    + "	`created_at`	TEXT\n"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS `drug` (\n"
                    + "	`id`            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	`name`          TEXT NOT NULL UNIQUE,\n"
                    + "	`genric_name`	TEXT NOT NULL,\n"
                    + " `note`          TEXT,\n"
                    + "	`created_at`	TEXT\n"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS `patient` (\n"
                    + "	`id`            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " `thumbnail`     TEXT,\n"
                    + "	`name`          TEXT NOT NULL,\n"
                    + " `date_of_birth` TEXT NOT NULL,\n"
                    + " `sex`           INTEGER NOT NULL,\n"
                    + "	`email`         TEXT,\n"
                    + "	`phone`         TEXT,\n"
                    + "	`address`	TEXT,\n"
                    + "	`created_at`	TEXT\n"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS `template` (\n"
                    + "	`id`            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	`name`          TEXT,\n"
                    + " `note`          TEXT,\n"
                    + "	`cc`            TEXT,\n"
                    + "	`oe`            TEXT,\n"
                    + "	`pd`            TEXT,\n"
                    + "	`dd`            TEXT,\n"
                    + "	`lab_workup`	TEXT,\n"
                    + "	`advices`	TEXT\n"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS `template_drug` (\n"
                    + "	`id`            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " `template_id`   INTEGER NOT NULL,\n"
                    + "	`drug_id`	INTEGER NOT NULL,\n"
                    + "	`drug_type`	TEXT,\n"
                    + "	`drug_strength`	TEXT,\n"
                    + "	`drug_dose`	TEXT,\n"
                    + "	`drug_duration`	TEXT,\n"
                    + "	`drug_advice`	TEXT\n"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS `prescription` (\n"
                    + "	`id`            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	`patient_id`	INTEGER NOT NULL,\n"
                    + "	`date`          TEXT NOT NULL,\n"
                    + "	`cc`            TEXT,\n"
                    + "	`oe`            TEXT,\n"
                    + "	`pd`            TEXT,\n"
                    + "	`dd`            TEXT,\n"
                    + "	`lab_workup`	TEXT,\n"
                    + "	`advice`	TEXT,\n"
                    + " `next_visit`    TEXT\n"
                    + ");"
                    + "CREATE TABLE IF NOT EXISTS `prescription_drug` (\n"
                    + "	`id`            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "	`prescription_id`	INTEGER NOT NULL,\n"
                    + "	`drug_id`	TEXT NOT NULL,\n"
                    + "	`drug_type`	TEXT,\n"
                    + "	`drug_dose`	TEXT,\n"
                    + "	`drug_duration`	TEXT,\n"
                    + "	`drug_strength`	TEXT,\n"
                    + "	`drug_advice`	TEXT\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(TableSchema.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
