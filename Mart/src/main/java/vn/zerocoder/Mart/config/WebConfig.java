package vn.zerocoder.Mart.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.zerocoder.Mart.converter.CategoryConverter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CategoryConverter categoryConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(categoryConverter);
    }
}
