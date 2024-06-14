package vn.zerocoder.Mart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CategoryRequest {
    private Long id;
    private String name;
    private Long parentId;
}
