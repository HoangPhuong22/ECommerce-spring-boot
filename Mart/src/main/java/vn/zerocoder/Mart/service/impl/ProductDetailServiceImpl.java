package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.repository.ProductDetailRepository;
import vn.zerocoder.Mart.service.ProductDetailService;
import vn.zerocoder.Mart.service.ProductService;
import vn.zerocoder.Mart.service.VariationOptionService;
import vn.zerocoder.Mart.service.VariationService;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final VariationService variationService;
    private final ProductService productService;
    private final VariationOptionService optionService;

    @Override
    public Long save(ProductDetailRequest detailRequest) {
        String sku = detailRequest.getProduct_id().toString();

        ProductDetail productDetail = ProductDetail.builder()
                .sku(detailRequest.getSku())
                .productImage(detailRequest.getImage())
                .price(detailRequest.getPrice())
                .qty(detailRequest.getQuantity())
                .build();

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
