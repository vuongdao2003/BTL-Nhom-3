package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Shipping;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Tạo shipping
    public Shipping createShipping(int orderID, String shippingAddress, Date deliveryDate, String shippingInfo) {
        try {
            Order order = orderRepository.findByOrderID(orderID);
            if (order == null) {
                throw new RuntimeException("Không tìm thấy Order với ID: " + orderID);
            }

            Shipping shipping = new Shipping();
            shipping.setShippingAddress(shippingAddress);
            shipping.setDeliveryDate(deliveryDate);
            shipping.setStatus("Đang giao");
            shipping.setShippingInfo(shippingInfo);
            shipping.setOrder(order);

            // Cập nhật trạng thái đơn hàng
            order.setStatus("Đang giao");

            // Lưu Shipping và Order
            Shipping savedShipping = shippingRepository.save(shipping);
            orderRepository.save(order);

            return savedShipping;
        } catch (RuntimeException e) {
            // Lỗi cụ thể do developer tạo (ví dụ: không tìm thấy order)
            throw e;
        } catch (Exception e) {
            // Lỗi không xác định (ví dụ: lỗi database, null pointer...)
            throw new RuntimeException("Lỗi khi tạo đơn: " + e.getMessage(), e);
        }
    }


    // Cập nhật status
    public Shipping updateStatus(int shippingId, String newStatus) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderID: " + shippingId));

        shipping.setStatus(newStatus);
        shippingRepository.save(shipping);

        // Update order status
        Order order = shipping.getOrder();
        order.setStatus(newStatus);
        orderRepository.save(order);

        return shipping;
    }

    // Truy vấn shipping
    public Shipping trackShipping(int shippingId) {
        return shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderID: " + shippingId));
    }

    // 4. Track shipping history
    public List<Shipping> trackShippingHistory() {
        return shippingRepository.findAll();
    }
}
