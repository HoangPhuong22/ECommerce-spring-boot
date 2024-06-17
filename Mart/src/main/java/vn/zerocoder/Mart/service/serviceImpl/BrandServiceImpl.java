package vn.zerocoder.Mart.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.BrandRequest;
import vn.zerocoder.Mart.dto.response.BrandResponse;
import vn.zerocoder.Mart.model.Brand;
import vn.zerocoder.Mart.repository.BrandRepository;
import vn.zerocoder.Mart.service.BrandService;
import vn.zerocoder.utils.NameNormalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Long save(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(NameNormalizer.normalize(brandRequest.getName()))
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
                .name(NameNormalizer.normalize(brandRequest.getName()))
                .description(brandRequest.getDescription())
                .build();
        if(brandRepository.existsByName(brand.getName())) {
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
                .build();
    }

    @Override
    public List<BrandResponse> findAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(brand -> BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .build())
                .toList();
    }
}
