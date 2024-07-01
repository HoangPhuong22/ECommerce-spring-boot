package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.VariationRequest;
import vn.zerocoder.Mart.dto.response.VariationResponse;
import vn.zerocoder.Mart.mapper.VariationMapper;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.model.VariationOption;
import vn.zerocoder.Mart.repository.CategoryRepository;
import vn.zerocoder.Mart.repository.VariationRepositiory;
import vn.zerocoder.Mart.service.VariationService;
import vn.zerocoder.Mart.utils.Normalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VariationServiceImpl implements VariationService {

    private final VariationRepositiory variationRepositiory;
    private final CategoryRepository categoryRepository;

    private final VariationMapper variationMapper;
    @Override
    public Long save(VariationRequest variationRequest) {
        log.info("Create new variation!");
        // Tạo mới một variation từ variationRequest
        Variation variation = variationMapper.toEntity(variationRequest);
        // Kiểm tra xem variation đã tồn tại chưa
        if(variationRepositiory.existsByName(variation.getName())) {
            return -1L;
        }
        return variationRepositiory.save(variation).getId();
    }

    @Override
    public Long update(Long id, VariationRequest variationRequest) {

        log.info("Update variation!");
        // Tìm variation theo id
        Variation variation = variationRepositiory.findById(id).orElseThrow();
        // Set lại tên cho variation
        variation.setName(Normalizer.nameNormalize(variationRequest.getName()));
        // Kiểm tra xem variation đã tồn tại chưa
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
        return variationMapper.toResponse(variation);
    }

    @Override
    public List<VariationResponse> findAllById(List<Long> ids) {
        return variationRepositiory.findAllById(ids).stream().map(variationMapper::toResponse).toList();
    }

    @Override
    public List<VariationResponse> findAll() {
        return variationRepositiory.findAll().stream().map(variationMapper::toResponse).toList();
    }
}
