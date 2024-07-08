package vn.zerocoder.Mart.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.configuration.CustomUserDetailService;

@Component
@RequiredArgsConstructor
public class UserDetailUtils {
    private final CustomUserDetailService userService;
    public CustomUserDetail loadUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String currentUsername = authentication.getName();
            return (CustomUserDetail) userService.loadUserByUsername(currentUsername);
        }
        return null;
    }
}
