package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import vn.zerocoder.Mart.enums.ProductStatus;
import vn.zerocoder.Mart.validator.FileNotEmpty;

import java.util.List;

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

    @FileNotEmpty(message = "Ảnh sản phẩm không được để trống")
    private MultipartFile image;

    private String productImage;
    
    @NotNull(message = "Giá sản phẩm không được để trống")
    private Long price;

    @NotNull(message = "Phần trăm (%) khuyến mãi không được để trống")
    private Integer promotionRate;

    @NotNull(message = "Vui lòng chọn trạng thái sản phẩm")
    private ProductStatus status;

    @NotNull(message = "Thương hiệu sản phẩm không được để trống")
    private Long brand_id;

    @NotNull(message = "Danh mục sản phẩm không được để trống")
    private Long category_id;

    private List<Long> detail_id;
}
