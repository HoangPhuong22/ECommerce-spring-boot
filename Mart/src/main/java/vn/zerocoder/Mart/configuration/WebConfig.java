package vn.zerocoder.Mart.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.zerocoder.Mart.converter.StringToListConverter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final StringToListConverter listConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(listConverter);
    }
}
