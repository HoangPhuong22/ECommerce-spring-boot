package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.ProductRequest;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.dto.search.ProductSearchDTO;

import java.util.List;

public interface ProductService {

    Long save(ProductRequest productRequest);
    Long update(ProductRequest productRequest);
    Long delete(Long id);

    List<ProductResponse> findAll();
    List<ProductResponse> findAllAndSearch(ProductSearchDTO searchDTO);
    ProductResponse findById(Long id);
    List<ProductResponse> findAllByCategoryIdAndBrandId(Long category_id, Long brand_id);
}
