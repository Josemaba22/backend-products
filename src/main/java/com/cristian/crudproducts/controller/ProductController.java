package com.cristian.crudproducts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.crudproducts.dto.ProductRequest;
import com.cristian.crudproducts.entity.Product;
import com.cristian.crudproducts.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        if (products.hasContent()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteById(@PathVariable Long id) {
        Product product = productService.deleteById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody @Valid ProductRequest request) {
        Product product = productService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateById(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        Product product = productService.updateById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(Pageable pageable ,@RequestParam String name) {
        Page<Product> products = productService.searchProductsByName(pageable ,name);
        return ResponseEntity.ok(products);
    }

}
