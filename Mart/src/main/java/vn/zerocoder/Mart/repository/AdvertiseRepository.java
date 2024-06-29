package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Advertise;

@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long> {
}
