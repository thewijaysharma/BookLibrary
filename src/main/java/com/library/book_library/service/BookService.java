package com.library.book_library.service;

import com.library.book_library.exception.BookNotFoundException;
import com.library.book_library.model.Book;
import com.library.book_library.repository.BookRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Nonnull
    public Book getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> filterBooks(@Nullable String authorName, @Nullable String bookTitle) {
        String authorSearch = authorName != null ? authorName.toLowerCase() : null;
        String titleSearch = bookTitle != null ? bookTitle.toLowerCase() : null;

        return repository.findAll().stream()
                .filter(book -> authorSearch == null || book.getAuthor().toLowerCase().contains(authorSearch))
                .filter(book -> titleSearch == null || book.getTitle().toLowerCase().contains(titleSearch)).toList();
    }

    public Book addNewBook(Book book) {
        return repository.save(book);
    }

    public Book updateBook(Long id, Book newBookData) {
        Book targetBook = getBookById(id);
        targetBook.setTitle(newBookData.getTitle());
        targetBook.setAuthor(newBookData.getAuthor());
        return repository.save(targetBook);
    }

    public void deleteBook(Long id){
        Book targetBook = getBookById(id);
        repository.delete(targetBook);
    }
}
