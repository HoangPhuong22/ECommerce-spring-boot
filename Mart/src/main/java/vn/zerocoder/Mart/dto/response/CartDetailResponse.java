package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartDetailResponse {
    private Long id;
    private Long productDetailId;
    private Long quantity;
}
