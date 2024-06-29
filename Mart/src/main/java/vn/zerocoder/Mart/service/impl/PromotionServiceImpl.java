package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.PromotionRequest;
import vn.zerocoder.Mart.dto.response.PromotionResponse;
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
    @Override
    public Long save(PromotionRequest request) {
        List<Long> product_id = request.getProduct_id();
        List<Product> products = productRepository.findAllById(product_id);
        Promotion promotion = Promotion.builder()
                .name(request.getName())
                .discount(request.getDiscount())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .products(products)
                .build();
        LocalDateTime startDate = request.getStartDate();
        LocalDateTime endDate = request.getEndDate();
        if (startDate.isAfter(endDate)) {
            return -1L;
        }
        return promotionRepository.save(promotion).getId();
    }

    @Override
    public Long update(PromotionRequest request) {

        List<Long> product_id = request.getProduct_id();
        List<Product> products = productRepository.findAllById(product_id);

        Long promotion_id = request.getId();
        Promotion promotion = promotionRepository.findById(promotion_id).orElseThrow();
        promotion.setName(request.getName());
        promotion.setDiscount(request.getDiscount());
        promotion.setDescription(request.getDescription());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setProducts(products);

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
        return PromotionResponse.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .discount(promotion.getDiscount())
                .description(promotion.getDescription())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .product_id(promotion.getProducts().stream().map(Product::getId).toList())
                .status(getPromotionStatus(promotion.getStartDate(), promotion.getEndDate()))
                .build();
    }

    @Override
    public List<PromotionResponse> findAll() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream().map(promotion -> PromotionResponse.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .discount(promotion.getDiscount())
                .description(promotion.getDescription())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .product_id(promotion.getProducts().stream().map(Product::getId).toList())
                .status(getPromotionStatus(promotion.getStartDate(), promotion.getEndDate()))
                .build()).toList();
    }
    private String getPromotionStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isBefore(startDate)) {
            return "Chưa bắt đầu";
        } else if (currentDate.isAfter(endDate)) {
            return "Đã kết thúc";
        } else {
            return "Đang diễn ra";
        }
    }
}
