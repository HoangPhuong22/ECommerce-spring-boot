package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.CategoryRequest;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.repository.CategoryRepository;
import vn.zerocoder.Mart.repository.VariationRepositiory;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.service.VariationService;
import vn.zerocoder.Mart.utils.NameNormalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final VariationRepositiory variationRepositiory;
    @Override
    public Long save(CategoryRequest categoryRequest) {
        log.info("Save category");
        Category parent = categoryRepository.findById(categoryRequest.getParent_id()).orElse(null);
        List<Variation> variations = variationRepositiory.findAllById(categoryRequest.getVariations_id());
        String name = NameNormalizer.normalize(categoryRequest.getName());
        Category category = Category.builder()
                .name(name)
                .parent(parent)
                .variations(variations)
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

        List<Long> variations_id = categoryRequest.getVariations_id();
        List<Variation> variations = variationRepositiory.findAllById(variations_id);

        Long parent_id = categoryRequest.getParent_id();
        Category parent = categoryRepository.findById(parent_id).orElse(null);

        String category_name = NameNormalizer.normalize(categoryRequest.getName());

        Category category = categoryRepository.findById(id).orElseThrow();

        category.setName(category_name);
        category.setParent(parent);
        category.setVariations(variations);

        if(categoryRepository.existsByNameAndIdNot(category.getName(), id)) {
            return -1L;
        }
        return categoryRepository.save(category).getId();
    }

    @Override
    public Long delete(Long id) {
        log.info("Delete category");
        categoryRepository.deleteById(id);
        return id;
    }

    @Override
    public CategoryResponse findById(Long id) {
        log.info("Find category by id: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow();

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent() != null ? category.getParent().getId() : null)
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .build();
    }

    @Override
    public List<CategoryResponse> findAllCategoryChildren() {
        log.info("Find all category children");
        List<Category> categories = categoryRepository.findAllCategoryChildren();

        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent().getId())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findAll() {
        log.info("Find all categories");
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent() != null ? category.getParent().getId() : null)
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryParent() {
        log.info("Find all category parent");
        List<Category> categories = categoryRepository.findCategoryParent();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(null)
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryTechnology() {
        log.info("Find all category technology");
        List<Category> categories = categoryRepository.findCategoryTechnology();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent().getId())
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryHousehold() {
        log.info("Find all category household");
        List<Category> categories = categoryRepository.findCategoryHousehold();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent().getId())
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .build()).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryClothes() {
        log.info("Find all category clothes");
        List<Category> categories = categoryRepository.findCategoryClothes();
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent().getId())
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .build()).toList();
    }
}
