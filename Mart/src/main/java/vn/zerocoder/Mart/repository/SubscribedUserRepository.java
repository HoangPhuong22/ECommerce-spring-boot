package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.SubscribedUser;

@Repository
public interface SubscribedUserRepository extends JpaRepository<SubscribedUser, Long> {
    Boolean existsByEmail(String email);
}
