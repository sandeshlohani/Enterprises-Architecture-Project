package com.productservice.service.impl;

import com.productservice.domain.Inventory;
import com.productservice.domain.Product;
import com.productservice.repository.ProductRepository;
import com.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Test  implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
//        Product product = new Product();
//        product.setDescription("test");
//        Inventory inventory = new Inventory();
//        inventory.setQuantity(1);
//        product.setInventory(inventory);
//        inventory.setProduct(product);
//        productRepository.save(product);
    }
}
