package vn.zerocoder.Mart.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.configuration.CustomUserDetail;
import vn.zerocoder.Mart.configuration.CustomUserDetailService;
import vn.zerocoder.Mart.dto.request.AddressRequest;
import vn.zerocoder.Mart.dto.request.ProfileRequest;
import vn.zerocoder.Mart.model.Order;
import vn.zerocoder.Mart.model.OrderDetail;
import vn.zerocoder.Mart.model.Profile;
import vn.zerocoder.Mart.model.User;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final CustomUserDetailService userService;

    public CustomUserDetail loadUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String currentUsername = authentication.getName();
            return (CustomUserDetail) userService.loadUserByUsername(currentUsername);
        }
        return null;
    }

    public ProfileRequest loadProfile() {
        CustomUserDetail user = loadUserByUsername();
        if(user != null) {
            Profile profile = user.getUserConfig().getProfile();
            return ProfileRequest.builder()
                    .id(profile.getId())
                    .firstName(profile.getFirstName())
                    .lastName(profile.getLastName())
                    .phoneNumber(profile.getPhoneNumber())
                    .dateOfBirth(profile.getDateOfBirth())
                    .gender(profile.getGender())
                    .build();
        }
        return null;
    }
    public AddressRequest loadAddress() {
        CustomUserDetail user = loadUserByUsername();
        if(user != null) {
            User userConfig = user.getUserConfig();

            return userConfig.getAddresses() == null
                    ? null : AddressRequest.builder()
                            .id(userConfig.getAddresses().getId())
                            .district(userConfig.getAddresses().getDistrict())
                            .province(userConfig.getAddresses().getProvince())
                            .build();
        }
        return null;
    }
    public Boolean productIsOrdered(Long productId) {
        CustomUserDetail user = loadUserByUsername();
        boolean isOrdered = false;
        if(user != null) {
            for(Order order : user.getUserConfig().getOrders()) {
                for(OrderDetail orderDetail : order.getOrderDetails()) {
                    if(orderDetail.getProductDetail().getProduct().getId().equals(productId)) {
                        isOrdered = true;
                        break;
                    }
                }
            }
        }
        return isOrdered;
    }
}
