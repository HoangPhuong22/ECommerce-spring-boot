package vn.zerocoder.Mart.service;

public interface OrderService {
    Long save(Long shippingMethodId, Long addressId);
}
