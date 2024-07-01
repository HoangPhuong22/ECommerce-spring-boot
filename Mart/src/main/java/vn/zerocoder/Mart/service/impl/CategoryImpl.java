package vn.zerocoder.Mart.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.zerocoder.Mart.dto.request.CategoryRequest;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.mapper.CategoryMapper;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.Variation;
import vn.zerocoder.Mart.repository.CategoryRepository;
import vn.zerocoder.Mart.repository.VariationRepositiory;
import vn.zerocoder.Mart.service.CategoryService;
import vn.zerocoder.Mart.utils.Normalizer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final VariationRepositiory variationRepositiory;
    private final CategoryMapper categoryMapper;

    @Override
    public Long save(CategoryRequest categoryRequest) {
        log.info("Save category");

        // Lấy category cha
        Category parent = categoryRepository.findById(categoryRequest.getParent_id()).orElse(null);

        // Lấy danh sách variation
        List<Variation> variations = variationRepositiory.findAllById(categoryRequest.getVariations_id());

        // Chuẩn hóa tên category
        String name = Normalizer.nameNormalize(categoryRequest.getName());

        // Tạo category mới
        Category category = Category.builder()
                .name(name)
                .parent(parent)
                .variations(variations)
                .build();

        // Kiểm tra xem category có tồn tại không
        if(categoryRepository.existsByName(category.getName())) {
            log.error("Category name already exists");
            return -1L;

        }
        return categoryRepository.save(category).getId();
    }

    @Override
    public Long update(Long id, CategoryRequest categoryRequest) {
        log.info("Update category");
        // Lấy danh sách variation
        List<Long> variations_id = categoryRequest.getVariations_id();
        List<Variation> variations = variationRepositiory.findAllById(variations_id);

        // Lấy category cha
        Long parent_id = categoryRequest.getParent_id();
        Category parent = categoryRepository.findById(parent_id).orElse(null);

        // Chuẩn hóa tên category
        String category_name = Normalizer.nameNormalize(categoryRequest.getName());

        // Lấy category cần update
        Category category = categoryRepository.findById(id).orElseThrow();

        // Set thông tin mới
        category.setName(category_name);
        category.setParent(parent);
        category.setVariations(variations);

        // Kiểm tra xem category có tồn tại không
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

        return categoryMapper.getCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> findAllCategoryChildren() {
        log.info("Find all category children");
        // Lấy danh sách category con
        List<Category> categories = categoryRepository.findAllCategoryChildren();
        return categoryMapper.getCategoryResponses(categories);
    }

    @Override
    public List<CategoryResponse> findAll() {
        log.info("Find all categories");
        // Lấy danh sách category
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::getCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryParent() {
        log.info("Find all category parent");
        // Lấy danh sách category cha
        List<Category> categories = categoryRepository.findCategoryParent();
        return categories.stream().map(categoryMapper::getCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> findCategoryTechnology() {
        log.info("Find all category technology");
        // Lấy danh sách category công nghệ
        List<Category> categories = categoryRepository.findCategoryTechnology();
        return categoryMapper.getCategoryResponses(categories);
    }

    @Override
    public List<CategoryResponse> findCategoryHousehold() {
        log.info("Find all category household");
        // Lấy danh sách category gia dụng
        List<Category> categories = categoryRepository.findCategoryHousehold();
        return categoryMapper.getCategoryResponses(categories);
    }

    @Override
    public List<CategoryResponse> findCategoryClothes() {
        log.info("Find all category clothes");
        // Lấy danh sách category quần áo
        List<Category> categories = categoryRepository.findCategoryClothes();
        return categoryMapper.getCategoryResponses(categories);
    }
}
