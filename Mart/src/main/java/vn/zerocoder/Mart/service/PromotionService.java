package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.PromotionRequest;
import vn.zerocoder.Mart.dto.response.PromotionResponse;

import java.util.List;

public interface PromotionService {
    Long save(PromotionRequest request);
    Long update(PromotionRequest request);
    Long delete(Long id);

    PromotionResponse findById(Long id);
    List<PromotionResponse> findAll();
}
