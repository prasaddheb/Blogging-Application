package com.backend.blog.service.impl;

import com.backend.blog.entity.CategoryEntity;
import com.backend.blog.exception.ResourceNotFoundException;
import com.backend.blog.mapper.CategoryMapper;
import com.backend.blog.model.dto.CategoryDto;
import com.backend.blog.repository.CategoryRepository;
import com.backend.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private  final CategoryRepository categoryRepository;

    private  final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(categoryDto);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return categoryMapper.toCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));

        categoryEntity.setCategoryTitle(categoryDto.getCategoryTitle());
        categoryEntity.setCategoryDescription(categoryDto.getCategoryDescription());

        CategoryEntity  updatedCategoryEntity = categoryRepository.save(categoryEntity);

        return categoryMapper.toCategoryDto(updatedCategoryEntity);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));

        categoryRepository.delete(categoryEntity);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));

        return categoryMapper.toCategoryDto(categoryEntity);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        return categoryEntityList.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }
}
