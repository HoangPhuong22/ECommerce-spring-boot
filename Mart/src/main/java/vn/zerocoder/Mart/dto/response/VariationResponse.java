package vn.zerocoder.Mart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class VariationResponse {
    private Long id;
    private String name;
    private List<Long> options_id;
}
