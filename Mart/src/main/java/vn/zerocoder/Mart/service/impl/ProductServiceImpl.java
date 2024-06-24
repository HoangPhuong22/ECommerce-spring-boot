package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProductRequest;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Long save(ProductRequest productRequest) {
        return 0L;
    }

    @Override
    public Long update(Long id, ProductRequest productRequest) {
        return 0L;
    }

    @Override
    public Long delete(Long id) {
        return 0L;
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .image(product.getProductImage())
                        .status(product.getStatus())
                        .brand_id(product.getBrand().getId())
                        .category_id(product.getCategory().getId())
                        .build())
                .toList();
    }

    @Override
    public ProductResponse findById(Long id) {
        return null;
    }
}
