package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.AdvertiseRequest;
import vn.zerocoder.Mart.dto.response.AdvertiseResponse;

import java.util.List;

public interface AdvertiseService {
    Long save(AdvertiseRequest advertiseRequest);
    Long update(AdvertiseRequest advertiseRequest);
    Long delete(Long id);

    AdvertiseResponse findById(Long id);
    List<AdvertiseResponse> findAll();
}
