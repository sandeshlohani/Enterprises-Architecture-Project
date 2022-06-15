package com.project.eaproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter@Setter@NoArgsConstructor
public class OrderCourierStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrderStatus status;
    private LocalDate statusDate;

    public OrderCourierStatus(OrderStatus status, LocalDate statusDate) {
        this.status = status;
        this.statusDate = statusDate;
    }

}
