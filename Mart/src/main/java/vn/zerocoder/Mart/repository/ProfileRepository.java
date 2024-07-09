package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.zerocoder.Mart.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
