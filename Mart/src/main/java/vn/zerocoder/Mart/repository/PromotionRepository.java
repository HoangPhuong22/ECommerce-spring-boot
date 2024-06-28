package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
