package com.example.mainservice.controller;

import com.example.mainservice.models.Book;
import com.example.mainservice.models.BookDTO;
import com.example.mainservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Получить список книг")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> findAllBooks(){
        List<Book> books = bookService.findAll();
        return books.stream().map(book -> bookService.convertToDTO(book)).toList();
    }

    @Operation(summary = "Получить список свободных книг")
    @GetMapping("/all/free")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> findFreeBooks(@RequestHeader(name = "Authorization", required = false) String authHeader){
        var headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return bookService.findFree(headers);
    }

    @Operation(summary = "Получить книгу по id")
    @GetMapping("/find/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO findBookById(@PathVariable("id") Long id){
        Book book = bookService.findById(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with ID: " + id);
        }
        return bookService.convertToDTO(book);
    }


    @Operation(summary = "Получить книгу по isbn")
    @GetMapping("/find/isbn/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO findBookByIsbn(@PathVariable("isbn")String isbn){
        Book book = bookService.findByIsbn(isbn);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with ISBN: " + isbn);
        }
        return bookService.convertToDTO(book);
    }

    @Operation(summary = "Добавить книгу")
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO saveBook(@RequestBody BookDTO bookDTO){
        Book book = bookService.convertToEntity(bookDTO);
        book = bookService.save(book);
        return bookService.convertToDTO(book);
    }

    @Operation(summary = "Изменить книгу")
    @PatchMapping("/changes/{id}")
    public BookDTO changeBook(@RequestBody Book patch, @PathVariable("id") Long id){
        Book book = bookService.findById(id);
        if(book != null) {
            if (patch.getAuthor() != null) book.setAuthor(patch.getAuthor());
            if (patch.getIsbn() != null) book.setIsbn(patch.getIsbn());
            if (patch.getDescription() != null) book.setDescription(patch.getDescription());
            if (patch.getName() != null) book.setName(patch.getName());
            if (patch.getStyle() != null) book.setStyle(patch.getStyle());

            bookService.save(book);
            return bookService.convertToDTO(book);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with ID: " + id);
    }

    @Operation(summary = "Удалить книгу")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

}
