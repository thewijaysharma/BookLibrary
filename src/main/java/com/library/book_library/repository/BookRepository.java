package com.library.book_library.repository;

import com.library.book_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { // Book - entity type, Long - primary key type
}
