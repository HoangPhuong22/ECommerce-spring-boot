package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Boolean existsByAccountNumber(String account_number);
    Boolean existsByAccountNumberAndIdNot(String account_number, Long id);
}
