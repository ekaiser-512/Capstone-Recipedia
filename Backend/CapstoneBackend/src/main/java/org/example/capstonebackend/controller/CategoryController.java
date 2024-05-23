package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.ICategoryRepository;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.example.capstonebackend.service.CategoryService;
import org.example.capstonebackend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    IRecipeRepository recipeRepository;

//CREATE
    //create category
    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) throws Exception {
        try {
            Category addedCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(addedCategory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //add recipe to category
    @PostMapping("/categories/{categoryId}/recipes/{recipeId}")
    public ResponseEntity<Category> addRecipeToCategory(@PathVariable Integer categoryId, @PathVariable Integer recipeId) throws Exception {
        try {
            Category category = categoryService.addRecipeToCategory(categoryId, recipeId);
            return ResponseEntity.ok().body(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//READ
    //Get Category by Id
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    //Get All Recipes in Category
//    @GetMapping("/categories/{categoryId}/recipes")
//    public ResponseEntity<List<Recipe>> getRecipesByCategory(@PathVariable Integer categoryId) {
//        try {
//            List<Recipe> recipes = categoryService.getRecipesByCategory(categoryId);
//            return ResponseEntity.ok(recipes);
//        } catch (Exception e) {
//            return ResponseEntity.status(404).body(null);
//        }
//    }

    //Get All Categories
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories;
    }

//UPDATE
    //Update Category
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable int categoryId, @RequestBody Category category) throws Exception {
        try {
            Category updateCategory = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.ok(updateCategory);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//DELETE
    //Delete Category
    @DeleteMapping("/categories/{categoryId}")
    public void deleteRecipe(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }
}
