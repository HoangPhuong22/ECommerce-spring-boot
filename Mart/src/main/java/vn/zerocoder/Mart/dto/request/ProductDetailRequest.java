package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import vn.zerocoder.Mart.validator.FileNotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest {

    private Long id;

    private String sku;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Size(min = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
    private Integer quantity;

    private String image;

    @FileNotEmpty(message = "Ảnh sản phẩm không được để trống")
    private MultipartFile detailImage;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Range(min = 1000, max = 1000000000, message = "Giá sản phẩm phải từ 1.000 vnđ đến 1.000.000.000 vnđ (1 nghìn đồng đến 1 tỷ đồng)")
    private Long price;

    @NotNull(message = "Sản phẩm không được để trống")
    private Long product_id;

}
