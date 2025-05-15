package com.example.demo.controller;

import com.example.demo.payload.ResponData;
import com.example.demo.payload.request.AddInventoryTransactionRequest;
import com.example.demo.service.InventoryTransactionService;
import com.example.demo.service.imp.InventoryTransactionServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/InventoryTransaction")
public class InventoryTransactionController {
    @Autowired
    InventoryTransactionServiceimp inventoryTransactionServiceimp;

    @GetMapping("")
    public ResponseEntity<?> getInventory(){
        return new ResponseEntity<>(inventoryTransactionServiceimp.getAllInventoryTransaction(), HttpStatus.OK);
    }

    @PostMapping("/addTrans/{type}")
    public ResponseEntity<?> addInventoryTransaction(@PathVariable String type,@RequestBody AddInventoryTransactionRequest request) {
        ResponData responData = new ResponData();
        request.setType(type);
        System.out.println(request.getType());
        boolean success = inventoryTransactionServiceimp.addInventoryTransaction(request);
        System.out.println(success);
        if(success) {
            responData.setMessage("Cập nhật thành công");
            return new ResponseEntity<>(responData, HttpStatus.OK);
        }
        responData.setMessage("Cập nhật thất bại");
        responData.setSuccess(false);
        return new ResponseEntity<>(responData, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInventoryTransaction(@PathVariable int id) {

        ResponData responData = new ResponData();
        responData.setSuccess(inventoryTransactionServiceimp.deleteInventoryTransaction(id));
        responData.setMessage("Xóa thành công");
        return new ResponseEntity<>(responData, HttpStatus.OK);
    }
}
