package com.library.book_library.exception

// Not in use
class DuplicateBookException(id : Long) : RuntimeException("Book with id $id already exists.")