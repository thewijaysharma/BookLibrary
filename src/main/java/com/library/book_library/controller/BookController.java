package com.library.book_library.controller;

import com.library.book_library.model.Book;
import com.library.book_library.service.BookService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false, name = "author") @Nullable String authorName,
                                                  @RequestParam(required = false, name = "title") @Nullable String titleName) {
        if(authorName == null && titleName == null){
            return ResponseEntity.ok(bookService.getAllBooks());
        }else{
            List<Book> books = bookService.filterBooks(authorName, titleName);
            if(books.size() > 0){
                return ResponseEntity.ok(books);
            }else{
                return ResponseEntity.noContent().build();
            }
        }
    }

    @GetMapping("/{id}")   // GET /books/1
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@Valid @RequestBody Book book) {
        bookService.addNewBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // todo send success response body later
    }

    @PutMapping("/{id}")   // PUT /books/1
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
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
