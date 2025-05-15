package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<List<Order>> createOrder(@RequestBody OrderDTO orderDTO) {
        List<Order> createdOrders = orderService.createOrders(orderDTO);
        return ResponseEntity.ok(createdOrders);
    }

    @DeleteMapping("/{orderID}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderID) {
        boolean deleted = orderService.deleteOrder(orderID);
        if (deleted) {
            return ResponseEntity.ok("Hủy đơn thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đơn không tồn tại");
        }
    }

}
