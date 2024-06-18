package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VariationOptionResponse {
    private Long id;
    private String value;
    private Long variation_id;

    @Override
    public String toString() {
        return value;
    }
}
