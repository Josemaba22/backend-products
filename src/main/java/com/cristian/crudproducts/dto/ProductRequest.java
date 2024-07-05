package com.cristian.crudproducts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank
    private String name;
    
    @NotBlank
    private String description;

    @NotNull
    private Double price;
    
    @NotNull
    private Long category_id;

    public ProductRequest( String name, String description, Double price, Long category_id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryID() {
        return category_id;
    }

    public void setCategoryID(Long category_id) {
        this.category_id = category_id;
    }
    
}
