package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
import vn.zerocoder.Mart.mapper.DetailMapper;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.model.VariationOption;
import vn.zerocoder.Mart.repository.ProductDetailRepository;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.repository.VariationOptionRepository;
import vn.zerocoder.Mart.repository.VariationRepositiory;
import vn.zerocoder.Mart.service.ProductDetailService;
import vn.zerocoder.Mart.utils.FileUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final VariationRepositiory vtaRepository;
    private final ProductRepository productRepository;
    private final VariationOptionRepository optionRepository;

    private final DetailMapper detailMapper;


    @Value("${file.upload-dir}")
    private String Path;

    @Override
    public Long save(ProductDetailRequest detailRequest) {
        // Lấy sản phẩm theo id
        Long product_id = detailRequest.getProduct_id();
        Product product = productRepository.findById(product_id).orElseThrow();

        // Lấy danh sách giá trị biến thể
        List<VariationOption> options = optionRepository.findAllById(detailRequest.getOptions());

        // Tạo sku
        StringBuilder sku = new StringBuilder(product.getName());
        for (VariationOption option : options) {
            sku.append("-").append(option.getValue());
        }

        // Tạo chi tiết sản phẩm
        ProductDetail productDetail = detailMapper.toProductDetail(detailRequest, product, options, sku);

        if(productDetailRepository.existsBySku(sku.toString())){
            return -1L;
        }
        return productDetailRepository.save(productDetail).getId();
    }

    @Override
    public Long update(ProductDetailRequest detailRequest) {

        // Lấy danh sách giá trị biến thể
        List<VariationOption> options = optionRepository.findAllById(detailRequest.getOptions());

        // Lấy sản phẩm
        Product product = productRepository.findById(detailRequest.getProduct_id()).orElseThrow();

        // Tạo sku
        StringBuilder sku = new StringBuilder(product.getName());
        for (VariationOption option : options) {
            sku.append("-").append(option.getValue());
        }
        // Lấy chi tiết sản phẩm
        ProductDetail detail = productDetailRepository.findById(detailRequest.getId()).orElseThrow();

        // Lưu ảnh mới và xóa ảnh cũ
        String oldImage = detail.getProductImage();
        String newImage = FileUtils.save(Path, detailRequest.getDetailImage());
        FileUtils.delete(Path, oldImage);

        // Cập nhật thông tin chi tiết sản phẩm
        detail.setSku(sku.toString());
        detail.setProductImage(newImage);
        detail.setPrice(detailRequest.getPrice());
        detail.setQty(detailRequest.getQuantity());
        detail.setOptions(options);

        // Kiểm tra xem chi tiết sản phẩm có tồn tại không
        if(productDetailRepository.existsBySkuAndIdNot(sku.toString(), detailRequest.getId())){
            return -1L;
        }
        return productDetailRepository.save(detail).getId();
    }

    @Override
    public Long delete(Long id) {
        return 0L;
    }

    @Override
    public ProductDetailResponse findById(Long id) {
        // Lấy chi tiết sản phẩm theo id
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow();
        return detailMapper.toProductDetailResponse(productDetail);
    }

    @Override
    public List<ProductDetailResponse> findAllById(List<Long> id) {
        // Lấy danh sách chi tiết sản phẩm theo id
        return productDetailRepository.findAllById(id).stream().map(detailMapper::toProductDetailResponse).toList();
    }
}
