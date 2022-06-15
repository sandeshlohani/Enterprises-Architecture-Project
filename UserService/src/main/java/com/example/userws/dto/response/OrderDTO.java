package com.example.userws.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {
    private Long id;
    private String order_id;
    private LocalDate ordered_date;
    private Long userId;
}
