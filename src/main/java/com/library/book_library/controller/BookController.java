package com.library.book_library.controller;

import com.library.book_library.model.Book;
import com.library.book_library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService service){
        this.bookService = service;
    }
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")   // GET /books/1
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody Book book) {
        bookService.addNewBook(book);
        return ResponseEntity.status(201).build(); // todo send success response body later
    }

    @PutMapping("/{id}")   // PUT /books/1
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook); // 200 + updated book
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/{id}") // DELETE
    public ResponseEntity<Void> removeBook(@PathVariable Long id){
        boolean isSuccessful = bookService.deleteBook(id);
        if (isSuccessful) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
