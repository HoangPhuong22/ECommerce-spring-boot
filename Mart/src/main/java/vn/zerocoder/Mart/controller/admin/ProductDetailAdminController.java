package vn.zerocoder.Mart.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.service.ProductDetailService;
import vn.zerocoder.Mart.service.ProductService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/details")
public class ProductDetailAdminController {
    private final ProductDetailService productDetailService;
    private final ProductService productService;

    @GetMapping("/add")
    public String addProductDetail(@RequestParam("product_id") Long product_id, Model theModel) {
        ProductDetailRequest detailRequest = new ProductDetailRequest();
        detailRequest.setProduct_id(product_id);
        theModel.addAttribute("detail", detailRequest);
        theModel.addAttribute("products", productService.findById(product_id));
        return "admin/product_detail/add";
    }
}
