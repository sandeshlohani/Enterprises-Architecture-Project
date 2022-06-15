package com.productservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
    @JoinColumn(name = "parent_id")
    private Category parent;
}
