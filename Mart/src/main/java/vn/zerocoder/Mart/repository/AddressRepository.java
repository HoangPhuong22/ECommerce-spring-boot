package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.zerocoder.Mart.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
