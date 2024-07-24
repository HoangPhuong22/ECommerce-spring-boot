package vn.zerocoder.Mart.configuration;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.zerocoder.Mart.enums.UserStatus;
import vn.zerocoder.Mart.model.CartDetail;
import vn.zerocoder.Mart.model.Order;
import vn.zerocoder.Mart.model.ShippingAddress;
import vn.zerocoder.Mart.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class CustomUserDetail implements UserDetails {

    private final User userConfig;

    public CustomUserDetail(User user) {
        this.userConfig = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Thêm quyền từ vai trò (role)
        userConfig.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getName()))
            );
        });

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

    public List<Long> getProductIdsFavourite() {
        return userConfig.getFavourites().stream().map(favourite -> favourite.getProduct().getId()).toList();
    }
    public Long getTotalQtyCart() {
        return userConfig.getCart().getCartDetails().stream().mapToLong(CartDetail::getQuantity).sum();
    }
    public Long getTotalPriceCart() {
        return userConfig.getCart().getCartDetails().stream().mapToLong(cartDetail -> cartDetail.getQuantity() * cartDetail.getProductDetail().getPrice()).sum();
    }
    public List<ShippingAddress> getShippingAddresses() {
        return userConfig.getShippingAddresses();
    }
    public List<Order> getOrders() {
        return userConfig.getOrders();
    }
}
