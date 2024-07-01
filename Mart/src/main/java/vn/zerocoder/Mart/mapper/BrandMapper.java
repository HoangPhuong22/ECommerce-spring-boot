package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.BrandRequest;
import vn.zerocoder.Mart.dto.response.BrandResponse;
import vn.zerocoder.Mart.model.Brand;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.utils.Normalizer;

@Component
public class BrandMapper {
    public Brand toBrand(BrandRequest brandRequest) {
        return Brand.builder()
                .name(Normalizer.nameNormalize(brandRequest.getName()))
                .description(brandRequest.getDescription())
                .build();
    }
    public BrandResponse toBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .product_count(productCount(brand))
                .build();
    }
    private Integer productCount(Brand brand) {
        return brand.getProducts().stream().map(Product::getQuantity).reduce(0, Integer::sum);
    }
}
