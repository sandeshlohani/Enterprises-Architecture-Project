package com.project.eaproject.feigndata;

import com.project.eaproject.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    ProductDTO getProducts(@PathVariable Long id);

}
