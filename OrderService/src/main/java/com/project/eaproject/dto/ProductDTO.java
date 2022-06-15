package com.project.eaproject.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private InventoryDTO inventory;
    private CategoryDto category;
    private Long categoryId;
//    private List<Photo> photos;

}