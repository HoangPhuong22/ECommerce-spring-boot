package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ShippingMethodRequest;
import vn.zerocoder.Mart.dto.response.ShippingMethodResponse;
import vn.zerocoder.Mart.repository.ShippingMethodRepository;
import vn.zerocoder.Mart.service.ShippingMethodService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingMethodServiceImpl implements ShippingMethodService {

    private final ShippingMethodRepository shippingMethodRepository;

    @Override
    public Long save(ShippingMethodRequest shippingMethod) {
        return 0L;
    }

    @Override
    public Long update(ShippingMethodRequest shippingMethod) {
        return 0L;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ShippingMethodResponse> findAll() {
        return shippingMethodRepository.findAll().stream().map(
                shippingMethod -> ShippingMethodResponse.builder()
                        .id(shippingMethod.getId())
                        .name(shippingMethod.getName())
                        .price(shippingMethod.getPrice())
                        .description(shippingMethod.getDescription())
                        .build()
        ).toList();
    }
}
