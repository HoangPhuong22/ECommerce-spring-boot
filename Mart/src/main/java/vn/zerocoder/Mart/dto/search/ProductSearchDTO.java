package vn.zerocoder.Mart.dto.search;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO {
    private String keyword;
    private Long categoryId;
    private Long price_min;
    private Long price_max;
    private List<String> category;
    private List<String> brand;
    private List<String> memory;
    private List<String> ram;
    private String orderBy;
}
