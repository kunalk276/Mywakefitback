package com.wakefit.ecommerce.service;

import java.util.List;

import com.wakefit.ecommerce.dto.ProductDTO;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDto);

    ProductDTO getProductById(Long productId);

    ProductDTO updateProduct(ProductDTO productDto);

    void deleteProduct(Long productId);

    List<ProductDTO> findAll();

    List<ProductDTO> searchProductsByName(String name);

    List<ProductDTO> getProductsByCategory(Long categoryId);
    
    ProductDTO addProductToCategory(Long productId, Long categoryId);
}
