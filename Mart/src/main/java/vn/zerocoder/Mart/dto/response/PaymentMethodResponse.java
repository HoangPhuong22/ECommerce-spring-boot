package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class PaymentMethodResponse {
    private Long id;
    private String provider;
    private String account_number;
    private String account_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expired;
    private Long user_id;
    private Long payment_type_id;
}
