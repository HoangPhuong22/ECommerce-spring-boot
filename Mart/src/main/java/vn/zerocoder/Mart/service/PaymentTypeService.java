package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.PaymentTypeRequest;
import vn.zerocoder.Mart.dto.response.PaymentTypeResponse;
import vn.zerocoder.Mart.model.PaymentType;

import java.util.List;

public interface PaymentTypeService {
    Long save(PaymentTypeRequest paymentTypeRequest);
    Long update(PaymentTypeRequest paymentTypeRequest);
    void delete(Long id);

    List<PaymentTypeResponse> findAll();
    List<PaymentTypeResponse> findAllByRequiresAccount(Boolean requiresAccount);
}
