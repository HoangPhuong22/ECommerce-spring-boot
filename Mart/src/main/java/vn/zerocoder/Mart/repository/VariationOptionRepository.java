package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.VariationOption;

@Repository
public interface VariationOptionRepository extends JpaRepository<VariationOption, Long> {
    Boolean existsByValue(String value);
    Boolean existsByValueAndIdNot(String value, Long id);
}
