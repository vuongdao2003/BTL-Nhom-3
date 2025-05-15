package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private Users user;

    private double amount;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    public Payment() {}

    public Payment(Order order,Users user, double amount, String status, Date paymentDate) {
        this.order = order;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
