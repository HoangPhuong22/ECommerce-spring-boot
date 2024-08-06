package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
