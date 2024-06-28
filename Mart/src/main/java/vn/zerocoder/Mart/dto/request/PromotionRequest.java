package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {
    private Long id;

    @NotBlank(message = "Tiêu đề khuyến mãi không được để trống")
    private String name;

    private String description;

    @Min(value = 1, message = "Phần trăm khuyến mãi phải lớn hơn 0")
    private Integer discount;

    @NotNull(message = "Vui lòng chọn ngày bắt đầu")
    private LocalDateTime startDate;

    @NotNull(message = "Vui lòng chọn ngày kết thúc")
    private LocalDateTime endDate;
}
