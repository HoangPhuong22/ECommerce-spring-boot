package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vn.zerocoder.Mart.validator.PhoneValid;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressRequest {
    private Long id;

    @NotBlank(message = "Tên người nhận không được để trống")
    private String recipientName;

    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    private String province;

    @NotBlank(message = "Quận/Huyện không được để trống")
    private String district;

    @NotBlank(message = "Đường không được để trống")
    private String street;

    @NotBlank(message = "Số nhà không được để trống")
    private String apartment;

    @NotBlank(message = "Số điện thoại không được để trống")
    @PhoneValid
    private String phoneNumber;

    private Long userId;
}
