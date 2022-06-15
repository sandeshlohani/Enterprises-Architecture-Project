package com.productservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.productservice.domain.Category;
import com.productservice.domain.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private Long parentId;
    @JsonIgnore
    private CategoryDto parent;
    @JsonIgnore
    private List<ProductDto> products;
}
