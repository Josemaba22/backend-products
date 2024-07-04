package com.cristian.crudproducts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristian.crudproducts.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
