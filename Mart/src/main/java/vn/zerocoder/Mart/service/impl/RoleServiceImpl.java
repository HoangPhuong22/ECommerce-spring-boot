package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.model.Role;
import vn.zerocoder.Mart.repository.RoleRepository;
import vn.zerocoder.Mart.service.RoleService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> findAll() {
        return Set.copyOf(roleRepository.findAll());
    }
}
