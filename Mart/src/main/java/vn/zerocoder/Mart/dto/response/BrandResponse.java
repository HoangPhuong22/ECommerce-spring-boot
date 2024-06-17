package vn.zerocoder.Mart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
}
