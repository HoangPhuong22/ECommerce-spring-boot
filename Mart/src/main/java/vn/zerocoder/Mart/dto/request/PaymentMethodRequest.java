package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Tên ngân hàng không được để trống")
    private String provider;

    @NotBlank(message = "Số tài khoản không được để trống")
    private String account_number;

    @NotBlank(message = "Tên tài khoản không được để trống")
    private String account_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Vui lòng chọn ngày hết hạn")
    private LocalDate expired;

    private Long user_id;

    @NotNull(message = "Vui lòng chọn phương thức thanh toán")
    private Long payment_type_id;

}
