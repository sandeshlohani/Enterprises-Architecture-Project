package com.productservice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    private Inventory inventory;
    @ManyToOne
    @JoinTable(name = "category_products")
//    @JsonManagedReference
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JsonBackReference
    private List<Photo> photos = new ArrayList<>();

    public void addPhotos(List<Photo> photos){
        photos.forEach(photo -> photo.setProduct(this));
        this.photos = new ArrayList<>();
        this.photos.addAll(photos);
    }

}
