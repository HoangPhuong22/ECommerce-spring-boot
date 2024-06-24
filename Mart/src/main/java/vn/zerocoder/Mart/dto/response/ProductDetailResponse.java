package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String sku;
    private String image;
    private Long price;
    private Long quantity;
}
