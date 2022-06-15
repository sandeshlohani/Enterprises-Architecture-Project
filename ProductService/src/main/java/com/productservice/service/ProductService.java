package com.productservice.service;

import com.productservice.domain.Product;
import com.productservice.dto.request.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface ProductService extends CrudService<ProductDto, ProductDto, Long>{
    ProductDto save(ProductDto productDto, List<MultipartFile> files);
    ByteArrayOutputStream findImage(Long propertyId, Long imageId);
}
