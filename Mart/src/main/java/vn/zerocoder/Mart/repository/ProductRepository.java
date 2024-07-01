package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String productName);
    Boolean existsByNameAndIdNot(String productName, Long id);

    List<Product> findAllByCategoryIdAndBrandId(Long category_id, Long brand_id);
}
