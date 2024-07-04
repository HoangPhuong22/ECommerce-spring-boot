package vn.zerocoder.Mart.controller.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.service.CategoryService;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final CategoryService categoryService;

    @ModelAttribute("techCategories")
    public List<CategoryResponse> technologyCategories() {
        return categoryService.findCategoryTechnology();
    }
}