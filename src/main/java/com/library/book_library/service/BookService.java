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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<Book> filterBooks(@Nullable String authorName, @Nullable String bookTitle, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name())
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (authorName != null && bookTitle != null) {
            return bookRepository.findByTitleAndAuthorName(bookTitle, authorName, pageable);
        } else if (authorName != null) {
            return bookRepository.findByAuthorName(authorName, pageable);
        } else if (bookTitle != null) {
            return bookRepository.findByTitle(bookTitle, pageable);
        }
        return bookRepository.findAll(pageable);
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
