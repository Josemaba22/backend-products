package com.cristian.crudproducts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cristian.crudproducts.dto.ProductRequest;
import com.cristian.crudproducts.entity.Product;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long id);
    Product save(ProductRequest request);
    Product deleteById(Long id);
    Product updateById(Long id, ProductRequest request);
    List<Product> searchProductsByName(String name);
}
