/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author RIfat
 */
public class Patient {
    private int id;
    private String thumbnail;
    private String name;
    private LocalDate dateOrBirth;
    private int sex;
    private String email;
    private String phone;
    private String address;
    private String createdAt;
    private int numberOfPrescription;

    public Patient() {
    }

    public Patient(int id) {
        this.id = id;
    }
    
    
    
    

    public Patient(int id, String thumbnail, String name, LocalDate dateOrBirth, int sex, String email, String phone, String address, String createdAt) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.dateOrBirth = dateOrBirth;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
    }

    public Patient(int id, String thumbnail, String name, LocalDate dateOrBirth, int sex, String email, String phone, String address, String createdAt, int numberOfPrescription) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.dateOrBirth = dateOrBirth;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.numberOfPrescription = numberOfPrescription;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOrBirth() {
        return dateOrBirth;
    }

    public void setDateOrBirth(LocalDate dateOrBirth) {
        this.dateOrBirth = dateOrBirth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getNumberOfPrescription() {
        return numberOfPrescription;
    }

    public void setNumberOfPrescription(int numberOfPrescription) {
        this.numberOfPrescription = numberOfPrescription;
    }

    
    

    
    
    
}
