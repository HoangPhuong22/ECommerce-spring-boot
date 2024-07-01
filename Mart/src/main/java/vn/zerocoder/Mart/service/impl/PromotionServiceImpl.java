package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.PromotionRequest;
import vn.zerocoder.Mart.dto.response.PromotionResponse;
import vn.zerocoder.Mart.mapper.PromotionMapper;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.Promotion;
import vn.zerocoder.Mart.repository.ProductRepository;
import vn.zerocoder.Mart.repository.PromotionRepository;
import vn.zerocoder.Mart.service.PromotionService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    private final PromotionMapper promotionMapper;

    @Override
    public Long save(PromotionRequest request) {
        // Lấy danh sách sản phẩm theo id
        List<Long> product_id = request.getProduct_id();
        List<Product> products = productRepository.findAllById(product_id);

        // Tạo thông tin khuyến mãi
        Promotion promotion = promotionMapper.toPromotion(request, products);

        // Kiểm tra ngày bắt đầu và ngày kết thúc
        LocalDateTime startDate = request.getStartDate();
        LocalDateTime endDate = request.getEndDate();
        if (startDate.isAfter(endDate)) {
            return -1L;
        }
        return promotionRepository.save(promotion).getId();
    }

    @Override
    public Long update(PromotionRequest request) {
        // Lấy danh sách sản phẩm theo id
        List<Long> product_id = request.getProduct_id();
        List<Product> products = productRepository.findAllById(product_id);

        // Lấy thông tin khuyến mãi theo id
        Long promotion_id = request.getId();
        Promotion promotion = promotionRepository.findById(promotion_id).orElseThrow();

        // Cập nhật thông tin khuyến mãi
        promotion.setName(request.getName());
        promotion.setDiscount(request.getDiscount());
        promotion.setDescription(request.getDescription());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setProducts(products);

        // Kiểm tra ngày bắt đầu và ngày kết thúc
        LocalDateTime startDate = request.getStartDate();
        LocalDateTime endDate = request.getEndDate();
        if (startDate.isAfter(endDate)) {
            return -1L;
        }

        return promotionRepository.save(promotion).getId();
    }

    @Override
    public Long delete(Long id) {
        promotionRepository.deleteById(id);
        return id;
    }

    @Override
    public PromotionResponse findById(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow();
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public List<PromotionResponse> findAll() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream().map(promotionMapper::toPromotionResponse).toList();
    }
}
