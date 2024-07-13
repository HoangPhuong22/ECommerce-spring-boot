package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ShippingMethodResponse {
    private Long id;
    private String name;
    private Long price;
    private String description;
}
