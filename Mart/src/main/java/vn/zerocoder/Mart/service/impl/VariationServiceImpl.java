package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.VariationRequest;
import vn.zerocoder.Mart.dto.response.VariationResponse;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.model.VariationOption;
import vn.zerocoder.Mart.repository.CategoryRepository;
import vn.zerocoder.Mart.repository.VariationRepositiory;
import vn.zerocoder.Mart.service.VariationService;
import vn.zerocoder.Mart.utils.NameNormalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VariationServiceImpl implements VariationService {

    private final VariationRepositiory variationRepositiory;
    private final CategoryRepository categoryRepository;
    @Override
    public Long save(VariationRequest variationRequest) {

        log.info("Create new variation!");
        Variation variation = Variation.builder()
                .name(NameNormalizer.normalize(variationRequest.getName()))
                .build();

        if(variationRepositiory.existsByName(variation.getName())) {
            return -1L;
        }
        return variationRepositiory.save(variation).getId();
    }

    @Override
    public Long update(Long id, VariationRequest variationRequest) {

        log.info("Update variation!");
        Variation variation = variationRepositiory.findById(id).orElseThrow();

        variation.setName(NameNormalizer.normalize(variationRequest.getName()));

        if(variationRepositiory.existsByNameAndIdNot(variation.getName(), id)) {
            return -1L;
        }
        return variationRepositiory.save(variation).getId();
    }

    @Override
    public Long delete(Long id) {
        log.info("Delete variation!");
        variationRepositiory.deleteById(id);
        return id;
    }

    @Override
    public Variation findVariationById(Long id) {
        return variationRepositiory.findById(id).orElseThrow();
    }

    @Override
    public VariationResponse findById(Long id) {
        Variation variation = variationRepositiory.findById(id).orElseThrow();
        return VariationResponse.builder()
                .id(variation.getId())
                .name(variation.getName())
                .options_id(variation.getOptions().stream().map(VariationOption::getId).toList())
                .build();
    }

    @Override
    public List<VariationResponse> findAllById(List<Long> ids) {
        return variationRepositiory.findAllById(ids).stream()
                .map(variation -> VariationResponse.builder()
                        .id(variation.getId())
                        .name(variation.getName())
                        .options_id(variation.getOptions().stream().map(VariationOption::getId).toList())
                        .build())
                .toList();
    }

    @Override
    public List<VariationResponse> findAll() {
        return variationRepositiory.findAll().stream()
                .map(variation -> VariationResponse.builder()
                        .id(variation.getId())
                        .name(variation.getName())
                        .options_id(variation.getOptions().stream().map(VariationOption::getId).toList())
                        .build())
                .toList();
    }
}
