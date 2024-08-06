package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.model.Order;

import java.util.List;

public interface OrderService {
    Long save(Long shippingMethodId, Long addressId);
    Long updateStatus(Long id, String status);
    List<Order> findTop6ByOrderByCreatedAtDesc();
    Order findById(Long id);
    List<Order> findAll();
}
