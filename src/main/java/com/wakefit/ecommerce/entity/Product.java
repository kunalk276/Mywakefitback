package com.wakefit.ecommerce.entity;


import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "images")
    private String images;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    private Long categoryId;

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        
    }
}
