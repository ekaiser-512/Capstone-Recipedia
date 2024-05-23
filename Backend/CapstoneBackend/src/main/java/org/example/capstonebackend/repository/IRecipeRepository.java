package org.example.capstonebackend.repository;

import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findByName(String recipeName);

    List<Recipe> findByCategory(Category category);

//    Optional<Object> findByAuthor(String recipeAuthor);
}
