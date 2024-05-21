package org.example.capstonebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String datOfBirth;

    //Creating 1:1 relationship between User and Auth
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id", nullable = false, unique = true)
    private Auth auth;

    //Creating 1:N relationship between User and Book
    @OneToMany(mappedBy = "user")
    private List<Book> books;

}
