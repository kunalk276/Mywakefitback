package com.wakefit.ecommerce.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakefit.ecommerce.dto.CategoryDTO;
import com.wakefit.ecommerce.dto.ProductDTO;
import com.wakefit.ecommerce.entity.Category;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.CategoryRepository;
import com.wakefit.ecommerce.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDto) {
        Category category = convertToEntity(categoryDto);
        category = categoryRepository.save(category);
        return convertToDto(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + categoryId));
        return convertToDto(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(CategoryDTO updatedCategoryDto) {
        Long categoryId = updatedCategoryDto.getCategoryId();
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + categoryId));

        existingCategory.setName(updatedCategoryDto.getName());
        existingCategory.setDescription(updatedCategoryDto.getDescription());

        return convertToDto(categoryRepository.save(existingCategory));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllCategoriesWithProducts() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDtoWithProducts)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDto(Category category) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    private CategoryDTO convertToDtoWithProducts(Category category) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());

        List<ProductDTO> productDtos = category.getProducts().stream()
                .map(product -> {
                    ProductDTO productDto = new ProductDTO();
                    productDto.setProductId(product.getProductId());
                    productDto.setName(product.getName());
                    productDto.setDescription(product.getDescription());
                    productDto.setPrice(product.getPrice());
                    productDto.setImages(product.getImages());
                    productDto.setStockQuantity(product.getStockQuantity());
                    return productDto;
                })
                .collect(Collectors.toList());

        categoryDto.setProducts(productDtos);
        return categoryDto;
    }

    private Category convertToEntity(CategoryDTO categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }
}
