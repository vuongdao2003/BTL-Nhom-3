package com.example.demo.entity;

import jakarta.persistence.*;
@Entity(name="Products")
@Table(name="Products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ProductID")
    private int productID;

    @Column(name="productname")
    private String productName;

    @Column(name = "image")
    private String image;  // Lưu đường dẫn hình ảnh hoặc URL

    @ManyToOne
    @JoinColumn(name="CategoryID")
    private Category category;

    @Column(name="Price")
    private double Price;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Column(name="Quantity")
    private int Quantity;

    @ManyToOne
    @JoinColumn(name="SupplierID")
    private Suppliers Supplier;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        productID = productID;
    }

    public Suppliers getSupplier() {
        return Supplier;
    }

    public void setSupplier(Suppliers supplier) {
        Supplier = supplier;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
