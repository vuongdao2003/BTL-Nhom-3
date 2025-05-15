package com.example.demo.repository;

import com.example.demo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Integer> {
    List<Products> findByCategory_CategoryName(String categoryName);
    List<Products> findByProductNameContainingIgnoreCaseAndCategory_CategoryNameIgnoreCase(String ProductName, String categoryName);
    List<Products> findByProductNameContainingIgnoreCase(String ProductName);
    List<Products> findByCategory_CategoryNameIgnoreCase(String categoryName);

    Products findByProductID(int productID);
}
