package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariationOptionRequest {

    private Long id;

    @NotBlank(message = "Tên biến thể không được để trống")
    private String value;

    @NotNull(message = "Biến thể không được để trống")
    private Long variation_id;
}
