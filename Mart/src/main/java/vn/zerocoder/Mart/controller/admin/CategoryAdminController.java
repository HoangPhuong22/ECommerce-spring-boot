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

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryAdminController {

    private final CategoryService categoryService;


    @GetMapping
    public String listCategory(Model model) {
        model.addAttribute("categories", categoryService.findAllCategoryChildren());
        return "admin/category/list";
    }



    @GetMapping("/parent")
    public String listCategoryParent(Model model) {
        model.addAttribute("categories", categoryService.findCategoryParent());
        return "admin/category/list-parent";
    }


    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("categories", categoryService.findCategoryParent());
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
        model.addAttribute("categories", categoryService.findCategoryParent());
        model.addAttribute("categoryRequest", CategoryRequest.builder()
                .id(id)
                .name(categoryResponse.getName())
                .parent_id(categoryResponse.getParent_id())
                .build());
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
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/category";
    }
}
