package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.zerocoder.Mart.model.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByProductDetailId(Long productDetailId);
}