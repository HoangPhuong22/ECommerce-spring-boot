package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.ProductDetailRequest;
import vn.zerocoder.Mart.service.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/details")
public class ProductDetailAdminController {
    private final ProductDetailService productDetailService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final VariationService variationService;
    private final VariationOptionService optionService;

    @GetMapping("/add")
    public String addProductDetail(@RequestParam("product_id") Long product_id, Model theModel) {

        addAttributesToModel(product_id, theModel);
        ProductDetailRequest detailRequest = new ProductDetailRequest();
        detailRequest.setProduct_id(product_id);
        theModel.addAttribute("detail", detailRequest);
        return "admin/product_detail/add";
    }
    @PostMapping("/add")
    public String addProductDetail(@Valid @ModelAttribute("detail") ProductDetailRequest detailRequest ,
                                    BindingResult bindingResult,
                                    @RequestParam("product_id") Long product_id, Model theModel
    ){

        log.info("Detail: {}", detailRequest.getOptions());
        if(bindingResult.hasErrors()){
            addAttributesToModel(product_id, theModel);
            return "admin/product_detail/add";
        }
        productDetailService.save(detailRequest);
        return "redirect:/admin/products/view/" + detailRequest.getProduct_id();
    }

    private void addAttributesToModel(Long product_id, Model theModel) {
        Long category_id = productService.findById(product_id).getCategory_id();
        Long ctg_parent_id = categoryService.findById(category_id).getParent_id();
        List<Long> variation_id = categoryService.findById(ctg_parent_id).getVariations_id();

        theModel.addAttribute("variations", variationService.findAllById(variation_id));
        theModel.addAttribute("optionService", optionService);
        theModel.addAttribute("categoryService", categoryService);
        theModel.addAttribute("brandService", brandService);
        theModel.addAttribute("product", productService.findById(product_id));
    }
}
