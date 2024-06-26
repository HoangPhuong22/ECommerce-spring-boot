package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    private Long id;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(min = 6, max = 255, message = "Tên danh mục phải từ 6 đến 255 ký tự")
    private String name;

    @NotNull(message = "Danh mục cha không được để trống")
    private Long parent_id;

    @NotEmpty(message = "Chọn biến thể cho danh mục")
    private List<Long> variations_id;
}
