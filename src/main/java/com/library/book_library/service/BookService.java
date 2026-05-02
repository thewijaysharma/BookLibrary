package com.library.book_library.service;

import com.library.book_library.exception.AuthorNotFoundException;
import com.library.book_library.exception.BookNotFoundException;
import com.library.book_library.model.Author;
import com.library.book_library.model.Book;
import com.library.book_library.model.request.BookRequest;
import com.library.book_library.repository.AuthorRepository;
import com.library.book_library.repository.BookRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Nonnull
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> filterBooks(@Nullable String authorName, @Nullable String bookTitle) {
        String authorSearch = authorName != null ? authorName.toLowerCase() : null;
        String titleSearch = bookTitle != null ? bookTitle.toLowerCase() : null;

        return bookRepository.findAll().stream()
                .filter(book -> authorSearch == null || book.getAuthor().getName().toLowerCase().contains(authorSearch))
                .filter(book -> titleSearch == null || book.getTitle().toLowerCase().contains(titleSearch)).toList();
    }

    public Book addNewBook(BookRequest bookRequest) {
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(bookRequest.getAuthorId()));
        Book book = new Book(bookRequest.getTitle(), author);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookRequest bookRequest) {
        Book targetBook = getBookById(id);
        targetBook.setTitle(bookRequest.getTitle());
        if (bookRequest.getAuthorId() != null) {
            Author author = authorRepository.findById(bookRequest.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException(bookRequest.getAuthorId()));
            targetBook.setAuthor(author);
        }
        return bookRepository.save(targetBook);
    }

    public void deleteBook(Long id) {
        Book targetBook = getBookById(id);
        bookRepository.delete(targetBook);
    }
}
