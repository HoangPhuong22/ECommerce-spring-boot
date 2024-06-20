package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {

    private Long id;

    @NotBlank(message = "Tên thương hiệu không được để trống")
    @Size(min = 2, max = 255, message = "Tên thương hiệu phải từ 2 đến 255 ký tự")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;
}
