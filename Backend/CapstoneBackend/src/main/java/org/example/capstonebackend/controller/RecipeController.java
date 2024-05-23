package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.example.capstonebackend.service.IngredientService;
import org.example.capstonebackend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    IRecipeRepository recipeRepository;


//CREATE
    //create recipe
    @PostMapping("/recipes")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) throws Exception {
        try {
            Recipe addedRecipe = recipeService.addRecipe(recipe);
            return ResponseEntity.ok(addedRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//READ
    //get recipe by name
    @GetMapping("/recipes/name/{name}")
    public ResponseEntity<Recipe> getRecipeByName(@PathVariable String name) {
        try {
            Recipe recipe = recipeService.getRecipeByName(name);
            return ResponseEntity.ok(recipe);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Get All Recipes in Category
    @GetMapping("/categories/{categoryId}/recipes")
    public List<Recipe> getRecipesByCategory(@PathVariable Category category) {
        return recipeService.getRecipesByCategory(category);
    }

    //get all recipes
    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

//UPDATE
    //update recipe
    @PutMapping("/recipes/{recipeId}")
    public ResponseEntity<?> updateRecipe(@PathVariable int recipeId, @RequestBody Recipe recipe) throws Exception {
        try {
            Recipe updateRecipe = recipeService.updateRecipe(recipeId, recipe);
            return ResponseEntity.ok(updateRecipe);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//DELETE
    //delete recipe
@DeleteMapping("recipes/{recipeId}")
public void deleteRecipe(@PathVariable int id) {
    recipeService.deleteRecipe(id);
}

}
