package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProductRequest;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.model.*;
import vn.zerocoder.Mart.repository.BrandRepository;
import vn.zerocoder.Mart.repository.CategoryRepository;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.service.ProductService;
import vn.zerocoder.Mart.utils.FileUtils;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;


    @Value("${file.upload-dir}")
    private String Path;

    @Override
    public Long save(ProductRequest productRequest) {

        Long category_id = productRequest.getCategory_id();
        Long brand_id = productRequest.getBrand_id();

        Category category = categoryRepository.findById(category_id).orElseThrow();
        Brand brand = brandRepository.findById(brand_id).orElseThrow();

        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .promotionRate(productRequest.getPromotionRate())
                .description(productRequest.getDescription())
                .quantity(0)
                .productImage(FileUtils.save(Path, productRequest.getImage()))
                .status(productRequest.getStatus())
                .brand(brand)
                .category(category)
                .build();

        if(productRepository.existsByName(product.getName())) {
            return -1L;
        }
        return productRepository.save(product).getId();
    }

    @Override
    public Long update(ProductRequest productRequest) {
        Long category_id = productRequest.getCategory_id();
        Long brand_id = productRequest.getBrand_id();

        Category category = categoryRepository.findById(category_id).orElseThrow();
        Brand brand = brandRepository.findById(brand_id).orElseThrow();

        Long product_id = productRequest.getId();
        Product product = productRepository.findById(product_id).orElseThrow();

        String newImage = FileUtils.save(Path, productRequest.getImage());
        String oldImage = product.getProductImage();

        FileUtils.delete(Path, oldImage);

        product.setName(productRequest.getName());
        product.setProductImage(newImage);
        product.setPrice(productRequest.getPrice());
        product.setPromotionRate(productRequest.getPromotionRate());
        product.setDescription(productRequest.getDescription());
        product.setProductImage(newImage);
        product.setStatus(productRequest.getStatus());
        product.setBrand(brand);
        product.setCategory(category);

        if(productRepository.existsByNameAndIdNot(product.getName(), product_id)) {
            return -1L;
        }

        return productRepository.save(product).getId();
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
                        .price(product.getPrice())
                        .promotionRate(product.getPromotionRate())
                        .description(product.getDescription())
                        .image(product.getProductImage())
                        .status(product.getStatus())
                        .quantity(product.getQuantity())
                        .brand_id(product.getBrand().getId())
                        .category_id(product.getCategory().getId())
                        .detail_id(product.getProductDetails().stream()
                                .map(BaseEntity::getId)
                                .toList())
                        .spec_value_id(product.getSpecValues().stream()
                                .map(SpecValue::getId)
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .promotionRate(product.getPromotionRate())
                        .description(product.getDescription())
                        .image(product.getProductImage())
                        .quantity(product.getQuantity())
                        .status(product.getStatus())
                        .brand_id(product.getBrand().getId())
                        .category_id(product.getCategory().getId())
                        .detail_id(product.getProductDetails().stream()
                                .map(BaseEntity::getId)
                                .toList())
                        .spec_value_id(product.getSpecValues().stream()
                                .map(SpecValue::getId)
                                .toList())
                        .build())
                .orElseThrow();
    }
}
