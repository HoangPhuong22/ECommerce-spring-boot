package vn.zerocoder.Mart.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.AdvertiseRequest;
import vn.zerocoder.Mart.dto.response.AdvertiseResponse;
import vn.zerocoder.Mart.model.Advertise;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.utils.FileUtils;

@Component
public class AdvertiseMapper {

    @Value("${file.upload-dir}")
    private String Path;

    public Advertise toEntity(AdvertiseRequest request, Product product) {
        return Advertise.builder()
                .bannerImage(FileUtils.save(Path, request.getImageFile()))
                .product(product)
                .build();
    }

    public AdvertiseResponse toResponse(Advertise advertise) {
        return AdvertiseResponse.builder()
                .id(advertise.getId())
                .bannerImage(advertise.getBannerImage())
                .product_id(advertise.getProduct().getId())
                .build();
    }
}
