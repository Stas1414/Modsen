package com.example.librarysevice.repository;

import com.example.librarysevice.model.BookKeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookKeepingRepository extends JpaRepository<BookKeeping, Long> {
    Optional<BookKeeping> findById(Long id);
}
