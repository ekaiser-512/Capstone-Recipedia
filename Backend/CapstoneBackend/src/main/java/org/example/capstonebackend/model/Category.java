package org.example.capstonebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes;


}
