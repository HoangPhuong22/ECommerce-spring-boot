package vn.zerocoder.Mart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest implements Serializable {
    Long id;
    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    String province;
    @NotBlank(message = "Quận/Huyện không được để trống")
    String district;
}