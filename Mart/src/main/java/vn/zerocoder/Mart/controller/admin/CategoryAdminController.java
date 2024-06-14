package vn.zerocoder.Mart.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.zerocoder.Mart.service.CategoryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/category/list";
    }
}
