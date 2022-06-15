package com.project.eaproject.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderClientDTO {
    private Long id;
    private String order_id;
    private LocalDate ordered_date;
    private Long userId;
}
