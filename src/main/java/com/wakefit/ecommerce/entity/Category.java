package com.wakefit.ecommerce.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 100 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 250, message = "Description cannot exceed 250 characters")
    @Column(name = "description", length = 250)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
