package com.example.demo.service;

import com.example.demo.dto.InventoryTransactionDTO;
import com.example.demo.entity.InventoryTransaction;
import com.example.demo.entity.Products;
import com.example.demo.payload.request.AddInventoryTransactionRequest;
import com.example.demo.repository.InventoryTransactionRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.service.imp.InventoryServiceimp;
import com.example.demo.service.imp.InventoryTransactionServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class InventoryTransactionService implements InventoryTransactionServiceimp {
    @Autowired
    private InventoryTransactionRepository inventoryTransactionRepository;

    @Autowired
    private InventoryServiceimp inventoryService;
    @Autowired
    private ProductsRepository productsRepository;
    @Override
    public List<InventoryTransactionDTO> getAllInventoryTransaction() {
        List<InventoryTransactionDTO> inventoryTransactionDTOList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page <InventoryTransaction> inventoryTransactions = inventoryTransactionRepository.findAll(pageRequest);
        for (InventoryTransaction inventoryTransaction : inventoryTransactions) {
            InventoryTransactionDTO inventoryTransactionDTO = new InventoryTransactionDTO();
            inventoryTransactionDTO.setInventoryId(inventoryTransaction.getTransactionID());
            inventoryTransactionDTO.setDate(inventoryTransaction.getTransactionDate());
            inventoryTransactionDTO.setProductId(inventoryTransaction.getProducts().getProductID());
            inventoryTransactionDTO.setQuantitychange(inventoryTransaction.getQuantityChange());
            inventoryTransactionDTO.setType(inventoryTransaction.getType());
            inventoryTransactionDTO.setNote(inventoryTransaction.getNote());
            inventoryTransactionDTOList.add(inventoryTransactionDTO);
        }

        return inventoryTransactionDTOList;
    }

    @Override
    public boolean addInventoryTransaction(AddInventoryTransactionRequest request) {
        try {
            // Tìm sản phẩm
            Products product = productsRepository.findById(request.getProductId()).orElse(null);
            System.out.println(product.getProductName());
            if (product == null) {
                return false; // Không tìm thấy sản phẩm
            }

            // Kiểm tra logic tồn kho nếu là xuất kho
            if ("export".equalsIgnoreCase(request.getType()) && product.getQuantity() < request.getQuantitychange()) {
                return false; // Không đủ hàng để xuất
            }

            // Cập nhật tồn kho
            int updatedQuantity = "import".equalsIgnoreCase(request.getType())
                    ? product.getQuantity() + request.getQuantitychange()
                    : product.getQuantity() - request.getQuantitychange();
            System.out.println(updatedQuantity);
            inventoryService.updateInventory(request.getProductId(), updatedQuantity);

            // Ghi nhận giao dịch
            InventoryTransaction inventoryTransaction = new InventoryTransaction();
            inventoryTransaction.setProducts(product);
            inventoryTransaction.setQuantityChange(request.getQuantitychange());
            inventoryTransaction.setType(request.getType());
            inventoryTransaction.setNote(request.getNote());
            inventoryTransaction.setTransactionDate(LocalDateTime.now());

            inventoryTransactionRepository.save(inventoryTransaction);

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi (hoặc dùng logger nếu có)
            return false;
        }
    }



    @Override
    public boolean deleteInventoryTransaction(int id) {
        if(inventoryTransactionRepository.existsById(id)){
            inventoryTransactionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
