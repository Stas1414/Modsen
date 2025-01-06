package com.example.librarysevice.service;

import com.example.librarysevice.model.BookKeeping;
import com.example.librarysevice.repository.BookKeepingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class BookKeepingService {
    private final BookKeepingRepository bookKeepingRepository;
    public BookKeeping save(BookKeeping bookKeeping){
        Random random = new Random();
        int randomMinute = random.nextInt(90);
        bookKeeping.setBorrowedAt(LocalDateTime.now());
        bookKeeping.setReturnAt(LocalDateTime.now().plusMinutes(randomMinute));
        return bookKeepingRepository.save(bookKeeping);
    }

    public List<BookKeeping> findAll(){
        return bookKeepingRepository.findAll();
    }
}
