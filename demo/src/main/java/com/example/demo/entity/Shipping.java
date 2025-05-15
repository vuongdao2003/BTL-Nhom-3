package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String shippingAddress;

    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    private String status;

    @Lob
    private String shippingInfo; // lưu dưới dạng JSON hoặc text

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    // Constructors
    public Shipping() {}

    public Shipping(String shippingAddress, Date deliveryDate, String status, String shippingInfo, Order order) {
        this.shippingAddress = shippingAddress;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.shippingInfo = shippingInfo;
        this.order = order;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
