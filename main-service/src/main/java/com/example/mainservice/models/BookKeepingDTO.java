package com.example.mainservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookKeepingDTO {
    private Long id;
    private LocalDateTime takenAt;
    private LocalDateTime returnAt;
}
