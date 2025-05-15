package com.example.demo.service.imp;

import com.example.demo.dto.InventoryTransactionDTO;
import com.example.demo.payload.request.AddInventoryTransactionRequest;

import java.util.List;

public interface InventoryTransactionServiceimp {
    List<InventoryTransactionDTO>  getAllInventoryTransaction();
    boolean addInventoryTransaction(AddInventoryTransactionRequest request);
    boolean deleteInventoryTransaction(int id);
}
