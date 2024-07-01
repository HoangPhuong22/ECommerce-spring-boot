package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.dto.request.PromotionRequest;
import vn.zerocoder.Mart.dto.response.PromotionResponse;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.Promotion;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PromotionMapper {
    public PromotionResponse toPromotionResponse(Promotion promotion) {
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

    public Promotion toPromotion(PromotionRequest request, List<Product> products) {
        return Promotion.builder()
                .name(request.getName())
                .discount(request.getDiscount())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .products(products)
                .build();
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
