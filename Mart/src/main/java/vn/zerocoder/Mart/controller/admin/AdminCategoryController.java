package vn.zerocoder.Mart.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.zerocoder.Mart.dto.request.CategoryRequest;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.VariationService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final VariationService variationService;

    @GetMapping
    public String listCategory(Model model) {
        model.addAttribute("categories_parent", categoryService.findCategoryParent());
        model.addAttribute("categories_child", categoryService.findAllCategoryChildren());
        return "admin/category/list";
    }


    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("categories", categoryService.findCategoryParent());
        model.addAttribute("variations", variationService.findAll());
        model.addAttribute("categoryRequest", new CategoryRequest());
        return "admin/category/add";
    }


    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("categoryRequest") CategoryRequest categoryRequest,
                              BindingResult bindingResult,
                              Model model
    ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findCategoryParent());
            model.addAttribute("variations", variationService.findAll());
            return "admin/category/add";
        }
        Long id = categoryService.save(categoryRequest);
        if(id == -1) {
            model.addAttribute("categories", categoryService.findCategoryParent());
            bindingResult.rejectValue("name", "error.categoryRequest", "Tên danh mục đã tồn tại");
            return "admin/category/add";
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {

        CategoryResponse categoryResponse = categoryService.findById(id);
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id(id)
                .name(categoryResponse.getName())
                .parent_id(categoryResponse.getParent_id() != null ? categoryResponse.getParent_id() : null)
                .variations_id(categoryResponse.getVariations_id())
                .build();

        model.addAttribute("variations", variationService.findAll());
        model.addAttribute("categories", categoryService.findCategoryParent());
        model.addAttribute("categoryRequest", categoryRequest);

        return "admin/category/edit";
    }
    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id,
                               @Valid @ModelAttribute("categoryRequest") CategoryRequest categoryRequest,
                               BindingResult bindingResult,
                               Model model
    ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findCategoryParent());
            model.addAttribute("variations", variationService.findAll());
            return "admin/category/edit";
        }
        Long idCategory = categoryService.update(id, categoryRequest);
        if(idCategory == -1) {
            model.addAttribute("categories", categoryService.findCategoryParent());
            bindingResult.rejectValue("name", "error.categoryRequest", "Tên danh mục đã tồn tại");
            return "admin/category/edit";
        }
        return "redirect:/admin/category";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        Long delete_id = categoryService.delete(id);
        return "redirect:/admin/category";
    }
}
