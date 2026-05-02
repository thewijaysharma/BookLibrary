package com.library.book_library.controller;

import com.library.book_library.model.Book;
import com.library.book_library.model.request.BookRequest;
import com.library.book_library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService service){
        this.bookService = service;
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(required = false, name = "author") String authorName,
                                                  @RequestParam(required = false, name = "title") String titleName,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(bookService.filterBooks(authorName, titleName, page, size, sortBy, direction));
    }

    @GetMapping("/{id}")   // GET /books/1
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")   // PUT /books/1
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        Book updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(updatedBook); // 200 + updated book
    }

    @DeleteMapping("/{id}") // DELETE
    public ResponseEntity<Void> removeBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Book> addBook(
            @Valid @RequestBody BookRequest request,
            UriComponentsBuilder uriBuilder) {
        Book saved = bookService.addNewBook(request);
        URI location = uriBuilder.path("/books/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }


}
