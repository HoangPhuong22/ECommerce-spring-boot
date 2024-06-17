package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.VariationRequest;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.dto.response.VariationResponse;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.VariationService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final CategoryService categoryService;
    private final VariationService variationService;

    @GetMapping("/variation")
    public String listVariation(Model theModel) {
        List<CategoryResponse> categories = categoryService.findCategoryParent();
        List<VariationResponse> variations = variationService.findAll();
        theModel.addAttribute("categories", categories);
        theModel.addAttribute("variations", variations);
        return "admin/product/variation/list";
    }



    @GetMapping("/variation/add")
    public String addSpecs(Model theModel) {
        List<CategoryResponse> categories = categoryService.findCategoryParent();
        theModel.addAttribute("categories", categories);
        theModel.addAttribute("variationRequest", new VariationRequest());
        return "admin/product/variation/add";
    }




    @PostMapping("/variation/add")
    public String addSpecs(@Valid @ModelAttribute("variationRequest") VariationRequest variationRequest,
                           BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            List<CategoryResponse> categories = categoryService.findCategoryParent();
            theModel.addAttribute("categories", categories);
            return "admin/product/variation/add";
        }
        Long id = variationService.save(variationRequest);
        if(id == -1) {
            bindingResult.rejectValue("name", "error.variationRequest", "Tên biến thể đã tồn tại");
            return "admin/product/variation/add";
        }
        return "redirect:/admin/products/variation";
    }



    @GetMapping("/variation/edit/{id}")
    public String editSpecs(@PathVariable Long id, Model theModel) {
        Variation variation = variationService.findVariationById(id);
        theModel.addAttribute("variationRequest", VariationRequest.builder()
                .id(id)
                .name(variation.getName())
                .category(variation.getCategories())
                .build());
        theModel.addAttribute("categories", categoryService.findCategoryParent());
        List<Long> categoryIds = variation.getCategories().stream().map(Category::getId).toList();
        theModel.addAttribute("categoryIds", categoryIds);
        return "admin/product/variation/edit";
    }



    @PostMapping("/variation/edit/{id}")
    public String editSpecs(@PathVariable Long id, @Valid @ModelAttribute("variationRequest") VariationRequest variationRequest,
                            BindingResult bindingResult, Model theModel
    ) {
        if(bindingResult.hasErrors()) {
            List<CategoryResponse> categories = categoryService.findCategoryParent();
            List<Long> categoryIds = variationRequest.getCategory().stream().map(Category::getId).toList();
            theModel.addAttribute("categoryIds", categoryIds);
            theModel.addAttribute("categories", categories);
            return "admin/product/variation/edit";
        }
        Long result = variationService.update(id, variationRequest);
        if(result == -1) {
            bindingResult.rejectValue("name", "error.variationRequest", "Tên biến thể đã tồn tại");
            return "admin/product/variation/edit";
        }
        return "redirect:/admin/products/variation";
    }


    @GetMapping("/variation/delete/{id}")
    public String deleteSpecs(@PathVariable Long id) {
        variationService.delete(id);
        return "redirect:/admin/products/variation";
    }
}
