package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.ProductRequest;
import vn.zerocoder.Mart.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    Long save(ProductRequest productRequest);
    Long update(ProductRequest productRequest);
    Long delete(Long id);

    List<ProductResponse> findAll();
    ProductResponse findById(Long id);
}
