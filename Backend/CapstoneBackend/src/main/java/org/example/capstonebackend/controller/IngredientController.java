package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.example.capstonebackend.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    IIngredientRepository ingredientRepository;

//CREATE
    //create ingredient
    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> addIngredient (@RequestBody Ingredient ingredient) throws Exception {
        try {
            Ingredient addedIngredient = ingredientService.addIngredient(ingredient);
            return ResponseEntity.ok(addedIngredient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//READ
    //get ingredient by name
    @GetMapping("/ingredients/ingredientName/{ingredientName}")
    public ResponseEntity<Ingredient> getIngredientByName(@PathVariable String ingredientName) {
        try {
            Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
            return ResponseEntity.ok(ingredient);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //get ingredient by dietary restriction
    @GetMapping("/ingredients/dietaryRestriction/{dietaryRestriction}")
    public ResponseEntity<Ingredient> getIngredientByDiet(@PathVariable String dietaryRestriction) {
        try {
            Ingredient ingredient = ingredientService.getIngredientByDiet(dietaryRestriction);
            return ResponseEntity.ok(ingredient);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //get list of ingredients that are common allergens
    @GetMapping("/ingredients/commonAllergens")
    public List<Ingredient> getIngredientsThatAreCommonAllergens(@RequestBody Boolean commonAllergen) {
        return ingredientService.getIngredientsThatAreCommonAllergens(commonAllergen);
    }

    //todo get ingredients in recipe
    @GetMapping("/{recipeId}/ingredients")
    public List<Ingredient> getIngredientsInRecipe(@PathVariable Recipe recipe) {
        return ingredientService.getIngredientsInRecipe(recipe);
    }

    //get all ingredients
    @GetMapping("/ingredients")
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ingredients;
    }


//UPDATE
    //update ingredient
    @PutMapping("/ingredients/{ingredientId}")
    public ResponseEntity<?> updateIngredient(@PathVariable int ingredientId, @RequestBody Ingredient ingredient) throws Exception {
        try {
            Ingredient updateIngredient = ingredientService.updateIngredient(ingredientId, ingredient);
            return ResponseEntity.ok(updateIngredient);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//DELETE
    //delete ingredient
    @DeleteMapping("ingredients/{id}")
    public void deleteIngredient(@PathVariable int id) {
        ingredientService.deleteIngredient(id);
    }

}
