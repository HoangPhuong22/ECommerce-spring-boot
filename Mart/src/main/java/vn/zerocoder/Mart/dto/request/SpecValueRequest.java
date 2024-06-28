package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecValueRequest {

    private Long id;

    @NotBlank(message = "Giá trị thuộc tính không được để trống")
    private String value;

    @NotNull(message = "Vui lòng chọn thuộc tính")
    private Long spec_id;

    @NotNull(message = "Vui lòng chọn sản phẩm")
    private Long product_id;
}
