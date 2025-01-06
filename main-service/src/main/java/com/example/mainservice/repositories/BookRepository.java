package com.example.mainservice.repositories;

import com.example.mainservice.models.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @NonNull
    List<Book> findAll();
    @NonNull
    Optional<Book> findById(Long id);

    Optional<Book> findByIsbn(String isbn);

    void deleteById(Long id);

}
