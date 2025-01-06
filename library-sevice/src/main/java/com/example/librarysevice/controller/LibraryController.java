package com.example.librarysevice.controller;

import com.example.librarysevice.model.BookKeeping;
import com.example.librarysevice.service.BookKeepingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keep")
@AllArgsConstructor
public class LibraryController {
    private final BookKeepingService bookKeepingService;

    @PostMapping("/save")
    public ResponseEntity<?> recordBook(@RequestBody BookKeeping bookKeeping){
        bookKeepingService.save(bookKeeping);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/free")
    public List<BookKeeping> findFree(){
        return bookKeepingService.findAll();
    }

}
