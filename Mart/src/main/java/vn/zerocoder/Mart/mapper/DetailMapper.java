package vn.zerocoder.Mart.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.model.VariationOption;
import vn.zerocoder.Mart.utils.FileUtils;

import java.util.List;

@Component
public class DetailMapper {

    @Value("${file.upload-dir}")
    private String Path;

    public ProductDetailResponse toProductDetailResponse(ProductDetail detail) {
        return ProductDetailResponse.builder()
                .id(detail.getId())
                .sku(detail.getSku())
                .image(detail.getProductImage())
                .price(detail.getPrice())
                .quantity(detail.getQty())
                .product_id(detail.getProduct().getId())
                .options(detail.getOptions().stream().map(VariationOption::getId).toList())
                .build();
    }
    public ProductDetail toProductDetail(ProductDetailRequest detailRequest, Product product, List<VariationOption> options, StringBuilder sku) {
        return ProductDetail.builder()
                .sku(sku.toString())
                .productImage(FileUtils.save(Path, detailRequest.getDetailImage()))
                .price(detailRequest.getPrice())
                .qty(detailRequest.getQuantity())
                .product(product)
                .options(options)
                .build();
    }
}
