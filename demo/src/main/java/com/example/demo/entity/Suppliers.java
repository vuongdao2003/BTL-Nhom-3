package com.example.demo.entity;

import jakarta.persistence.*;

@Entity(name="Suppliers")
@Table(name="suppliers")
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SupplierID")
    private int SupplierID;
    @Column(name="SupplierName")
    private String SupplierName;
    @Column(name="Address")
    private String Address;
    @Column(name="PhoneNumber")
    private String PhoneNumber ;

    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int supplierID) {
        SupplierID = supplierID;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
