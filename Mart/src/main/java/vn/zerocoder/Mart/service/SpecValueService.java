package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.SpecValueRequest;
import vn.zerocoder.Mart.dto.response.SpecValueResponse;

import java.util.List;

public interface SpecValueService {
    Long save(SpecValueRequest specValue);
    Long update(SpecValueRequest specValue);
    Long delete(Long id);

    SpecValueResponse findById(Long id);
    List<SpecValueResponse> findAll();
}
