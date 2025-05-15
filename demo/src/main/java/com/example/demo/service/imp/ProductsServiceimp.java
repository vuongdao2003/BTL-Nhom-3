package com.example.demo.service.imp;

import com.example.demo.dto.ProductsDTO;
import com.example.demo.entity.Products;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductsServiceimp {
    boolean insertProduct(  MultipartFile file,
                           String productName,
                           Integer category,
                           Double price,
                           Integer quantity,
                           Integer supplierID
                            );
    List<ProductsDTO> getProducts();
    List<ProductsDTO> getProductsByCategoryT(String category);
    boolean deleteProduct(int id);
    boolean updateProduct(ProductsDTO product);
    List<ProductsDTO> searchProducts(String name,String category);
}
