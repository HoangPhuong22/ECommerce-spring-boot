package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.SpecValueRequest;
import vn.zerocoder.Mart.dto.response.SpecValueResponse;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.Spec;
import vn.zerocoder.Mart.model.SpecValue;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.repository.SpecRepository;
import vn.zerocoder.Mart.repository.SpecValueRepository;
import vn.zerocoder.Mart.service.SpecValueService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecValueServiceImpl implements SpecValueService {

    private final SpecValueRepository valueRepository;
    private final SpecRepository specRepository;
    private final ProductRepository productRepository;

    @Override
    public Long save(SpecValueRequest spec) {
        Long product_id = spec.getProduct_id();
        Long spec_id = spec.getSpec_id();
        Product product = productRepository.findById(product_id).orElseThrow();
        Spec specs = specRepository.findById(spec_id).orElseThrow();
        if(valueRepository.existsByProduct_IdAndSpec_Id(product_id, spec_id)) {
            return -1L;
        }
        SpecValue value = SpecValue.builder()
                .value(spec.getValue())
                .product(product)
                .spec(specs)
                .build();
        return valueRepository.save(value).getId();
    }

    @Override
    public Long update(SpecValueRequest specValue) {
        Long spec_value_id = specValue.getId();
        SpecValue value = valueRepository.findById(spec_value_id).orElseThrow();

        Long product_id = specValue.getProduct_id();
        Long spec_id = specValue.getSpec_id();
        if(valueRepository.existsByProduct_IdAndSpec_IdAndIdNot(product_id, spec_id, spec_value_id)) {
            return -1L;
        }
        Product product = productRepository.findById(product_id).orElseThrow();
        Spec specs = specRepository.findById(spec_id).orElseThrow();

        value.setValue(specValue.getValue());
        value.setProduct(product);
        value.setSpec(specs);
        return valueRepository.save(value).getId();
    }

    @Override
    public Long delete(Long id) {
        SpecValue value = valueRepository.findById(id).orElseThrow();
        valueRepository.delete(value);
        return id;
    }

    @Override
    public SpecValueResponse findById(Long id) {
        SpecValue value = valueRepository.findById(id).orElseThrow();
        return SpecValueResponse.builder()
                .id(value.getId())
                .value(value.getValue())
                .product_id(value.getProduct().getId())
                .spec_id(value.getSpec().getId())
                .build();
    }

    @Override
    public List<SpecValueResponse> findAll() {
        return valueRepository.findAll().stream()
                .map(value -> SpecValueResponse.builder()
                        .id(value.getId())
                        .value(value.getValue())
                        .product_id(value.getProduct().getId())
                        .spec_id(value.getSpec().getId())
                        .build())
                .toList();
    }
}
