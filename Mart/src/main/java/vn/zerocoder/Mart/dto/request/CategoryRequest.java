package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.zerocoder.Mart.model.Category;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private Long id;
    @NotBlank(message = "Tên danh mục không được để trống")
    @NotNull(message = "Tên danh mục không được để trống")
    @Size(min = 6, max = 255, message = "Tên danh mục phải từ 6 đến 255 ký tự")
    private String name;

    @NotNull(message = "Danh mục cha không được để trống")
    private Category parent;
}
