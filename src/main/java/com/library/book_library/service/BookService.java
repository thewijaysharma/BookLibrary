package com.library.book_library.service;

import com.library.book_library.exception.BookNotFoundException;
import com.library.book_library.model.Book;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BookService {

    private final List<Book> books = new LinkedList<>(List.of(
            new Book(1L, "Clean Code", "Robert C. Martin"),
            new Book(2L, "The Pragmatic Programmer", "Andrew Hunt"),
            new Book(3L, "Designing Data-Intensive Applications", "Martin Kleppmann")
    ));

    public List<Book> getAllBooks() {
        return books;
    }

    @Nonnull
    public Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> filterBooks(@Nullable String authorName, @Nullable String bookTitle) {
        String authorSearch = authorName != null ? authorName.toLowerCase() : null;
        String titleSearch = bookTitle != null ? bookTitle.toLowerCase() : null;

        return books.stream()
                .filter(book -> authorSearch == null || book.getAuthor().toLowerCase().contains(authorSearch))
                .filter(book -> titleSearch == null || book.getTitle().toLowerCase().contains(titleSearch)).toList();
    }

    public void addNewBook(Book book) {
        books.add(book);
    }

    @Nullable
    public Book updateBook(Long id, Book newBookData) {
        Book targetBook = getBookById(id);
        targetBook.setTitle(newBookData.getTitle());
        targetBook.setAuthor(newBookData.getAuthor());
        return targetBook;
    }

    public boolean deleteBook(Long id){
        Book targetBook = getBookById(id);
        books.remove(targetBook);
        return true;

    }
}
