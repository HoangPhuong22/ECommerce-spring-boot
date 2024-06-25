package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.repository.ProductDetailRepository;
import vn.zerocoder.Mart.service.ProductDetailService;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;

    @Override
    public Long save(ProductDetailRequest detailRequest) {
        return 0L;
    }

    @Override
    public Long update(ProductDetailRequest detailRequest) {
        return 0L;
    }

    @Override
    public Long delete(Long id) {
        return 0L;
    }

    @Override
    public ProductDetailResponse findById(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow();
        return ProductDetailResponse.builder()
                .id(productDetail.getId())
                .sku(productDetail.getSku())
                .image(productDetail.getProductImage())
                .price(productDetail.getPrice())
                .quantity(productDetail.getQty())
                .build();
    }
}
