package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private final IIngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IIngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

//CREATE
    //add ingredient
    public Ingredient addIngredient(Ingredient ingredient) throws Exception {
        Optional<Ingredient> ingredientExists = ingredientRepository.findByName(ingredient.getIngredientName());

        if(ingredientExists.isPresent()) {
            throw new Exception("Ingredient with name " + ingredient.getIngredientName() + " already exists");
        }

        return ingredientRepository.save(ingredient);
    }

//READ
    //get ingredient by name
    public Ingredient getIngredientByName(String ingredientName) throws Exception {
        return ingredientRepository.findByName(ingredientName)
                .orElseThrow(() -> new Exception("Ingredient with name " + ingredientName + " not found"));
    }

    //get ingredient by dietary restriction
    public Ingredient getIngredientByDiet(String dietaryRestriction) throws Exception {
            return ingredientRepository.findByRestriction(dietaryRestriction)
                    .orElseThrow(() -> new Exception("Ingredient with dietary restriction " + dietaryRestriction + " not found"));
    }

    //get list of ingredients that are common allergens
    public List<Ingredient> getIngredientsThatAreCommonAllergens(Boolean commonAllergen) {
        return ingredientRepository.findByAllergen(commonAllergen);
    }

    //get all ingredients
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

//UPDATE
    //update ingredient
    public Ingredient updateIngredient(int id, Ingredient ingredient) throws Exception {
        Ingredient oldIngredient = ingredientRepository.findByName(ingredient.getIngredientName()).orElse(null);

        if(oldIngredient == null) {
            throw new Exception("Ingredient with name " + ingredient.getIngredientName() + " not found");
        }
        oldIngredient.setIngredientName(ingredient.getIngredientName());
        oldIngredient.setIngredientFoodGroup(ingredient.getIngredientFoodGroup());
        oldIngredient.setCommonAllergen(ingredient.getCommonAllergen());
        oldIngredient.setDietaryRestriction(ingredient.getDietaryRestriction());

        return ingredientRepository.save(oldIngredient);
    }

//DELETE
    //delete ingredient
    public void deleteIngredient(int id) {
        ingredientRepository.deleteById(id);
    }

}
