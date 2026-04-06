package com.library.book_library.model;

import jakarta.validation.constraints.NotBlank;

public class Book {
    private final Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    private String author;

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public void setTitle(String title) {
        this.title  = title;
    }
    public void setAuthor(String author) { this.author = author; }
}