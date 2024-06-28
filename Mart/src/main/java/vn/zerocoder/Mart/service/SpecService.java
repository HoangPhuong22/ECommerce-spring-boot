package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.SpecRequest;
import vn.zerocoder.Mart.dto.response.SpecResponse;

import java.util.List;

public interface SpecService {
    Long save(SpecRequest specRequest);
    Long update(SpecRequest specRequest);
    Long delete(Long id);

    SpecResponse findById(Long id);
    List<SpecResponse> findAll();
}
