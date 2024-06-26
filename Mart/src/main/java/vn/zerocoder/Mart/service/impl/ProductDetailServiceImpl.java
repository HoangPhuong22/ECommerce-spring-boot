package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
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

    @Value("${file.upload-dir}")
    private String Path;

    @Override
    public Long save(ProductDetailRequest detailRequest) {
        Long product_id = detailRequest.getProduct_id();
        Product product = productRepository.findById(product_id).orElseThrow();

        List<VariationOption> options = optionRepository.findAllById(detailRequest.getOptions());
        StringBuilder sku = new StringBuilder(product.getName());
        for (VariationOption option : options) {
            sku.append("-").append(option.getValue());
        }

        ProductDetail productDetail = ProductDetail.builder()
                .sku(sku.toString())
                .productImage(FileUtils.save(Path, detailRequest.getDetailImage()))
                .price(detailRequest.getPrice())
                .qty(detailRequest.getQuantity())
                .product(product)
                .options(options)
                .build();

        if(productDetailRepository.existsBySku(sku.toString())){
            return -1L;
        }
        return productDetailRepository.save(productDetail).getId();
    }

    @Override
    public Long update(ProductDetailRequest detailRequest) {
        List<VariationOption> options = optionRepository.findAllById(detailRequest.getOptions());
        Product product = productRepository.findById(detailRequest.getProduct_id()).orElseThrow();

        StringBuilder sku = new StringBuilder(product.getName());
        for (VariationOption option : options) {
            sku.append("-").append(option.getValue());
        }
        ProductDetail detail = productDetailRepository.findById(detailRequest.getId()).orElseThrow();
        String oldImage = detail.getProductImage();
        String newImage = FileUtils.save(Path, detailRequest.getDetailImage());
        FileUtils.delete(Path, oldImage);

        detail.setSku(sku.toString());
        detail.setProductImage(newImage);
        detail.setPrice(detailRequest.getPrice());
        detail.setQty(detailRequest.getQuantity());
        detail.setOptions(options);

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
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow();
        return ProductDetailResponse.builder()
                .id(productDetail.getId())
                .sku(productDetail.getSku())
                .image(productDetail.getProductImage())
                .price(productDetail.getPrice())
                .quantity(productDetail.getQty())
                .product_id(productDetail.getProduct().getId())
                .options(productDetail.getOptions().stream().map(VariationOption::getId).toList())
                .build();
    }
}
