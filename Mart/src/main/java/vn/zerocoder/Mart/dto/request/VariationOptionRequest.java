package vn.zerocoder.Mart.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariationOptionRequest {
    private Long id;
    private String value;
    private Long variation_id;
}
