package com.wakefit.ecommerce.service;

import java.util.List;

import com.wakefit.ecommerce.dto.CategoryDTO;

public interface CategoryService {

    CategoryDTO addCategory(CategoryDTO categoryDto);

    CategoryDTO getCategoryById(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDto);

    void deleteCategory(Long categoryId);

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getAllCategoriesWithProducts();
}
