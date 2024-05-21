package org.example.capstonebackend.repository;

import org.example.capstonebackend.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, Integer> {
    boolean existsByEmail(String userEmail);

    Optional<Object> findByEmail(String email);
}
