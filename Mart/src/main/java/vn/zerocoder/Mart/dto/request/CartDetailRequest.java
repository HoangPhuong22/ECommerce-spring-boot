package vn.zerocoder.Mart.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailRequest implements Serializable {
    private Long id;
    private Long productDetailId;
    private Long quantity;
}
