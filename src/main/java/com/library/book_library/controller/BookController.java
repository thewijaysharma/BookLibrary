package com.library.book_library.controller;

import com.library.book_library.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    List<Book> getAllBooks(){
        return List.of(
                new Book(1L, "Clean Code", "Robert C. Martin"),
                new Book(2L, "The Pragmatic Programmer", "Andrew Hunt"),
                new Book(3L, "Designing Data-Intensive Applications", "Martin Kleppmann")
        );
    }
}
