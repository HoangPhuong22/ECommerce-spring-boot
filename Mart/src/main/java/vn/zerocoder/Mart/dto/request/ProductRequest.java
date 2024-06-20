package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.zerocoder.Mart.enums.ProductStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 6, max = 255, message = "Tên sản phẩm phải từ 6 đến 255 ký tự")
    private String name;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    private String description;

    @NotNull(message = "Trạng thái sản phẩm không được để trống")
    private String image;

    private ProductStatus status;

    @NotNull(message = "Thương hiệu sản phẩm không được để trống")
    private Long brand_id;

    @NotNull(message = "Danh mục sản phẩm không được để trống")
    private Long category_id;
}
