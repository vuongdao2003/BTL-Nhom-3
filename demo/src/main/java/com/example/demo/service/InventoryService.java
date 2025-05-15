package com.example.demo.service;

import com.example.demo.dto.InventoryDTO;
import com.example.demo.dto.InventoryReportDTO;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.Products;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.InventoryTransactionRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.service.imp.InventoryServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService implements InventoryServiceimp {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryTransactionRepository inventoryTransactionRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<InventoryDTO> getInventory() {
        List<InventoryDTO> inventoryDTOList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Inventory> inventoryPage = inventoryRepository.findAll(pageRequest);
        for (Inventory inventory : inventoryPage) {
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setId(inventory.getInventoryID());
            inventoryDTO.setQuantity(inventory.getQuantity());
            inventoryDTO.setLast_update(inventory.getLastUpdated());
            inventoryDTO.setProduct_id(inventory.getProducts().getProductID());
            inventoryDTO.setProduct_name(inventory.getProducts().getProductName());
            inventoryDTOList.add(inventoryDTO);
        }return inventoryDTOList;
    }

    @Override
    public boolean addInventory(int productid) {
        Products products = productsRepository.findById(productid).orElse(null);
        Inventory isInventory = inventoryRepository.findByProducts(products);
        if (isInventory != null) {
            return false;
        }
        System.out.println(products);
        if (products == null) {
            return false;
        }
        Inventory inventory = new Inventory();
        inventory.setQuantity(products.getQuantity());
        inventory.setProducts(products);
        inventory.setLastUpdated(LocalDateTime.now());
        inventoryRepository.save(inventory);
        return true;
    }

    @Override
    public boolean updateInventory(int productid, int quantity) {
        Products products = productsRepository.findById(productid).orElse(null);
        System.out.println(products.getProductName());
        Inventory inventory = inventoryRepository.findByProducts(products);
        System.out.println(inventory.getQuantity());
        if (inventory == null) {
            return false;
        }
        System.out.println(products);
        if (products == null) {
            return false;
        }
        products.setQuantity(quantity);
        inventory.setQuantity(quantity);
        inventory.setLastUpdated(LocalDateTime.now());
        productsRepository.save(products);
        inventoryRepository.save(inventory);
        return true;
    }


    @Override
    public boolean deleteInventory(int inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElse(null);
        if (inventory == null) {
            return false;
        }
        inventoryRepository.delete(inventory);
        return true;
    }

    @Override
    public List<InventoryReportDTO> getInventotyReport() {
        List<InventoryReportDTO> inventoryReportDTOList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Inventory> inventoryPage = inventoryRepository.findAll(pageRequest);
        for (Inventory inventory : inventoryPage) {
            Products products = inventory.getProducts();
            int quantity = products.getQuantity();
            String status = quantity <=8 ? "Cần nhập thêm ": "Còn hàng";
            InventoryReportDTO inventoryReportDTO = new InventoryReportDTO();
            inventoryReportDTO.setProductName(products.getProductName());
            inventoryReportDTO.setProductId(products.getProductID());
            inventoryReportDTO.setQuantity(quantity);
            inventoryReportDTO.setStatus(status);
            inventoryReportDTO.setDate(inventory.getLastUpdated());
            inventoryReportDTOList.add(inventoryReportDTO);
        }
        return inventoryReportDTOList;
    }
}
