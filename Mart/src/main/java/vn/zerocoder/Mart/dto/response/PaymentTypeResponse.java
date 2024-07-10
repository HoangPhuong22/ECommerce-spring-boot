package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentTypeResponse {
    private Long id;
    private String name;
    private String description;
    private Boolean requiresAccount;
}
