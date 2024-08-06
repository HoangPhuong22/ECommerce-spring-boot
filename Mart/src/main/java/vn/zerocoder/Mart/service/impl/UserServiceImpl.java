package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.configuration.PasswordEncoderConfig;
import vn.zerocoder.Mart.dto.request.UserRequest;
import vn.zerocoder.Mart.dto.response.UserResponse;
import vn.zerocoder.Mart.enums.UserStatus;
import vn.zerocoder.Mart.mapper.UserMapper;
import vn.zerocoder.Mart.model.Cart;
import vn.zerocoder.Mart.model.Profile;
import vn.zerocoder.Mart.model.Role;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.RoleRepository;
import vn.zerocoder.Mart.repository.UserRepository;
import vn.zerocoder.Mart.service.UserService;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final AuthUtils authUtils;
    private final UserMapper userMapper;

    @Override
    public Long save(UserRequest userRequest) {

        Profile profile = Profile.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .avatar("default/avatar.webp")
                .build();
        Cart cart = Cart.builder().build();
        Role role = roleRepository.findById(3L).orElseThrow();
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoderConfig.passwordEncoder().encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .profile(profile)
                .cart(cart)
                .status(UserStatus.ACTIVE)
                .roles(List.of(role))
                .build();

        cart.setUser(user);
        profile.setUser(user);

        if(userRepository.existsByUsername(user.getUsername())) return -1L;
        if(userRepository.existsByEmail(user.getEmail())) return -2L;
        return userRepository.save(user).getId();
    }

    @Override
    public Long update(UserRequest userRequest) {
        User user = userRepository.findById(userRequest.getId()).orElseThrow();
        List<Role> roles = roleRepository.findAllById(userRequest.getRoles());
        user.setRoles(roles);
        user.setStatus(userRequest.getStatus());
        return userRepository.save(user).getId();
    }

    @Override
    public Long updatePassword(String password, String confirmPassword, Long id) {
        User user = userRepository.findById(id).orElseThrow();
        if(!password.equals(confirmPassword)) return -1L;
        user.setPassword(passwordEncoderConfig.passwordEncoder().encode(password));
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponse).orElse(null);
    }

    @Override
    public Long changePassword(String oldPassword, String newPassword, String confirmPassword) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        if(!passwordEncoderConfig.passwordEncoder().matches(oldPassword, user.getPassword())) return -1L;
        if(!newPassword.equals(confirmPassword)) return -2L;
        user.setPassword(passwordEncoderConfig.passwordEncoder().encode(newPassword));
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public List<UserResponse> findTop6ByOrderByCreatedAtDesc() {
        return userRepository.findTop6ByOrderByCreatedAtDesc().stream().map(userMapper::toUserResponse).toList();
    }
}
