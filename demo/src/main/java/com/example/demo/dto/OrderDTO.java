package com.example.demo.dto;

import java.util.List;

public class OrderDTO {
    private Integer userID;
    private List<Integer> productList;

    // Constructors
    public OrderDTO() {}

    public OrderDTO(Integer userID, List<Integer> productList) {
        this.userID = userID;
        this.productList = productList;
    }

    // Getters and Setters


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public List<Integer> getProductList() {
        return productList;
    }

    public void setProductList(List<Integer> productList) {
        this.productList = productList;
    }
}
