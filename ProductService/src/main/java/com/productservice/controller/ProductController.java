package com.productservice.controller;

import com.productservice.dto.request.ProductDto;
import com.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductDto> create(@RequestPart("product") ProductDto productDto,
                                             @RequestPart("files")List<MultipartFile> files) throws Exception {
        productDto = productService.save(productDto, files);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/{id}/images/{imageId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id, @PathVariable Long imageId) {
        ByteArrayOutputStream downloadInputStream = productService.findImage(id,imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(downloadInputStream.toByteArray());
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> get() {
        var products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        var product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto, @PathVariable Long id) {
        productDto = productService.update(productDto, id);
        return ResponseEntity.ok(productDto);
    }
}
