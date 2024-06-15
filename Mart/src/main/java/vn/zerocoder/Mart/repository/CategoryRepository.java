package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    @Query("SELECT c FROM Category c WHERE c.parent IS NOT NULL")
    List<Category> findAllCategoryChildren();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findCategoryParent();

    @Query("SELECT c FROM Category c WHERE c.parent.id = 1")
    List<Category> findCategoryTechnology();

    @Query("SELECT c FROM Category c WHERE c.parent.id = 2")
    List<Category> findCategoryHousehold();

    @Query("SELECT c FROM Category c WHERE c.parent.id = 3")
    List<Category> findCategoryClothes();

    Boolean existsByName(String name);
}
