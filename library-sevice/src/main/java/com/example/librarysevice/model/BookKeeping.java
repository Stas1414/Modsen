package com.example.librarysevice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookKeeping {
    @Id
    private Long id;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnAt;
}
