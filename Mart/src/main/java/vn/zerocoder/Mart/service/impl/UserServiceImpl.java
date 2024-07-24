package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.configuration.PasswordEncoderConfig;
import vn.zerocoder.Mart.dto.request.UserRequest;
import vn.zerocoder.Mart.dto.response.UserResponse;
import vn.zerocoder.Mart.enums.UserStatus;
import vn.zerocoder.Mart.mapper.UserMapper;
import vn.zerocoder.Mart.model.Cart;
import vn.zerocoder.Mart.model.Profile;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.RoleRepository;
import vn.zerocoder.Mart.repository.UserRepository;
import vn.zerocoder.Mart.service.UserService;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.util.List;

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

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoderConfig.passwordEncoder().encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .profile(profile)
                .cart(cart)
                .status(UserStatus.ACTIVE)
                .build();

        cart.setUser(user);
        profile.setUser(user);

        if(userRepository.existsByUsername(user.getUsername())) return -1L;
        if(userRepository.existsByEmail(user.getEmail())) return -2L;
        return userRepository.save(user).getId();
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
}
