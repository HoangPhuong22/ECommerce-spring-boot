package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import vn.zerocoder.Mart.validator.FileNotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiseRequest {
    private Long id;

    private String bannerImage;

    @FileNotEmpty(message = "Ảnh sản phẩm không được để trống")
    private MultipartFile imageFile;

    @NotNull(message = "Vui lòng chọn sản phẩm")
    private Long product_id;
}
