package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.PaymentTypeRequest;
import vn.zerocoder.Mart.dto.response.PaymentTypeResponse;
import vn.zerocoder.Mart.model.PaymentType;
import vn.zerocoder.Mart.repository.PaymentTypeRepository;
import vn.zerocoder.Mart.service.PaymentTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public Long save(PaymentTypeRequest paymentTypeRequest) {
        return 0L;
    }

    @Override
    public Long update(PaymentTypeRequest paymentTypeRequest) {
        return 0L;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<PaymentTypeResponse> findAll() {
        return List.of();
    }

    @Override
    public List<PaymentTypeResponse> findAllByRequiresAccount(Boolean requiresAccount) {
        return paymentTypeRepository.findAllByRequiresAccount(requiresAccount).stream()
                .map(
                        paymentType -> PaymentTypeResponse.builder()
                                .id(paymentType.getId())
                                .name(paymentType.getName())
                                .requiresAccount(paymentType.getRequiresAccount())
                                .build()
                )
                .toList();
    }
}
