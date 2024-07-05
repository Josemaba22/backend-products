package com.cristian.crudproducts.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cristian.crudproducts.dto.ProductRequest;
import com.cristian.crudproducts.entity.Category;
import com.cristian.crudproducts.entity.Product;
import com.cristian.crudproducts.exception.ObjectNotFoundException;
import com.cristian.crudproducts.repository.CategoryRepository;
import com.cristian.crudproducts.repository.ProductRepository;
import com.cristian.crudproducts.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));
        productRepository.deleteById(id);
        return product;
    }

    @Override
    public Product updateById(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        product.setCategory(category);
        return productRepository.save(product);
    }

}
