package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String sku;
    private String image;
    private Long price;
    private Integer quantity;
    private Long product_id;
    private List<Long> options;
}
