package com.productservice.service.impl;

import com.productservice.Exception.CategoryNotFoundException;
import com.productservice.domain.Category;
import com.productservice.dto.request.CategoryDto;
import com.productservice.repository.CategoryRepository;
import com.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category parentCategory = null;
        if(categoryDto.getParentId()!=null) {
            parentCategory = categoryRepository.findById(categoryDto.getParentId()).orElseThrow(() -> new CategoryNotFoundException("Parent Category Not Found"));
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        if (parentCategory !=null) {
            category.setParent(parentCategory);
        }
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAll() {
        var categories = categoryRepository.findAll();
        Type listType = new TypeToken<List<CategoryDto>>() {}.getType();

        return modelMapper.map(categories, listType);
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format("Category Not Found ", id)));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, Long id) {
        Category parentCategory = null;
        if(categoryDto.getParentId()!=null) {
            parentCategory = categoryRepository.findById(categoryDto.getParentId()).orElseThrow(() -> new CategoryNotFoundException("Parent Category Not Found"));
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        if (parentCategory !=null) {
            category.setParent(parentCategory);
        }
        category.setId(id);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
