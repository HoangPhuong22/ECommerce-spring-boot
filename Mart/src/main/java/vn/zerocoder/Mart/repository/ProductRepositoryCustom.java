package vn.zerocoder.Mart.repository;

import vn.zerocoder.Mart.dto.search.ProductSearchDTO;
import vn.zerocoder.Mart.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllAndSearch(ProductSearchDTO searchDTO);
}
