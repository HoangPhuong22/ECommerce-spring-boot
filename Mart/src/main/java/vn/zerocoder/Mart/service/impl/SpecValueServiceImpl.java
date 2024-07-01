package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.SpecValueRequest;
import vn.zerocoder.Mart.dto.response.SpecValueResponse;
import vn.zerocoder.Mart.mapper.SpecValueMapper;
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

    private final SpecValueMapper specValueMapper;

    @Override
    public Long save(SpecValueRequest request) {
        // Lấy ra product_id và spec_id từ request
        Long product_id = request.getProduct_id();
        Long spec_id = request.getSpec_id();
        // Tìm product và spec từ id
        Product product = productRepository.findById(product_id).orElseThrow();
        Spec spec = specRepository.findById(spec_id).orElseThrow();
        // Kiểm tra xem đã tồn tại giá trị của product và spec chưa
        if(valueRepository.existsByProduct_IdAndSpec_Id(product_id, spec_id)) {
            return -1L;
        }
        // Chuyển request thành entity
        SpecValue value = specValueMapper.toEntity(request, product, spec);

        // Lưu vào database
        return valueRepository.save(value).getId();
    }

    @Override
    public Long update(SpecValueRequest request) {

        // Lấy ra spec_value_id từ request
        Long spec_value_id = request.getId();
        SpecValue value = valueRepository.findById(spec_value_id).orElseThrow();

        // Lấy ra product_id và spec_id từ request
        Long product_id = request.getProduct_id();
        Long spec_id = request.getSpec_id();

        // Kiểm tra xem đã tồn tại giá trị của product và spec chưa
        if(valueRepository.existsByProduct_IdAndSpec_IdAndIdNot(product_id, spec_id, spec_value_id)) {
            return -1L;
        }
        // Tìm product và spec từ id
        Product product = productRepository.findById(product_id).orElseThrow();
        Spec specs = specRepository.findById(spec_id).orElseThrow();
        // Cập nhật giá trị
        value.setValue(request.getValue());
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
        return specValueMapper.toResponse(value);
    }

    @Override
    public List<SpecValueResponse> findAll() {
        return valueRepository.findAll().stream().map(specValueMapper::toResponse).toList();
    }
}
