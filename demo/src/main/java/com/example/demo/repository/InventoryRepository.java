package com.example.demo.repository;

import com.example.demo.entity.Inventory;
import com.example.demo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    Inventory findByInventoryID(int inventoryID);
    Inventory findByProducts(Products products);
}
