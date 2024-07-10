package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.PaymentMethodRequest;
import vn.zerocoder.Mart.dto.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    Long save(PaymentMethodRequest paymentMethodRequest);
    Long update(PaymentMethodRequest paymentMethodRequest);
    void delete(Long id);

    List<PaymentMethodResponse> findAll();
    PaymentMethodResponse findById(Long id);
}
