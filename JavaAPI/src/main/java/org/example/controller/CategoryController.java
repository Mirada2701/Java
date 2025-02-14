package org.example.controller;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.entities.CategoryEntity;
import org.example.repository.ICategoryRepository;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getList();
    }
//    @GetMapping("/{id}")
//    public CategoryEntity getOneCategory(int id) {
//        return categoryService.getOne(id);
//    }

    @PostMapping
    public CategoryEntity createCategory(@RequestBody CategoryCreateDTO dto) {
        return categoryService.create(dto);
    }

    @PutMapping
    public CategoryEntity editCategory(CategoryEditDTO dto) {
        return categoryService.edit(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id){
        categoryService.delete(id);
    }


}
