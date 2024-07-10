package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeRequest implements Serializable {
    Long id;
    @NotBlank(message = "Tên phương thức thanh toán không được để trống")
    String name;
    String description;
    @NotNull(message = "Yêu cầu tài khoản không được để trống")
    Boolean requiresAccount;
}