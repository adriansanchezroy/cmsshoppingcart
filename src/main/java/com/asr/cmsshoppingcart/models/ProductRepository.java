package com.asr.cmsshoppingcart.models;

import com.asr.cmsshoppingcart.models.data.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Integer> {

    Product findBySlug(String slug);
    
}
