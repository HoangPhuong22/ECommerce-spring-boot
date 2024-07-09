package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.AddressRequest;
import vn.zerocoder.Mart.model.Address;
import vn.zerocoder.Mart.model.User;
import vn.zerocoder.Mart.repository.AddressRepository;
import vn.zerocoder.Mart.service.AddressService;
import vn.zerocoder.Mart.utils.AuthUtils;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AuthUtils authUtils;
    @Override
    public Long save(AddressRequest addressRequest) {
        User user = authUtils.loadUserByUsername().getUserConfig();
        Address address = Address.builder()
                .province(addressRequest.getProvince())
                .district(addressRequest.getDistrict())
                .user(user)
                .build();
        return addressRepository.save(address).getId();
    }

    @Override
    public Long update(AddressRequest addressRequest) {
        Long id = addressRequest.getId();
        Address address = addressRepository.findById(id).orElseThrow();
        address.setProvince(addressRequest.getProvince());
        address.setDistrict(addressRequest.getDistrict());
        return addressRepository.save(address).getId();
    }
}
