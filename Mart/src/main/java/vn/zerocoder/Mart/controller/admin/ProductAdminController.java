package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.ProductRequest;
import vn.zerocoder.Mart.dto.response.ProductResponse;
import vn.zerocoder.Mart.service.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final ProductDetailService detailService;
    private final VariationService variationService;
    private final VariationOptionService optionService;

    @GetMapping
    public String listProduct(Model theModel) {
        theModel.addAttribute("products", productService.findAll());
        return "admin/product/list";
    }

    @GetMapping("/add")
    public String addProduct(Model theModel) {
        theModel.addAttribute("product", new ProductRequest());
        theModel.addAttribute("brands", brandService.findAll());
        theModel.addAttribute("categories", categoryService.findAllCategoryChildren());
        return "admin/product/add";
    }

    @PostMapping("/add")
    public String saveProduct(@Valid @ModelAttribute("product") ProductRequest productRequest,
                              BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            theModel.addAttribute("brands", brandService.findAll());
            theModel.addAttribute("categories", categoryService.findAllCategoryChildren());
            return "admin/product/add";
        }
        productService.save(productRequest);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model theModel) {
        ProductResponse productResponse = productService.findById(id);
        ProductRequest product = ProductRequest.builder()
                .id(productResponse.getId())
                .name(productResponse.getName())
                .description(productResponse.getDescription())
                .price(productResponse.getPrice())
                .promotionRate(productResponse.getPromotionRate())
                .status(productResponse.getStatus())
                .productImage(productResponse.getImage())
                .brand_id(productResponse.getBrand_id())
                .category_id(productResponse.getCategory_id())
                .build();
        theModel.addAttribute("product", product);
        theModel.addAttribute("brands", brandService.findAll());
        theModel.addAttribute("categories", categoryService.findAllCategoryChildren());
        return "admin/product/edit";
    }
}
