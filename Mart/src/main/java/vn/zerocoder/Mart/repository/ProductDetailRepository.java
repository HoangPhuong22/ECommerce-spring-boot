package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.model.VariationOption;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Boolean existsBySku(String sku);
    Boolean existsBySkuAndIdNot(String sku, Long id);
}
