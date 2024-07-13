package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.ShippingAddressRequest;

public interface ShippingAddressService {
    Long save(ShippingAddressRequest shippingAddressRequest);
    Long update(ShippingAddressRequest shippingAddressRequest);
    void delete(Long id);
}
