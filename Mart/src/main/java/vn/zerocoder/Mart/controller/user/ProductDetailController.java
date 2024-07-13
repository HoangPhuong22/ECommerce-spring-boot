package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.configuration.CustomUserDetailService;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.model.SpecValue;
import vn.zerocoder.Mart.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {

    private final ProductService productService;
    private final ProductDetailService productDetailService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final VariationService variationService;
    private final VariationOptionService optionService;
    private final CustomUserDetailService userService;
    private final SpecService specService;
    private final SpecValueService specValueService;

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

        model.addAttribute("detail", detail);
        model.addAttribute("product", productResponse);
        model.addAttribute("specValues", specValueService.findAll());

        model.addAttribute("brandService", brandService);
        model.addAttribute("specService", specService);

        model.addAttribute("similar_product", productService.findAllByCategoryIdAndBrandId(category_id, brand_id));
        return "user/product/detail";
    }
}
