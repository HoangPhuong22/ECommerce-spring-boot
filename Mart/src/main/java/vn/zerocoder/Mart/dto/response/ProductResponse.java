package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import vn.zerocoder.Mart.enums.ProductStatus;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private ProductStatus status;
    private Long brand_id;
    private Long category_id;
}
