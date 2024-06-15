package vn.zerocoder.Mart.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.CategoryRequest;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.repository.CategoryRepository;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.utils.NameNormalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Long save(CategoryRequest categoryRequest) {
        log.info("Save category");
        Category category = Category.builder()
                .name(NameNormalizer.normalize(categoryRequest.getName()))
                .parent(categoryRequest.getParent())
                .build();
        if(categoryRepository.existsByName(category.getName())) {
            log.error("Category name already exists");
            return -1L;
        }
        return categoryRepository.save(category).getId();
    }

    @Override
    public Long update(Long id, CategoryRequest categoryRequest) {
        log.info("Update category");
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(NameNormalizer.normalize(categoryRequest.getName()));
        category.setParent(categoryRequest.getParent());
        return categoryRepository.save(category).getId();
    }

    @Override
    public Long delete(Long id) {
        log.info("Delete category");
        categoryRepository.deleteById(id);
        return id;
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public CategoryResponse findById(Long id) {
        log.info("Find category by id: {}", id);
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null) return null;
        return CategoryResponse.builder()
                .name(category.getName())
                .parent(category.getParent() != null ?
                        CategoryResponse.builder()
                                .id(category.getParent().getId())
                                .name(category.getParent().getName())
                                .build() : null)
                .children(category.getChildren().stream().map(child -> CategoryResponse.builder()
                        .name(child.getName())
                        .build()).toList())
                .build();
    }

    @Override
    public List<CategoryResponse> findAllCategoryChildren() {
        log.info("Find all category children");
        List<Category> categories = categoryRepository.findAllCategoryChildren();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findAll() {
        log.info("Find all categories");
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryParent() {
        log.info("Find all category parent");
        List<Category> categories = categoryRepository.findCategoryParent();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren().stream().map(child -> CategoryResponse.builder()
                        .name(child.getName())
                        .build()).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryTechnology() {
        log.info("Find all category technology");
        List<Category> categories = categoryRepository.findCategoryTechnology();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren().stream().map(child -> CategoryResponse.builder()
                        .name(child.getName())
                        .build()).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryHousehold() {
        log.info("Find all category household");
        List<Category> categories = categoryRepository.findCategoryHousehold();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren().stream().map(child -> CategoryResponse.builder()
                        .name(child.getName())
                        .build()).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryClothes() {
        log.info("Find all category clothes");
        List<Category> categories = categoryRepository.findCategoryClothes();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren().stream().map(child -> CategoryResponse.builder()
                        .name(child.getName())
                        .build()).toList())
                .build()).toList();
    }
}
