package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.zerocoder.Mart.dto.request.AdvertiseRequest;
import vn.zerocoder.Mart.dto.response.AdvertiseResponse;
import vn.zerocoder.Mart.model.Advertise;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.repository.AdvertiseRepository;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.service.AdvertiseService;
import vn.zerocoder.Mart.utils.FileUtils;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertiseServiceImpl implements AdvertiseService {

    private final AdvertiseRepository advertiseRepository;
    private final ProductRepository productRepository;

    @Value("${file.upload-dir}")
    private String Path;

    @Override
    public Long save(AdvertiseRequest advertiseRequest) {
        Long product_id = advertiseRequest.getProduct_id();
        Product product = productRepository.findById(product_id).orElseThrow();
        Advertise advertise = Advertise.builder()
                .bannerImage(FileUtils.save(Path, advertiseRequest.getImageFile()))
                .product(product)
                .build();
        return advertiseRepository.save(advertise).getId();
    }

    @Override
    public Long update(AdvertiseRequest advertiseRequest) {
        Long product_id = advertiseRequest.getProduct_id();
        Product product = productRepository.findById(product_id).orElseThrow();
        Long advertise_id = advertiseRequest.getId();
        Advertise advertise = advertiseRepository.findById(advertise_id).orElseThrow();
        String newImage = FileUtils.save(Path, advertiseRequest.getImageFile());
        String oldImage = advertise.getBannerImage();
        FileUtils.delete(Path, oldImage);
        advertise.setBannerImage(newImage);
        advertise.setProduct(product);
        return advertiseRepository.save(advertise).getId();
    }

    @Override
    public Long delete(Long id) {
        Advertise advertise = advertiseRepository.findById(id).orElseThrow();
        String image = advertise.getBannerImage();
        FileUtils.delete(Path, image);
        advertiseRepository.delete(advertise);
        return id;
    }

    @Override
    public AdvertiseResponse findById(Long id) {
        Advertise advertise = advertiseRepository.findById(id).orElseThrow();
        return AdvertiseResponse.builder()
                .id(advertise.getId())
                .bannerImage(advertise.getBannerImage())
                .product_id(advertise.getProduct().getId())
                .build();
    }

    @Override
    public List<AdvertiseResponse> findAll() {
        List<Advertise> advertises = advertiseRepository.findAll();
        return advertises.stream().map(advertise -> AdvertiseResponse.builder()
                .id(advertise.getId())
                .bannerImage(advertise.getBannerImage())
                .product_id(advertise.getProduct().getId())
                .build()).toList();
    }
}
