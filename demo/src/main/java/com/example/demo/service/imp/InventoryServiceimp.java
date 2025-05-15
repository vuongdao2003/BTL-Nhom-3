package com.example.demo.service.imp;

import com.example.demo.dto.InventoryDTO;
import com.example.demo.dto.InventoryReportDTO;

import java.util.List;

public interface InventoryServiceimp {
    List<InventoryDTO> getInventory();
    boolean addInventory(int productid);
    boolean updateInventory(int productid, int quantity);
    boolean deleteInventory(int inventoryId);
    List<InventoryReportDTO> getInventotyReport();
}
