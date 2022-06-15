package com.productservice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String url;

    private String keyName;

    @ManyToOne
    @JsonBackReference
    private Product product;

    public Photo(String url, String keyName) {
        this.url = url;
        this.keyName = keyName;
    }
}
