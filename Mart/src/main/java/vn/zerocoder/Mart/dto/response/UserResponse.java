package vn.zerocoder.Mart.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import vn.zerocoder.Mart.enums.UserStatus;

import java.util.List;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String avatar;
    private String createdAt;
    List<Long> roles;
}
