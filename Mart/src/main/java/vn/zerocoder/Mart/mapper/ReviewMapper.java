package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.response.ReviewResponse;
import vn.zerocoder.Mart.model.Review;

@Component
public class ReviewMapper {
    public ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .user_id(review.getUser().getId())
                .avatar(review.getUser().getProfile().getAvatar())
                .username(review.getUser().getUsername())
                .build();
    }
}
