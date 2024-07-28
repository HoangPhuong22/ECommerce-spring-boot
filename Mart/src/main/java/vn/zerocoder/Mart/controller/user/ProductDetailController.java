package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.configuration.CustomUserDetailService;
import vn.zerocoder.Mart.dto.request.ReviewRequest;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.service.*;
import vn.zerocoder.Mart.utils.AuthUtils;

import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {

    private final ProductService productService;
    private final ProductDetailService productDetailService;
    private final BrandService brandService;
    private final SpecService specService;
    private final SpecValueService specValueService;
    private final AuthUtils authUtils;
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public String getProductDetail(@PathVariable Long id, Model model) {
        // Lấy ra sản phẩm
        ProductResponse productResponse = productService.findById(id);

        // Lấy ra detail_id để tìm chi tiết sản phẩm
        List<Long> detail_id = productResponse.getDetail_id();
        List<ProductDetailResponse> detail = productDetailService.findAllById(detail_id);

        // Lấy ra category_id và brand_id để tìm sản phẩm tương tự
        Long category_id = productResponse.getCategory_id();
        Long brand_id = productResponse.getBrand_id();

        // Kiểm tra xem sản phẩm đã được đặt hàng chưa
        Boolean productIsOrdered = authUtils.productIsOrdered(id);

        model.addAttribute("detail", detail);
        model.addAttribute("product", productResponse);
        model.addAttribute("specValues", specValueService.findAll());

        model.addAttribute("brandService", brandService);
        model.addAttribute("specService", specService);
        model.addAttribute("isOrdered", productIsOrdered);
        model.addAttribute("rating", new DecimalFormat("#.0").format(reviewService.getRating(id)));

        model.addAttribute("review", new ReviewRequest());
        model.addAttribute("reviews", reviewService.findAllByProductId(id));
        model.addAttribute("checkUserReview", reviewService.checkUserReview(id));
        model.addAttribute("similar_product", productService.findAllByCategoryIdAndBrandId(category_id, brand_id));
        return "user/product/detail";
    }
}
