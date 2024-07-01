package vn.zerocoder.Mart.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.dto.response.ProductDetailResponse;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.model.ProductDetail;
import vn.zerocoder.Mart.service.BrandService;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.ProductDetailService;
import vn.zerocoder.Mart.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {

    private final ProductService productService;
    private final ProductDetailService productDetailService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/{id}")
    public String getProductDetail(@PathVariable Long id, Model model) {
        ProductResponse productResponse = productService.findById(id);
        List<Long> detail_id = productResponse.getDetail_id();
        List<ProductDetailResponse> detail = productDetailService.findAllById(detail_id);

        Long category_id = productResponse.getCategory_id();
        Long brand_id = productResponse.getBrand_id();

        model.addAttribute("detail", detail);
        model.addAttribute("product", productResponse);
        model.addAttribute(("brandService"), brandService);
        model.addAttribute("similar_product", productService.findAllByCategoryIdAndBrandId(category_id, brand_id));
        return "user/product-detail";
    }
}
