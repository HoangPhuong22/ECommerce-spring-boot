package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.BrandRequest;
import vn.zerocoder.Mart.dto.response.BrandResponse;
import vn.zerocoder.Mart.model.Brand;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.repository.BrandRepository;
import vn.zerocoder.Mart.service.BrandService;
import vn.zerocoder.Mart.utils.Normalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Long save(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(Normalizer.nameNormalize(brandRequest.getName()))
                .description(brandRequest.getDescription())
                .build();
        if(brandRepository.existsByName(brand.getName())) {
            log.error("Brand name already exists");
            return -1L;
        }
        return brandRepository.save(brand).getId();
    }

    @Override
    public Long update(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .id(brandRequest.getId())
                .name(Normalizer.nameNormalize(brandRequest.getName()))
                .description(brandRequest.getDescription())
                .build();
        if(brandRepository.existsByNameAndIdNot(brand.getName(), brand.getId())) {
            return -1L;
        }
        return brandRepository.save(brand).getId();
    }

    @Override
    public Long delete(Long id) {
        brandRepository.deleteById(id);
        return id;
    }

    @Override
    public BrandResponse findById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow();
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .product_count(productCount(brand))
                .build();
    }

    @Override
    public List<BrandResponse> findAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(brand -> BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .product_count(productCount(brand))
                .build())
                .toList();
    }
    private Integer productCount(Brand brand) {
        return brand.getProducts().stream().map(Product::getQuantity).reduce(0, Integer::sum);
    }
}
