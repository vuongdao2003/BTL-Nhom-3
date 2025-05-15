package com.example.demo.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Revenue")
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double totalSales; // Tổng doanh thu

    private Date reportDate;

    // Constructor
    public Revenue() {}

    public Revenue(int id, double totalSales, Date reportDate) {
        this.id = id;
        this.totalSales = totalSales;
        this.reportDate = reportDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}
