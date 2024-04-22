package com.backend.blog.mapper;

import com.backend.blog.entity.CategoryEntity;
import com.backend.blog.model.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toCategoryDto(CategoryEntity categoryEntity);

    CategoryEntity toCategoryEntity(CategoryDto categoryDto);
}
