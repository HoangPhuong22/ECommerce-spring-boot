package vn.zerocoder.Mart.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.service.CategoryService;

@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<String, Category> {

    private final CategoryService categoryService;

    @Override
    public Category convert(String source) {
        if(source.isEmpty()) return null;
        return categoryService.findCategoryById(Long.parseLong(source));
    }
}
