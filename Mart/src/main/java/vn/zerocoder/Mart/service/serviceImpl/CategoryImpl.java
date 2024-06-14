package vn.zerocoder.Mart.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.repository.CategoryRepository;
import vn.zerocoder.Mart.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryResponse.builder()
                .name(category.getName())
                .build()).toList();
    }
}
