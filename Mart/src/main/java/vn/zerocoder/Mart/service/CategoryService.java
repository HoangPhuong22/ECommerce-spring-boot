package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAll();
}
