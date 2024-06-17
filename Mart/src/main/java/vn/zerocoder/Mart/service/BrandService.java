package vn.zerocoder.Mart.service;


import vn.zerocoder.Mart.dto.request.BrandRequest;
import vn.zerocoder.Mart.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {

    Long save(BrandRequest brandRequest);
    Long update(BrandRequest brandRequest);
    Long delete(Long id);

    BrandResponse findById(Long id);

    List<BrandResponse> findAll();
}
