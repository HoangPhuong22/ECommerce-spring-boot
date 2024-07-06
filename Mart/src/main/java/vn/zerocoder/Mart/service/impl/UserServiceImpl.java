package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.configuration.PasswordEncoderConfig;
import vn.zerocoder.Mart.dto.request.UserRequest;
import vn.zerocoder.Mart.enums.UserStatus;
import vn.zerocoder.Mart.model.Profile;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.RoleRepository;
import vn.zerocoder.Mart.repository.UserRepository;
import vn.zerocoder.Mart.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public Long save(UserRequest userRequest) {

        Profile profile = Profile.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .avatar("default/avatar.webp")
                .build();

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoderConfig.passwordEncoder().encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .profile(profile)
                .status(UserStatus.ACTIVE)
                .build();

        profile.setUser(user);
        if(userRepository.existsByUsername(user.getUsername())) return -1L;
        if(userRepository.existsByEmail(user.getEmail())) return -2L;
        return userRepository.save(user).getId();
    }
}
