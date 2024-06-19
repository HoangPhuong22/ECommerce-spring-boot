package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.request.CategoryRequest;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.model.Category;

import java.util.List;

public interface CategoryService {

    Long save(CategoryRequest categoryRequest);
    Long update(Long id, CategoryRequest categoryRequest);
    Long delete(Long id);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAllCategoryChildren();

    List<CategoryResponse> findAll();
    List<CategoryResponse> findCategoryParent();

    List<CategoryResponse> findCategoryTechnology();
    List<CategoryResponse> findCategoryHousehold();
    List<CategoryResponse> findCategoryClothes();
}
