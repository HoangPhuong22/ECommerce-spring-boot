package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private CategoryResponse parent;
    private List<CategoryResponse> children;
}
