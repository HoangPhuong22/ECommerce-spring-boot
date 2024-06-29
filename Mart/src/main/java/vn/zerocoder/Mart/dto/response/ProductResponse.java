package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import vn.zerocoder.Mart.enums.ProductStatus;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long price;
    Integer quantity;
    private Integer promotionRate;
    private ProductStatus status;
    private Long brand_id;
    private Long category_id;
    private List<Long> detail_id;
    private List<Long> spec_value_id;
    private List<Long> advertise_id;
}
