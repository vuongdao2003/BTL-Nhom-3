package com.example.demo.dto;

public class ProductsDTO {
    private int id;
    private String image;
    private String productName;
    private Double Price;
    private Integer Category;
    private Integer Quantity;
    private Integer Supplier;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSupplier() {
        return Supplier;
    }

    public void setSupplier(Integer supplier) {
        Supplier = supplier;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public Integer getCategory() {
        return Category;
    }

    public void setCategory(Integer category) {
        this.Category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
