package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.VariationOptionRequest;
import vn.zerocoder.Mart.dto.response.VariationOptionResponse;
import vn.zerocoder.Mart.dto.response.VariationResponse;

import java.util.List;

public interface VariationOptionService {

    Long save(VariationOptionRequest request);
    Long update(Long id, VariationOptionRequest request);
    Long delete(Long id);

    VariationOptionResponse findById(Long id);
    List<VariationOptionResponse> findAllById(List<Long> ids);
}
