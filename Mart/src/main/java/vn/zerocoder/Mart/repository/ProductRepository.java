package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String productName);
    Boolean existsByNameAndIdNot(String productName, Long id);
}
