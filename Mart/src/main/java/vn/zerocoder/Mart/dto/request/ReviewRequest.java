package vn.zerocoder.Mart.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long id;
    private Integer rating;
    private String comment;
    private Long user_id;
    private Long product_id;
}
