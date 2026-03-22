package com.library.book_library.service;

import com.library.book_library.model.Book;
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

    @Nullable
    public Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addNewBook(Book book) {
        books.add(book);
    }

    @Nullable
    public Book updateBook(Long id, Book newBookData) {
        Book targetBook = getBookById(id);
        if (targetBook != null) {
            targetBook.setTitle(newBookData.getTitle());
            targetBook.setAuthor(newBookData.getAuthor());
            return targetBook;
        } else {
            return null; // book not found, couldn't update
        }
    }

    public boolean deleteBook(Long id){
        Book targetBook = getBookById(id);
        if(targetBook != null){
            books.remove(targetBook);
            return true;
        }else{
            return false;
        }

    }
}
