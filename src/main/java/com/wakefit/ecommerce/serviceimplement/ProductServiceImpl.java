package com.wakefit.ecommerce.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wakefit.ecommerce.dto.ProductDTO;
import com.wakefit.ecommerce.entity.Category;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.CategoryRepository;
import com.wakefit.ecommerce.repository.ProductRepository;
import com.wakefit.ecommerce.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDto) {
        Product product = convertToEntity(productDto);

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        }

        product = productRepository.save(product);
        return convertToDto(product);
    }


    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return product != null ? convertToDto(product) : null;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO updatedProductDto) {
        Long productId = updatedProductDto.getProductId();
        Optional<Product> existingProductOpt = productRepository.findById(productId);

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            existingProduct.setName(updatedProductDto.getName());
            existingProduct.setDescription(updatedProductDto.getDescription());
            existingProduct.setPrice(updatedProductDto.getPrice());
            existingProduct.setImages(updatedProductDto.getImages());
            existingProduct.setStockQuantity(updatedProductDto.getStockQuantity());

            if (updatedProductDto.getCategoryId() != null) {
                Optional<Category> categoryOpt = categoryRepository.findById(updatedProductDto.getCategoryId());
                if (categoryOpt.isPresent()) {
                    existingProduct.setCategory(categoryOpt.get());
                } else {
                    existingProduct.setCategory(null);
                }
            }

            return convertToDto(productRepository.save(existingProduct));
        }
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        }
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                                .stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId)
                                .stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO addProductToCategory(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            if (categoryOpt.isPresent()) {
                product.setCategory(categoryOpt.get());
                return convertToDto(productRepository.save(product));
            }
        }
        return null;
    }
//    @Override
//    public void purchaseProduct(Long productId, int quantity) {
//        Product product = productRepository.findById(productId)
//            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId)); //lambda expression
//        
//        if (product.getStockQuantity() >= quantity) {
//            product.setStockQuantity(product.getStockQuantity() - quantity);
//            productRepository.save(product); 
//        } else {
//            throw new IllegalArgumentException("Insufficient stock for product ID: " + productId);
//        }
//    }

    @Override
    public void purchaseProduct(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock available.");
        }

        product.setStockQuantity(product.getStockQuantity() - quantity); 
        productRepository.save(product); 
    }

    
    private ProductDTO convertToDto(Product product) {
        ProductDTO productDto = new ProductDTO();
        productDto.setProductId(product.getProductId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImages(product.getImages());
        productDto.setStockQuantity(product.getStockQuantity());
        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getCategoryId());
            productDto.setCategoryName(product.getCategory().getName());
        }
        return productDto;
    }

    private Product convertToEntity(ProductDTO productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImages(productDto.getImages());
        product.setStockQuantity(productDto.getStockQuantity());
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
            product.setCategory(category);
        }
        return product;
    }
    
    
}
