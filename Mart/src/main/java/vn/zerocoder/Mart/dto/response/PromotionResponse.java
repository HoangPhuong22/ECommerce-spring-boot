package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class PromotionResponse {
    private Long id;
    private String name;
    private String description;
    private Integer discount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
