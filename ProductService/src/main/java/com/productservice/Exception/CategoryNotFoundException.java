package com.productservice.Exception;

public class CategoryNotFoundException extends RuntimeException{
   public CategoryNotFoundException(String message) {
        super(message);
    }
}
