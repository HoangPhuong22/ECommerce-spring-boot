package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdvertiseResponse {
    private Long id;
    private String bannerImage;
    private Long product_id;
}
