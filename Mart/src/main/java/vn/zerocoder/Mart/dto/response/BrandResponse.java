package vn.zerocoder.Mart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
    private Long product_count;
    List<Long> products_id;
}
