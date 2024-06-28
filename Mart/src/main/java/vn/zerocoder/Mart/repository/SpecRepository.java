package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Spec;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Long id);
}
