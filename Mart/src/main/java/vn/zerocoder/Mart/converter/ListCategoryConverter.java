package vn.zerocoder.Mart.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.service.CategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListCategoryConverter implements Converter<String, List<Category>> {

    private final CategoryService categoryService;

    @Override
    public List<Category> convert(String source) {
        return Arrays.stream(source.split(","))
                .map(Long::parseLong)
                .map(categoryService::findCategoryById)
                .collect(Collectors.toList());
    }
}