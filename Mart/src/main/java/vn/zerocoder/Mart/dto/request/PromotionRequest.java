package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "Vui lòng chọn ngày kết thúc")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime endDate;

    @NotEmpty(message = "Vui lòng chọn sản phẩm")
    private List<Long> product_id;
}
