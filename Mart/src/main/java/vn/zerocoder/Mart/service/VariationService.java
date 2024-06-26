package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.VariationRequest;
import vn.zerocoder.Mart.dto.response.VariationResponse;
import vn.zerocoder.Mart.model.Variation;

import java.util.List;

public interface VariationService {

    Long save(VariationRequest variationRequest);
    Long update(Long id, VariationRequest variationRequest);
    Long delete(Long id);

    Variation findVariationById(Long id);

    VariationResponse findById(Long id);

    List<VariationResponse> findAllById(List<Long> ids);
    List<VariationResponse> findAll();
}
