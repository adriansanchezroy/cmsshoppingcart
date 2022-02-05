package com.asr.cmsshoppingcart.models;

import com.asr.cmsshoppingcart.models.data.Page;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PageRepository extends JpaRepository<Page, Integer>{

    Page findBySlug(String slug);

    Page findBySlugAndId(String slug, int id);

}
