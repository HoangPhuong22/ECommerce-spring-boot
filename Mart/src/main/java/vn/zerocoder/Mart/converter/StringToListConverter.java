package vn.zerocoder.Mart.converter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringToListConverter implements Converter<String, List<Long>> {

    @Override
    public List<Long> convert(String source) {
        if (source.isEmpty()) return new ArrayList<>();
        return Arrays.stream(source.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}

