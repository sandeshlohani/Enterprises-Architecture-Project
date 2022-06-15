package com.project.eaproject.dto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Long parentId;
    private CategoryDto parent;
    private List<ProductDTO> products;
}