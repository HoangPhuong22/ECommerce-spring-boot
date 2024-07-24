package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ShippingAddressRequest;
import vn.zerocoder.Mart.model.ShippingAddress;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.ShippingAddressRepository;
import vn.zerocoder.Mart.repository.UserRepository;
import vn.zerocoder.Mart.service.ShippingAddressService;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;
    private final AuthUtils authUtils;
    private final UserRepository userRepository;

    @Override
    public Long save(ShippingAddressRequest shippingAddressRequest) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        ShippingAddress address = ShippingAddress.builder()
                .recipientName(shippingAddressRequest.getRecipientName())
                .province(shippingAddressRequest.getProvince())
                .district(shippingAddressRequest.getDistrict())
                .street(shippingAddressRequest.getStreet())
                .apartment(shippingAddressRequest.getApartment())
                .phoneNumber(shippingAddressRequest.getPhoneNumber())
                .isActive(true)
                .user(user)
                .build();
        return shippingAddressRepository.save(address).getId();
    }

    @Override
    public Long update(ShippingAddressRequest shippingAddressRequest) {
        // Lấy shipping address từ cơ sở dữ liệu
        Long id = shippingAddressRequest.getId();
        ShippingAddress address = shippingAddressRepository.findById(id).orElseThrow();

        // Cập nhật thông tin shipping address
        address.setRecipientName(shippingAddressRequest.getRecipientName());
        address.setProvince(shippingAddressRequest.getProvince());
        address.setDistrict(shippingAddressRequest.getDistrict());
        address.setStreet(shippingAddressRequest.getStreet());
        address.setApartment(shippingAddressRequest.getApartment());
        address.setPhoneNumber(shippingAddressRequest.getPhoneNumber());

        // Lưu thông tin shipping address
        return shippingAddressRepository.save(address).getId();
    }

    @Override
    public void delete(Long id) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        List<Long> lst = user.getShippingAddresses().stream().map(ShippingAddress::getId).toList();
        if (!lst.contains(id)) {
            throw new AccessDeniedException("Bạn không có quyền xóa địa chỉ này");
        }
        ShippingAddress address = shippingAddressRepository.findById(id).orElseThrow();
        address.setIsActive(false);
        shippingAddressRepository.save(address);
    }
}
