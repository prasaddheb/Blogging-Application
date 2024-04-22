package com.backend.blog.controller;

import com.backend.blog.model.dto.ApiResponse;
import com.backend.blog.model.dto.CategoryDto;
import com.backend.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private  final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return  new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto category = categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted SuccessFully", Boolean.TRUE), HttpStatus.OK);
    }

    @GetMapping
    public  ResponseEntity<List<CategoryDto>> getAllCategories(){
        return  ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping(value = "/{categoryId}")
    public  ResponseEntity<CategoryDto> getUserById(@PathVariable Integer categoryId){
        return  ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
}

