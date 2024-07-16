package com.cristian.crudproducts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cristian.crudproducts.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    Page<Product> findByNameContainingIgnoreCase(Pageable pageable,String name);

}
