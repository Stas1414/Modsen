package com.example.mainservice.service;


import com.example.mainservice.models.Book;
import com.example.mainservice.models.BookDTO;
import com.example.mainservice.models.BookKeepingDTO;
import com.example.mainservice.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookService {
    private final RestTemplate restTemplate;

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    public BookDTO convertToDTO(Book book){
        return modelMapper.map(book, BookDTO.class);
    }
    public Book convertToEntity(BookDTO bookDTO){
        return modelMapper.map(bookDTO, Book.class);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.findById(id).get();
    }

    public Book findByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).get();
    }

    public Book save(Book book){
        String token = userService.getUserJwtToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        Book savedBook = bookRepository.save(book);
        HttpEntity<Long> request = new HttpEntity<>(savedBook.getId(), headers);
        String urlLibrary = "http://localhost:8081/library-service/api/keep/save";
        restTemplate.postForEntity(urlLibrary, request, String.class);
        return savedBook;
    }

    public List<BookDTO> findFree(HttpHeaders headers){
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String urlLibrary = "http://localhost:8081/library-service/api/keep/free";

        ResponseEntity<BookKeepingDTO[]> response = restTemplate.exchange(
                urlLibrary,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BookKeepingDTO[].class
        );
        List<BookKeepingDTO> bookKeepingDTOS = Arrays.asList(response.getBody());
        List<Book> books = findAll();
        List<BookDTO> result = new ArrayList<>();
        for (Book book : books) {
            boolean isBookFree = true;

            for (BookKeepingDTO bookKeep : bookKeepingDTOS) {
                if (book.getId().equals(bookKeep.getId()) && bookKeep.getReturnAt().isAfter(LocalDateTime.now())) {
                    isBookFree = false;
                    break;
                }
            }
            if (isBookFree) {
                result.add(convertToDTO(book));
            }
        }
        return result;
    }

    @Transactional
    public void delete(Long id){
        bookRepository.deleteById(id);
    }


}
