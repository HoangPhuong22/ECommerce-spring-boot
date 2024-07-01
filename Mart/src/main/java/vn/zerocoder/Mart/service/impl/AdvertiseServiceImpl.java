package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.zerocoder.Mart.dto.request.AdvertiseRequest;
import vn.zerocoder.Mart.dto.response.AdvertiseResponse;
import vn.zerocoder.Mart.mapper.AdvertiseMapper;
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

    private final AdvertiseMapper advertiseMapper;

    @Value("${file.upload-dir}")
    private String Path;

    @Override
    public Long save(AdvertiseRequest advertiseRequest) {
        // Tìm sản phẩm theo id
        Long product_id = advertiseRequest.getProduct_id();
        Product product = productRepository.findById(product_id).orElseThrow();

        // Tạo mới quảng cáo
        Advertise advertise = advertiseMapper.toEntity(advertiseRequest, product);

        return advertiseRepository.save(advertise).getId();
    }

    @Override
    public Long update(AdvertiseRequest advertiseRequest) {
        // Tìm sản phẩm theo id
        Long product_id = advertiseRequest.getProduct_id();
        Product product = productRepository.findById(product_id).orElseThrow();

        // Tìm quảng cáo theo id
        Long advertise_id = advertiseRequest.getId();
        Advertise advertise = advertiseRepository.findById(advertise_id).orElseThrow();

        // Tạo ảnh mới và xóa ảnh cũ
        String newImage = FileUtils.save(Path, advertiseRequest.getImageFile());
        String oldImage = advertise.getBannerImage();
        FileUtils.delete(Path, oldImage);

        // Cập nhật thông tin quảng cáo
        advertise.setBannerImage(newImage);
        advertise.setProduct(product);

        return advertiseRepository.save(advertise).getId();
    }

    @Override
    public Long delete(Long id) {
        // Xóa ảnh và quảng cáo
        Advertise advertise = advertiseRepository.findById(id).orElseThrow();
        String image = advertise.getBannerImage();
        FileUtils.delete(Path, image);

        advertiseRepository.delete(advertise);
        return id;
    }

    @Override
    public AdvertiseResponse findById(Long id) {
        Advertise advertise = advertiseRepository.findById(id).orElseThrow();
        return advertiseMapper.toResponse(advertise);
    }

    @Override
    public List<AdvertiseResponse> findAll() {
        List<Advertise> advertises = advertiseRepository.findAll();
        return advertises.stream().map(advertiseMapper::toResponse).toList();
    }
}
