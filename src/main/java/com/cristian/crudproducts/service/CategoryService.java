package com.cristian.crudproducts.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cristian.crudproducts.dto.CategoryRequest;
import com.cristian.crudproducts.entity.Category;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findById(Long id);
    Category save(CategoryRequest request);
    Category deleteById(Long id);
    Category updateById(Long id, CategoryRequest request);
}
