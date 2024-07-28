package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ReviewRequest;
import vn.zerocoder.Mart.dto.response.ReviewResponse;
import vn.zerocoder.Mart.mapper.ReviewMapper;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.Review;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.repository.ReviewRepository;
import vn.zerocoder.Mart.service.ReviewService;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final AuthUtils authUtils;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public Long save(ReviewRequest reviewRequest) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        Product product = productRepository.findById(reviewRequest.getProduct_id()).orElseThrow();
        Review review = Review.builder()
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .user(user)
                .product(product)
                .build();
        return reviewRepository.save(review).getId();
    }

    @Override
    public Double getRating(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0);
    }

    @Override
    public List<ReviewResponse> findAll() {
        return reviewRepository.findAll().stream().map(reviewMapper::toResponse).toList();
    }

    @Override
    public List<ReviewResponse> findAllByProductId(Long productId) {
        return reviewRepository.findAllByProductId(productId).stream().map(reviewMapper::toResponse).toList();
    }

    @Override
    public Boolean checkUserReview(Long productId) {
        try {
            User user = authUtils.loadUserByUsername().getUserConfig();
            return !reviewRepository.existsByUserIdAndProductId(user.getId(), productId);
        } catch (Exception e) {
            return false;
        }
    }
}
