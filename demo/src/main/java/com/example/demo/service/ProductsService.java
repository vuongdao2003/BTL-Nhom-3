package com.example.demo.service;

import com.example.demo.dto.ProductsDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.Products;
import com.example.demo.entity.Suppliers;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.repository.SuppliersRepository;
import com.example.demo.service.imp.FileServiceimp;
import com.example.demo.service.imp.InventoryServiceimp;
import com.example.demo.service.imp.ProductsServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService implements ProductsServiceimp {
    @Autowired
    ProductsRepository productsReponsitory;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    FileServiceimp fileService;
    @Autowired
    InventoryServiceimp inventoryService;
    @Autowired
    SuppliersRepository suppliersRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public boolean insertProduct(MultipartFile file, String productName, Integer category, Double price, Integer quantity, Integer supplierID) {
        boolean isInserSucces = false;
        try {
            boolean isSaveSuccess = fileService.saveFile(file);
            if (isSaveSuccess) {
                Optional<Suppliers> supplierOpt = suppliersRepository.findById(supplierID);
                Optional<Category> categoryOpt = categoryRepository.findById(category);
                if (supplierOpt.isPresent()) {
                    Category supplierCategory = categoryOpt.get();
                    System.out.println("ProductName: " + productName); // debug
                    Suppliers supplier = supplierOpt.get();
                    Products products = new Products();
                    products.setProductName(productName);
                    products.setCategory(supplierCategory);
                    products.setPrice(price);
                    products.setQuantity(quantity);
                    products.setSupplier(supplier);
                    products.setImage(file.getOriginalFilename());

                    productsReponsitory.save(products);
                    isInserSucces = true;
                } else {
                    System.out.println("Supplier not found with ID: " + supplierID);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in insertProduct: " + e.getMessage());
        }

        return isInserSucces;
    }

    @Override
    public List<ProductsDTO> getProducts() {
        List<ProductsDTO> productsDTOList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Products> productsPage = productsReponsitory.findAll(pageRequest);
        for (Products products : productsPage) {
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setProductName(products.getProductName());
            productsDTO.setCategory(products.getCategory().getCategoryID());
            productsDTO.setPrice(products.getPrice());
            productsDTO.setImage(products.getImage());
            productsDTO.setSupplier(products.getSupplier().getSupplierID());
            productsDTO.setQuantity(products.getQuantity());
            productsDTOList.add(productsDTO);
        }
        return productsDTOList;
    }

    @Override
    public List<ProductsDTO> getProductsByCategoryT(String category) {
        List<ProductsDTO> productsDTOList = new ArrayList<>();
        List<Products> productsPage = productsReponsitory.findByCategory_CategoryName(category);
        for (Products products : productsPage) {
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setProductName(products.getProductName());
            productsDTO.setCategory(products.getCategory().getCategoryID());
            productsDTO.setPrice(products.getPrice());
            productsDTO.setImage(products.getImage());
            productsDTO.setQuantity(products.getQuantity());
            productsDTOList.add(productsDTO);
        }
        return productsDTOList;
    }

    @Override
    public boolean deleteProduct(int id) {
        if (productsReponsitory.existsById(id)) {
            productsReponsitory.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateProduct(ProductsDTO productDTO) {
        Optional<Products> product = productsReponsitory.findById(productDTO.getId());

        if(!product.isPresent()) {
            return false;
        }
        Products products = product.get();
        Inventory isInventory = inventoryRepository.findByProducts(products);

        if (productDTO.getProductName() != null) {
            products.setProductName(productDTO.getProductName());
        }
        if(productDTO.getImage() != null) {
            products.setImage(productDTO.getImage());
        }
        if (productDTO.getCategory() != null) {
            Category category = categoryRepository.findById(productDTO.getCategory()).orElse(null);

            if (category != null) {
                products.setCategory(category);
            }
        }
        if (productDTO.getPrice() != null) {
            products.setPrice(productDTO.getPrice());
        }
        if (productDTO.getQuantity() != null) {
            products.setQuantity(productDTO.getQuantity());

            if (isInventory!=null){
                isInventory.setQuantity(productDTO.getQuantity());
                inventoryRepository.save(isInventory);
            }
        }
        if (productDTO.getImage() != null) {
            products.setImage(productDTO.getImage());
        }
        if(productDTO.getSupplier() != null) {
            Suppliers supplier = suppliersRepository.findById(productDTO.getSupplier()).orElse(null);

           if(supplier != null) {
               products.setSupplier(supplier);
           }
        }
        productsReponsitory.save(products);
       return true;
    }

    @Override
    public List<ProductsDTO> searchProducts(String name, String category) {
        List<Products> products;
        if ((name == null || name.isEmpty()) && (category == null || category.isEmpty())) {
            // Trả về tất cả sản phẩm nếu không có điều kiện tìm kiếm nào
            products = productsReponsitory.findAll();
        } else if (name != null && !name.isEmpty() && category != null && !category.isEmpty()) {
            products = productsReponsitory.findByProductNameContainingIgnoreCaseAndCategory_CategoryNameIgnoreCase(name, category);
        } else if (name != null && !name.isEmpty()) {
            products = productsReponsitory.findByProductNameContainingIgnoreCase(name);
        } else {
            products = productsReponsitory.findByCategory_CategoryNameIgnoreCase(category);
        }
        List<ProductsDTO> productsDTOList = new ArrayList<>();
        for (Products product: products) {
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setProductName(product.getProductName());
            productsDTO.setCategory(product.getCategory().getCategoryID());
            productsDTO.setPrice(product.getPrice());
            productsDTO.setImage(product.getImage());
            productsDTO.setQuantity(product.getQuantity());
            productsDTO.setSupplier(product.getSupplier().getSupplierID());
            productsDTOList.add(productsDTO);

        }
        return productsDTOList;
    }

}


