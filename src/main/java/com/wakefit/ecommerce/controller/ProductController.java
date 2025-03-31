package com.wakefit.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wakefit.ecommerce.dto.FeedbackDTO;
import com.wakefit.ecommerce.dto.ProductDTO;
import com.wakefit.ecommerce.dto.PurchaseDTO;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.service.FeedbackService;
import com.wakefit.ecommerce.service.ProductService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FeedbackService feedbackService; 

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDto) {
        ProductDTO savedProduct = productService.addProduct(productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        ProductDTO productDto = productService.getProductById(productId);
        if (productDto == null) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(products);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDto) {
        ProductDTO existingProductDto = productService.getProductById(productId);
        if (existingProductDto == null) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productDto.setProductId(productId);
        ProductDTO updatedProduct = productService.updateProduct(productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        ProductDTO existingProductDto = productService.getProductById(productId);
        if (existingProductDto == null) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String name) {
        List<ProductDTO> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/{productId}/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByProductId(@PathVariable Long productId) {
        List<FeedbackDTO> feedbacks = feedbackService.findFeedbacksByProductId(productId);
        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        return ResponseEntity.ok(feedbacks);
    }
    
    

    @PostMapping("/products/purchase")
    public ResponseEntity<String> purchaseProduct(@RequestBody PurchaseDTO purchaseDTO) {
        try {
            productService.purchaseProduct(purchaseDTO.getProductId(), purchaseDTO.getQuantity());
            return ResponseEntity.ok("Purchase successful! Stock updated.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during purchase.");
        }
    }

    
}
