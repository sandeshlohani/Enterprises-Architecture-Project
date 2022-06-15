package com.project.eaproject.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter@Setter@ToString
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long product_id;
    private Integer quantity;

    public OrderProducts(Long product_id, Integer quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public OrderProducts() {
    }
}
