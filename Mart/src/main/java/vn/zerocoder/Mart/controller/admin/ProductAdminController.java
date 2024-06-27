package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        Long id = productService.save(productRequest);
        if(id == -1) {
            theModel.addAttribute("brands", brandService.findAll());
            theModel.addAttribute("categories", categoryService.findAllCategoryChildren());
            bindingResult.rejectValue("name", "error.product", "Tên sản phẩm đã tồn tại");
            return "admin/product/add";
        }
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

    @PostMapping("/edit")
    public String updateProduct(@Valid @ModelAttribute("product") ProductRequest productRequest,
                                BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            theModel.addAttribute("brands", brandService.findAll());
            theModel.addAttribute("categories", categoryService.findAllCategoryChildren());
            return "admin/product/edit";
        }
        Long id = productService.update(productRequest);
        if(id == -1) {
            theModel.addAttribute("brands", brandService.findAll());
            theModel.addAttribute("categories", categoryService.findAllCategoryChildren());
            bindingResult.rejectValue("name", "error.product", "Tên sản phẩm đã tồn tại");
            return "admin/product/edit";
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable Long id, Model theModel) {
        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);
        theModel.addAttribute("detailService", detailService);
        theModel.addAttribute("product", productService.findById(id));
        return "admin/product/view";
    }
}
