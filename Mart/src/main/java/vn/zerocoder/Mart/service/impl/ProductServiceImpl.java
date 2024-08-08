package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProductRequest;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.dto.search.ProductSearchDTO;
import vn.zerocoder.Mart.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    @Value("${file.upload-dir}")
    private String Path;

    @Override
    public Long save(ProductRequest productRequest) {

        Long category_id = productRequest.getCategory_id();
        Long brand_id = productRequest.getBrand_id();

        Category category = categoryRepository.findById(category_id).orElseThrow();
        Brand brand = brandRepository.findById(brand_id).orElseThrow();

        Product product = productMapper.toProduct(productRequest, brand, category);

        if(productRepository.existsByName(product.getName())) {
            return -1L;
        }
        return productRepository.save(product).getId();
    }

    @Override
    public Long update(ProductRequest productRequest) {
        // Lấy category_id và brand_id từ productRequest
        Long category_id = productRequest.getCategory_id();
        Long brand_id = productRequest.getBrand_id();

        // Lấy category và brand từ category_id và brand_id
        Category category = categoryRepository.findById(category_id).orElseThrow();
        Brand brand = brandRepository.findById(brand_id).orElseThrow();

        // Lấy product_id từ productRequest
        Long product_id = productRequest.getId();
        Product product = productRepository.findById(product_id).orElseThrow();

        // Lưu ảnh mới và xóa ảnh cũ
        String newImage = FileUtils.save(Path, productRequest.getImage());
        String oldImage = product.getProductImage();
        FileUtils.delete(Path, oldImage);

        // Cập nhật thông tin sản phẩm
        product.setName(productRequest.getName());
        product.setProductImage(newImage);
        product.setPrice(productRequest.getPrice());
        product.setPromotionRate(productRequest.getPromotionRate());
        product.setDescription(productRequest.getDescription());
        product.setProductImage(newImage);
        product.setStatus(productRequest.getStatus());
        product.setBrand(brand);
        product.setCategory(category);

        // Kiểm tra xem product có tồn tại không
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
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> findAllAndSearch(ProductSearchDTO searchDTO) {
        return productRepository.findAllAndSearch(searchDTO).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow();
    }

    @Override
    public List<ProductResponse> findAllByCategoryIdAndBrandId(Long category_id, Long brand_id) {
        return productRepository.findAllByCategoryIdAndBrandId(category_id, brand_id).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}
