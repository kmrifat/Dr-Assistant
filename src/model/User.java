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
public class User {
    private int id;
    private String userName;
    private String emailAddress;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String info;
    private int shoePhoneInPrescription;
    private int showAddressInPrescription;

    public User() {
    }

   
    
    

    public User(String userName, String emailAddress, String password, String fullName, String info, int shoePhoneInPrescription, int showAddressInPrescription) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.fullName = fullName;
        this.info = info;
        this.shoePhoneInPrescription = shoePhoneInPrescription;
        this.showAddressInPrescription = showAddressInPrescription;
    }
    
    

    public User(int id, String userName, String emailAddress, String password, String fullName, String phoneNumber, String address, String info, int shoePhoneInPrescription, int showAddressInPrescription) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.info = info;
        this.shoePhoneInPrescription = shoePhoneInPrescription;
        this.showAddressInPrescription = showAddressInPrescription;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getShoePhoneInPrescription() {
        return shoePhoneInPrescription;
    }

    public void setShoePhoneInPrescription(int shoePhoneInPrescription) {
        this.shoePhoneInPrescription = shoePhoneInPrescription;
    }

    public int getShowAddressInPrescription() {
        return showAddressInPrescription;
    }

    public void setShowAddressInPrescription(int showAddressInPrescription) {
        this.showAddressInPrescription = showAddressInPrescription;
    }
    
    
    
    
    
    
    
}
