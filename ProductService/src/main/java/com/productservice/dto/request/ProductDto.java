package com.productservice.dto.request;
import com.productservice.domain.Inventory;
import com.productservice.domain.Photo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private InventoryDto inventory;
    private CategoryDto category;
//    private Long categoryId;

//    private List<Photo> photos;

}
