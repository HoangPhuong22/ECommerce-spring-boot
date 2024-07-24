package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.model.Order;

public interface OrderService {
    Long save(Long shippingMethodId, Long addressId);

    Order findById(Long id);
}
