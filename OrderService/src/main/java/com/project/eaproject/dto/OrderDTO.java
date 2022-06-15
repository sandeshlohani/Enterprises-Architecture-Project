package com.project.eaproject.dto;

import com.project.eaproject.model.OrderProducts;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String order_id;
    private LocalDate ordered_date;
    private Long userId;
    private List<ProductDTO> productDTOS;
}
