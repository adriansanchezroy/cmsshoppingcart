package com.asr.cmsshoppingcart.models.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
// Product entity and validation
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    private String slug;
    
    @Size(min = 5, message = "Description must be at least 5 characters long")
    private String description;

    private String image;

    @Pattern(regexp = "[0-9]+([.][0-9]{1,2})?", message = "Expected format: e.g. 5, 5.99, 14.81, etc.") // price required format (i.e. 20 or 20.99)
    private String price;

    @Pattern(regexp = "^[1-9][0-9]*?", message = "Please choose a category") 
    @Column(name= "category_id") // SQL name
    private String categoryId;

    @Column(name = "created_at", updatable = false) // SQL name
    @CreationTimestamp // automatic timestamp creation
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at") // SQL name
    @UpdateTimestamp // automatic timestamp updating
    private LocalDateTime updatedAt;

    
}
