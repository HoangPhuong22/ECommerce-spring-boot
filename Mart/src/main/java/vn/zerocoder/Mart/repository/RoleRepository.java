package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
