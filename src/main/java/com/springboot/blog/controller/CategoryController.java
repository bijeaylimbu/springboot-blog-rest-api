package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.RestResponseDto;
import com.springboot.blog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.utils.AppConstants.CATEGORY_ADDED_SUCCESS_MESSAGE;
import static com.springboot.blog.utils.AppConstants.CATEGORY_DELETE_SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Build Add Category REST API
    @PostMapping
    public ResponseEntity addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return RestResponseDto.successModel(CATEGORY_ADDED_SUCCESS_MESSAGE);
    }

    // Build Get Category REST API
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
         CategoryDto categoryDto = categoryService.getCategory(categoryId);
         return ResponseEntity.ok(categoryDto);
    }

    // Build Get All Categories REST API
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Build Update Category REST API
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    // Build Delete Category REST API

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return RestResponseDto.successModel(CATEGORY_DELETE_SUCCESS_MESSAGE);
    }
}
