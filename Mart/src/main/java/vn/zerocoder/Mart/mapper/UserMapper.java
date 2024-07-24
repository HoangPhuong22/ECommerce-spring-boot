package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.response.UserResponse;
import vn.zerocoder.Mart.model.Role;
import vn.zerocoder.Mart.model.User;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roleId(user.getRoles().stream().map(Role::getId).toList())
                .build();
    }
}
