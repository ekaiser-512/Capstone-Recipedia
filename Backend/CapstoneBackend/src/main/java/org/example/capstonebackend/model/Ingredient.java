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
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ingredientId;
    private String name;
    private String ingredientFoodGroup;
    private Boolean commonAllergen;
    private String dietaryRestriction;

    //Creating M:N relationship with Recipe
    @JsonIgnore
    @ManyToMany(mappedBy = "ingredients")
    private List<Recipe> recipes = new ArrayList<>();
}
