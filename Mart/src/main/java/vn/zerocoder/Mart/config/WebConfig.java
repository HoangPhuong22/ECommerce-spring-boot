package vn.zerocoder.Mart.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.zerocoder.Mart.converter.CategoryConverter;
import vn.zerocoder.Mart.converter.ListCategoryConverter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CategoryConverter categoryConverter;
    private final ListCategoryConverter listCategoryConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(categoryConverter);
        registry.addConverter(listCategoryConverter);
    }
}
