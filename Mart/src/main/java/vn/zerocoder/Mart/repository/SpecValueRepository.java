package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.SpecValue;

@Repository
public interface SpecValueRepository extends JpaRepository<SpecValue, Long> {
    @Query("SELECT CASE WHEN COUNT(sv) > 0 THEN TRUE ELSE FALSE END FROM SpecValue sv left join sv.product p left join sv.spec s WHERE p.id = ?1 AND s.id = ?2")
    Boolean existsByProduct_IdAndSpec_Id(Long product_id, Long spec_id);

    @Query("SELECT CASE WHEN COUNT(sv) > 0 THEN TRUE ELSE FALSE END FROM SpecValue sv left join sv.product p left join sv.spec s WHERE p.id = ?1 AND s.id = ?2 AND sv.id != ?3")
    Boolean existsByProduct_IdAndSpec_IdAndIdNot(Long product_id, Long spec_id, Long id);
}
