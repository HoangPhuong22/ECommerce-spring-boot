package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.BrandRequest;
import vn.zerocoder.Mart.dto.response.BrandResponse;
import vn.zerocoder.Mart.mapper.BrandMapper;
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
    private final BrandMapper brandMapper;

    @Override
    public Long save(BrandRequest brandRequest) {
        // Chuyển đổi từ BrandRequest sang Brand
        Brand brand = brandMapper.toBrand(brandRequest);

        // Kiểm tra xem tên brand đã tồn tại chưa
        if(brandRepository.existsByName(brand.getName())) {
            log.error("Brand name already exists");
            return -1L;
        }
        return brandRepository.save(brand).getId();
    }

    @Override
    public Long update(BrandRequest brandRequest) {
        // Kiểm tra xem brand có tồn tại không
        Brand brand = brandRepository.findById(brandRequest.getId()).orElseThrow();
        // Cập nhật thông tin brand
        brand.setName(Normalizer.nameNormalize(brandRequest.getName()));
        brand.setDescription(brandRequest.getDescription());
        // Kiểm tra xem tên brand đã tồn tại chưa
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
        // Kiểm tra xem brand có tồn tại không
        Brand brand = brandRepository.findById(id).orElseThrow();
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public List<BrandResponse> findAll() {
        // Lấy danh sách brand
        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(brandMapper::toBrandResponse).toList();
    }
}
