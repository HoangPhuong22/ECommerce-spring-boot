package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import vn.zerocoder.Mart.validator.FileNotEmpty;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest {

    private Long id;

    private String sku;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
    private Long quantity;

    private String image;

    @FileNotEmpty(message = "Ảnh sản phẩm không được để trống")
    private MultipartFile detailImage;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Range(min = 1000, max = 1000000000, message = "Giá sản phẩm phải từ 1.000 vnđ đến 1.000.000.000 vnđ (1 nghìn đồng đến 1 tỷ đồng)")
    private Long price;

    @NotNull(message = "Sản phẩm không được để trống")
    private Long product_id;

    @NotEmpty(message = "Vui lòng chọn ít nhất một biến thể")
    private List<Long> options;

}
