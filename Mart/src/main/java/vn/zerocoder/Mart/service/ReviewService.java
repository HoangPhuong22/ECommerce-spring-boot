package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.ReviewRequest;
import vn.zerocoder.Mart.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    Long save(ReviewRequest reviewRequest);

    List<ReviewResponse> findAll();
    List<ReviewResponse> findAllByProductId(Long productId);
    Boolean checkUserReview(Long productId);
}
