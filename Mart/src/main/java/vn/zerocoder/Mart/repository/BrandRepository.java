package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Long id);
}
