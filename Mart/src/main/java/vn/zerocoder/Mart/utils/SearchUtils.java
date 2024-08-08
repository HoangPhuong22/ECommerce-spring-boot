package vn.zerocoder.Mart.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import vn.zerocoder.Mart.dto.search.ProductSearchDTO;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class SearchUtils {
    public static ProductSearchDTO buildSearchDTO(MultiValueMap<String, String> params) {
        ProductSearchDTO searchDTO = new ProductSearchDTO();
        Set<String> fieldNames = getFieldName(searchDTO);
        for(String key: params.keySet()) {
            if(fieldNames.contains(key)) {
                try {
                    Field field = searchDTO.getClass().getDeclaredField(key);
                    field.setAccessible(true);
                    if(field.getType().equals(List.class)) {
                        field.set(searchDTO, filterList(params.get(key)));
                    } else if(field.getType().equals(Long.class)) {
                        if(NumberUtils.isNumber(params.getFirst(key))) {
                            field.set(searchDTO, Long.parseLong(params.getFirst(key)));
                        }
                    } else {
                        field.set(searchDTO, params.getFirst(key));
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        return searchDTO;
    }
    public static Set<String> getFieldName(ProductSearchDTO searchDTO) {
        Set<String> fieldNames = new HashSet<>();
        for(Field field : searchDTO.getClass().getDeclaredFields()) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }
    public static List<String> filterList(List<String> list) {
        list.removeIf(item -> item == null || item.isEmpty());
        return list.isEmpty() ? null : list;
    }
}
