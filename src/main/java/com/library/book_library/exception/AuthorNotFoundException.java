package com.library.book_library.exception;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(Long id){
        super("Author with id "+id+" not found");
    }
}
