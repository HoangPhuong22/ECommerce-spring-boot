package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;

public interface ProductDetailService {
    Long save(ProductDetailRequest detailRequest);
    Long update(ProductDetailRequest detailRequest);
    Long delete(Long id);

    ProductDetailResponse findById(Long id);
}
