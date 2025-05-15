package com.example.demo.controller;

import com.example.demo.dto.InventoryDTO;
import com.example.demo.payload.ResponData;
import com.example.demo.service.InventoryService;
import com.example.demo.service.imp.InventoryServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Inventory")
public class InventoryController {

    @Autowired
    InventoryServiceimp inventoryServiceimp;

    @GetMapping("/report")
    public ResponseEntity<?> getAllInventory(){
        return new ResponseEntity<>(inventoryServiceimp.getInventotyReport(), HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<?> getInventory(){
        return new ResponseEntity<>(inventoryServiceimp.getInventory(), HttpStatus.OK);
    }

    @PostMapping("/{productid}")
    public ResponseEntity<?> addInventory(@PathVariable int productid){
        ResponData responData = new ResponData();
        boolean success = inventoryServiceimp.addInventory(productid);
        if (success) {
            responData.setMessage("Nhập kho thành công");
            return new ResponseEntity<>(responData, HttpStatus.OK);
        }else {
            responData.setMessage("Nhập kho thất bại");
            return new ResponseEntity<>(responData, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/update/{productid}")
    public ResponseEntity<?> updateInventory(@PathVariable int productid, @RequestParam int quantity){
        ResponData responData = new ResponData();
        System.out.println(productid);
        boolean success = inventoryServiceimp.updateInventory(productid, quantity);
        if (success) {
            responData.setMessage("Cập nhật thành công");
            return new ResponseEntity<>(responData, HttpStatus.OK);

        }
        responData.setMessage("Cập nhật thất bại");
        return new ResponseEntity<>(responData, HttpStatus.BAD_REQUEST);

    }
}
