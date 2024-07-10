package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.CartDetailRequest;

public interface CartDetailService {
    Long save(Long quantity, Long productDetailId, Boolean isAdd);
    Long delete(Long id);
}
