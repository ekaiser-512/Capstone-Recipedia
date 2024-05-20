package org.example.capstonebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authId;
    private String userEmail;
    private String userPassword;
}
