package com.library.book_library.service;
import com.library.book_library.exception.AuthorNotFoundException;
import com.library.book_library.exception.BookNotFoundException;
import com.library.book_library.model.Author;
import com.library.book_library.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public Author getAuthorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author addAuthor(Author author) {
        return repository.save(author);
    }

    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        repository.delete(author);
    }
}