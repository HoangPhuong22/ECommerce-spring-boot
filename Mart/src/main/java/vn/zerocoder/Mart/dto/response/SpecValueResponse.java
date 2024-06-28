package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class SpecValueResponse {
    private Long id;
    private String value;
    private Long spec_id;
    private Long product_id;
}
