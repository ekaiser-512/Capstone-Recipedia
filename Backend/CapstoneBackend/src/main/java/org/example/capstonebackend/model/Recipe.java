package org.example.capstonebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recipeId;
    private String recipeName;
    private String recipeAuthor;

    //Creating 1:N relationship between Category and Recipe
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Recipe recipe;

    //Creating M:N relationship between Recipe and Ingredient
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    List<Ingredient> ingredients = new ArrayList<>();
}
