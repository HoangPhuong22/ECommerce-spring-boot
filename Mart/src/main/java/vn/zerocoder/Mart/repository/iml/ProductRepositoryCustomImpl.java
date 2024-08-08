package vn.zerocoder.Mart.repository.iml;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.dto.search.ProductSearchDTO;
import vn.zerocoder.Mart.model.*;
import vn.zerocoder.Mart.repository.ProductRepositoryCustom;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAllAndSearch(ProductSearchDTO searchDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        Join<Product, Brand> brand = product.join("brand", JoinType.LEFT);
        Join<Product, Category> category = product.join("category", JoinType.LEFT);
        Join<Category, Category> category_parent = category.join("parent", JoinType.LEFT);
        Join<Product, SpecValue> specValue = product.join("specValues", JoinType.LEFT);
        Join<Product, ProductDetail> productDetail = product.join("productDetails", JoinType.LEFT);
        Join<ProductDetail, VariationOption> option = productDetail.join("options", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();

        for(Field field : searchDTO.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(searchDTO);
                if(value != null) {
                    if(field.getName().equals("keyword")) {
                        predicates.add(cb.like(product.get("name"), "%" + value + "%"));
                    } else if(field.getName().equals("categoryId")) {
                        Predicate categoryPredicate = cb.equal(category.get("id"), value);
                        Predicate parentCategoryPredicate = cb.equal(category_parent.get("id"), value);
                        predicates.add(cb.or(categoryPredicate, parentCategoryPredicate));
                    } else if(field.getName().equals("category")) {
                        predicates.add(cb.in(category.get("name")).value(value));
                    } else if(field.getName().equals("brand")) {
                        Predicate brandName = cb.in(brand.get("name")).value(value);
                        Predicate brandDesc = cb.in(brand.get("description")).value(value);
                        predicates.add(cb.or(brandName, brandDesc));
                    } else if(field.getName().equals("memory")) {
                       Predicate spec = cb.in(specValue.get("value")).value(value);
                       Predicate options = cb.in(option.get("value")).value(value);
                       predicates.add(cb.or(spec, options));
                    } else if(field.getName().equals("ram")) {
                        Predicate spec = cb.in(specValue.get("value")).value(value);
                        Predicate options = cb.in(option.get("value")).value(value);
                        predicates.add(cb.or(spec, options));
                    } else if(field.getName().equals("price_min")) {
                        predicates.add(cb.greaterThanOrEqualTo(product.get("price"), (Long) value));
                    } else if(field.getName().equals("price_max")) {
                        predicates.add(cb.lessThanOrEqualTo(product.get("price"), (Long) value));
                    } else if(field.getName().equals("orderBy")) {
                        if(value.equals("price_asc")) {
                            query.orderBy(cb.asc(product.get("price")));
                        } else if(value.equals("price_desc")) {
                            query.orderBy(cb.desc(product.get("price")));
                        }
                        else if(value.equals("quantity_desc")) {
                            query.orderBy(cb.desc(product.get("quantity")));
                        }
                        else if(value.equals("quantity_asc")) {
                            query.orderBy(cb.asc(product.get("quantity")));
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        query.select(product).where(predicates.toArray(new Predicate[0])).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }
}
