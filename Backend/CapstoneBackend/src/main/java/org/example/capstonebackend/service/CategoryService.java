package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.ICategoryRepository;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    private final IRecipeRepository recipeRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository, IRecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }


//CREATE
    //create category
    public Category addCategory(Category category) throws Exception {
        Optional<Category> categoryExists = categoryRepository.findById(category.getId());

        if(categoryExists.isPresent()) {
            throw new Exception("Category with id " + category.getId() + " already exists");
        }

        return categoryRepository.save(category);
    }

    //add category to book

    //add recipe to category todo move to recipe entity
    public Category  addRecipeToCategory(Integer categoryId, Integer recipeId) throws Exception {
        // Retrieve the category by ID or throw an exception if not found
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new Exception ("Category with id " + categoryId + " not found"));
        // Retrieve the recipe by ID or throw an exception if not found
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new Exception ("Recipe with id " + recipeId + " not found"));

        //Add the recipe to the category's list of recipes
        category.getRecipes().add(recipe);
        //set the category for the recipe
        recipe.setCategory(category);

        //save the updated recipe
        recipeRepository.save(recipe);
        //save the updated category
        categoryRepository.save(category);

        return category;
    }

//READ
    //Get Category by Id
    public Category getCategoryById(Integer id) throws Exception {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category with id " + id + " not found" ));
    }

    //Get All Categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


//UPDATE
    //Update Category
    public Category updateCategory(int id, Category category) throws Exception {
        Category oldCategory = categoryRepository.findById(category.getId()).orElse(null);

        if (oldCategory == null) {
            throw new Exception("Category with id " + category.getId() + " not found");
        }
        oldCategory.setTitle(category.getTitle());

        return categoryRepository.save(oldCategory);
    }

//DELETE
    //Delete Category
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }


}
