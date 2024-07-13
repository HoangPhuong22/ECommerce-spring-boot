package vn.zerocoder.Mart.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.ProductRequest;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.model.*;
import vn.zerocoder.Mart.utils.FileUtils;

@Component
public class ProductMapper {

    @Value("${file.upload-dir}")
    private String Path;

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .promotionRate(product.getPromotionRate())
                .description(product.getDescription())
                .image(product.getProductImage())
                .quantity(product.getQuantity())
                .status(product.getStatus())
                .brand_id(product.getBrand().getId())
                .category_id(product.getCategory().getId())
                .detail_id(product.getProductDetails().stream()
                        .map(BaseEntity::getId)
                        .toList())
                .spec_value_id(product.getSpecValues().stream()
                        .map(SpecValue::getId)
                        .toList())
                .advertise_id(product.getAdvertises().stream()
                        .map(Advertise::getId)
                        .toList())
                .build();
    }
    public Product toProduct(ProductRequest productRequest, Brand brand, Category category) {
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .promotionRate(productRequest.getPromotionRate())
                .description(productRequest.getDescription())
                .quantity(0L)
                .productImage(FileUtils.save(Path, productRequest.getImage()))
                .status(productRequest.getStatus())
                .brand(brand)
                .category(category)
                .build();
    }
}
