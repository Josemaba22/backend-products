package com.cristian.crudproducts.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cristian.crudproducts.dto.CategoryRequest;
import com.cristian.crudproducts.entity.Category;
import com.cristian.crudproducts.exception.ObjectNotFoundException;
import com.cristian.crudproducts.repository.CategoryRepository;
import com.cristian.crudproducts.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public Category deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));
        categoryRepository.deleteById(id);
        return category;

    }
}
