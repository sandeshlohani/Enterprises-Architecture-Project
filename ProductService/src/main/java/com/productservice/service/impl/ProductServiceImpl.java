package com.productservice.service.impl;

import com.productservice.Exception.CategoryNotFoundException;
import com.productservice.Exception.ProductNotFoundException;
import com.productservice.Exception.ResourceNotFoundException;
import com.productservice.domain.Category;
import com.productservice.domain.Photo;
import com.productservice.domain.Product;
import com.productservice.dto.request.CategoryDto;
import com.productservice.dto.request.ProductDto;
import com.productservice.repository.CategoryRepository;
import com.productservice.repository.ProductRepository;
import com.productservice.service.ProductService;
import com.productservice.service.S3BucketStorageService;
import com.productservice.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private final S3BucketStorageService s3BucketStorageService;

    @Override
    public ProductDto save(ProductDto productDto) {
        Category category = null;
        Long categoryId = productDto.getCategory() != null ? productDto.getCategory().getId() : null;
        if(categoryId != null) {
           category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(String.format("Category Not Found", categoryId)));
        }
        Product product = modelMapper.map(productDto, Product.class);
        if (category != null) {
            category.getProducts().add(product);
            product.setCategory(category);
        }
        product.getInventory().setProduct(product);
        productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto save(ProductDto productDto, List<MultipartFile> files) {
        Category category = null;
        Long categoryId = productDto.getCategory() != null ? productDto.getCategory().getId() : null;
        if(categoryId != null) {
            category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(String.format("Category Not Found", categoryId)));
        }
        Product product = modelMapper.map(productDto, Product.class);
        if (category != null) {
            category.getProducts().add(product);
            product.setCategory(category);
        }
        product.getInventory().setProduct(product);

        if(files.size() > 0){
            var photoMetas = s3BucketStorageService.uploadFiles(Helper.randomStringWithMixedCase(16),files);
            var photos = photoMetas.stream().map(meta -> new Photo(meta.getUrl(),meta.getKeyName())).toList();
            product.addPhotos(photos);
        }

        productRepository.save(product);

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ByteArrayOutputStream findImage(Long propertyId, Long imageId) {
        Product product = productRepository.findById(propertyId).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id: %s not found",propertyId)));
        Photo photo = product.getPhotos().stream().filter(p -> p.getId().equals(imageId)).findFirst().orElseThrow(() -> new ResourceNotFoundException(String.format("Image with id: %s not found",imageId)));
        return s3BucketStorageService.downloadImage(photo.getKeyName());
    }

    @Override
    public List<ProductDto> findAll() {
        var products = productRepository.findAll();

        products.forEach(item-> {
            if(item.getCategory()!=null)
                modelMapper.map(item.getCategory(), CategoryDto.class);
        });
        Type listType = new TypeToken<List<ProductDto>>() {}.getType();

        return modelMapper.map(products, listType);
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format("Product Not Found ", id)));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, Long id) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(id);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
