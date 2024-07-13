package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.ShippingMethodRequest;
import vn.zerocoder.Mart.dto.response.ShippingMethodResponse;

import java.util.List;

public interface ShippingMethodService {
    Long save(ShippingMethodRequest shippingMethod);
    Long update(ShippingMethodRequest shippingMethod);
    void delete(Long id);

    List<ShippingMethodResponse> findAll();

}
