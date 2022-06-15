package com.project.eaproject.model;

import com.project.eaproject.dto.ProductDTO;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@Entity(name = "ProductOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,updatable = false)
    private String order_id;
    private LocalDate ordered_date;
    private Long userId;
    @OneToMany(cascade = CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderProducts> orderProducts = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private OrderCourierStatus orderCourierStatus;

    public Order(String order_id, LocalDate ordered_date,Long userId) {
        this.order_id = order_id;
        this.ordered_date = ordered_date;
        this.userId=userId;
    }

    public Order() {
    }
}
