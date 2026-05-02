package com.library.book_library.repository;

import com.library.book_library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { // Book - entity type, Long - primary key type

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByTitle(String title, Pageable pageable);

    Page<Book> findByAuthorName(String name, Pageable pageable);

    Page<Book> findByTitleAndAuthorName(
            String title, String name, Pageable pageable);

}
