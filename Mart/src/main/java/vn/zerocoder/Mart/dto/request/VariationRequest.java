package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.zerocoder.Mart.model.Category;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariationRequest {

    private Long id;

    @NotNull(message = "Tên biến thể không được để trống")
    @NotBlank(message = "Tên biến thể không được để trống")
    @Size(min = 2, max = 255, message = "Tên biến thể phải từ 6 đến 255 ký tự")
    private String name;

    @NotEmpty(message = "Danh mục không được rỗng")
    private List<Long> categories_id;
}
