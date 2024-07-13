package vn.zerocoder.Mart.mapper;

import org.springframework.stereotype.Controller;
import vn.zerocoder.Mart.dto.response.CategoryResponse;
import vn.zerocoder.Mart.model.Category;
import vn.zerocoder.Mart.model.Product;
import vn.zerocoder.Mart.model.Variation;

import java.util.List;

@Controller
public class CategoryMapper {


    public CategoryResponse getCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent() != null ? category.getParent().getId() : null)
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .product_count(productCount(category))
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .products_id(category.getProducts().stream().map(Product::getId).toList())
                .build();
    }

    public List<CategoryResponse> getCategoryResponses(List<Category> categories) {
        return categories.stream().map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent_id(category.getParent().getId())
                .product_count(productCount(category))
                .children_id(category.getChildren().stream().map(Category::getId).toList())
                .variations_id(category.getVariations().stream().map(Variation::getId).toList())
                .products_id(category.getProducts().stream().map(Product::getId).toList())
                .build()).toList();
    }

    public Long productCount(Category category) {
        return category.getProducts().stream().map(Product::getQuantity).reduce(0L, Long::sum);
    }
}
