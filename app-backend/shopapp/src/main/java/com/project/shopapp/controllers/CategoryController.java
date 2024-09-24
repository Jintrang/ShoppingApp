package com.project.shopapp.controllers;


import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//
@RestController
@RequestMapping("api/v1/categories")

//@Validated
@RequiredArgsConstructor //tao constructor voi cac thuoc tinh final hoac not null
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    //Nếu tham số truyền vòa là môt đối tượng -> Data transfer object
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorsMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        Category newCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("This is a category" + newCategory);
    }
    //Hiển thị tất cả category
    @GetMapping("") //http://localhost:8080/api/v1/categories@page=1&limit=10
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok("This is all categories: " + allCategories);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update category with id = " + id + ": " + categoryDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Delete category " + id);
    }
}
