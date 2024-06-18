package vn.zerocoder.Mart.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.VariationOptionRequest;
import vn.zerocoder.Mart.dto.response.VariationOptionResponse;
import vn.zerocoder.Mart.dto.response.VariationResponse;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.model.VariationOption;
import vn.zerocoder.Mart.repository.VariationOptionRepository;
import vn.zerocoder.Mart.repository.VariationRepositiory;
import vn.zerocoder.Mart.service.VariationOptionService;
import vn.zerocoder.Mart.service.VariationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationOptionServiceImpl implements VariationOptionService {

    private final VariationOptionRepository variationOptionRepository;
    private final VariationRepositiory variationRepositiory;
    @Override
    public Long save(VariationOptionRequest request) {
        Variation variation = variationRepositiory.findById(request.getVariation_id()).orElseThrow();
        VariationOption variationOption = VariationOption.builder()
                .value(request.getValue())
                .variation(variation)
                .build();
        if(variationOptionRepository.existsByValue(request.getValue())) {
            return -1L;
        }
        return variationOptionRepository.save(variationOption).getId();
    }

    @Override
    public Long update(Long id, VariationOptionRequest request) {
        VariationOption variationOption = variationOptionRepository.findById(id).orElseThrow();
        Variation variation = variationRepositiory.findById(request.getVariation_id()).orElseThrow();
        variationOption.setValue(request.getValue());
        variationOption.setVariation(variation);
        if(variationOptionRepository.existsByValue(request.getValue())) {
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
        return VariationOptionResponse.builder()
                .id(variationOption.getId())
                .value(variationOption.getValue())
                .variation_id(variationOption.getVariation().getId())
                .build();
    }

    @Override
    public List<VariationOptionResponse> findAllById(List<Long> ids) {
        List<VariationOption> variationOptions = variationOptionRepository.findAllById(ids);
        return variationOptions.stream().map(variationOption -> VariationOptionResponse.builder()
                .id(variationOption.getId())
                .value(variationOption.getValue())
                .variation_id(variationOption.getVariation().getId())
                .build()).toList();
    }
}
