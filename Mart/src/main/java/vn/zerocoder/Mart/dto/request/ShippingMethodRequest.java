package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingMethodRequest {
    private Long id;

    @NotBlank(message = "Tên phương thức không được để trống")
    private String name;

    @NotNull(message = "Giá không được để trống")
    private Long price;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;
}
