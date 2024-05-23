package org.example.capstonebackend.repository;

import org.example.capstonebackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
}
