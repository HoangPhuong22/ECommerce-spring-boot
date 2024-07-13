package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.ShippingAddress;
@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
