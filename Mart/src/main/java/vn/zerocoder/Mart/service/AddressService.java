package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.AddressRequest;

public interface AddressService {
    Long save(AddressRequest addressRequest);
    Long update(AddressRequest addressRequest);
}
