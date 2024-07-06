package vn.zerocoder.Mart.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.zerocoder.Mart.enums.UserStatus;
import vn.zerocoder.Mart.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetail implements UserDetails {

    private final User userConfig;

    public CustomUserDetail(User user) {
        this.userConfig = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userConfig.getRoles().forEach(role -> {
            authorities.add(role::getName);
        });

        userConfig.getGroups().forEach(group ->
                authorities.add(new SimpleGrantedAuthority("GROUP_" + group.getName()))
        );
        userConfig.getRoles().forEach(role ->
                role.getPermissions().forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(permission.getName()))
                )
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return userConfig.getPassword();
    }

    @Override
    public String getUsername() {
        return userConfig.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userConfig.getStatus().equals(UserStatus.KICKED);
    }

    @Override
    public boolean isEnabled() {
        return userConfig.getStatus().toString().equals("ACTIVE");
    }

    public String getAvatarUrl() {
        return userConfig.getProfile().getAvatar();
    }
    public String getFullName() {
        return userConfig.getProfile().getFirstName() + " " + userConfig.getProfile().getLastName();
    }
    public String getEmail() {
        return userConfig.getEmail();
    }
}
