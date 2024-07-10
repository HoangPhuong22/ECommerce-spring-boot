package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.PaymentType;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    List<PaymentType> findAllByRequiresAccount(Boolean requiresAccount);
}
