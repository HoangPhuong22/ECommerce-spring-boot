package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.VariationOptionRequest;
import vn.zerocoder.Mart.dto.response.VariationOptionResponse;
import vn.zerocoder.Mart.mapper.OptionMapper;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.model.VariationOption;
import vn.zerocoder.Mart.repository.VariationOptionRepository;
import vn.zerocoder.Mart.repository.VariationRepositiory;
import vn.zerocoder.Mart.service.VariationOptionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationOptionServiceImpl implements VariationOptionService {

    private final VariationOptionRepository variationOptionRepository;
    private final VariationRepositiory variationRepositiory;

    private final OptionMapper optionMapper;
    @Override
    public Long save(VariationOptionRequest request) {
        // Lấy ra variation từ request
        Variation variation = variationRepositiory.findById(request.getVariation_id()).orElseThrow();

        // Tạo entity từ request
        VariationOption variationOption = optionMapper.toEntity(request, variation);

        // Kiểm tra xem đã tồn tại giá trị của variation chưa
        if(variationOptionRepository.existsByValue(request.getValue())) {
            return -1L;
        }
        return variationOptionRepository.save(variationOption).getId();
    }

    @Override
    public Long update(Long id, VariationOptionRequest request) {
        // Lấy ra variation_option từ id
        VariationOption variationOption = variationOptionRepository.findById(id).orElseThrow();
        Variation variation = variationRepositiory.findById(request.getVariation_id()).orElseThrow();

        // Cập nhật giá trị cho entity
        variationOption.setValue(request.getValue());
        variationOption.setVariation(variation);

        // Kiểm tra xem đã tồn tại giá trị của variation chưa
        if(variationOptionRepository.existsByValueAndIdNot(request.getValue(), id)) {
            return -1L;
        }
        return variationOptionRepository.save(variationOption).getId();
    }

    @Override
    public Long delete(Long id) {
        variationOptionRepository.deleteById(id);
        return id;
    }

    @Override
    public VariationOptionResponse findById(Long id) {
        VariationOption variationOption = variationOptionRepository.findById(id).orElseThrow();
        return optionMapper.toResponse(variationOption);
    }

    @Override
    public List<VariationOptionResponse> findAllById(List<Long> ids) {
        List<VariationOption> variationOptions = variationOptionRepository.findAllById(ids);
        return variationOptions.stream().map(optionMapper::toResponse).toList();
    }

    @Override
    public List<VariationOptionResponse> findAll() {
        List<VariationOption> variationOptions = variationOptionRepository.findAll();
        return variationOptions.stream().map(optionMapper::toResponse).toList();
    }
}
