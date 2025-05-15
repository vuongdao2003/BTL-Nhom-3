package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.Products;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<Order> createOrders(OrderDTO orderDTO) {
        List<Order> createdOrders = new ArrayList<>();

        List<Integer> productList = orderDTO.getProductList();
        if (productList == null || productList.isEmpty()) {
            throw new RuntimeException("Danh sách sản phẩm rỗng!");
        }

        for (int productID : productList) {
            Order order = new Order();

            // Tạo 1 order với userid từ getusserid của entity users, totalamount lấy từ price trong entity products, orderdate là thời điểm hiện tại
            Products products = productsRepository.findByProductID(productID);
            order.setProduct(products);
            order.setTotalAmount(products.getPrice());
            order.setUser(userRepository.findByUserID(orderDTO.getUserID()));
            order.setOrderDate(new Date());

            orderRepository.save(order);
            createdOrders.add(order);
        }

        return createdOrders;
    }

    public boolean deleteOrder(int orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    // All Order
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Order by ID
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    // Update Status
    public Order updateOrderStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }
        return null;
    }

}
