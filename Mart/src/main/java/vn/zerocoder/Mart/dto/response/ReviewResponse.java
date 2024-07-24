package vn.zerocoder.Mart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Integer rating;
    private String comment;
    private Long user_id;
    private String avatar;
    private String username;
}
