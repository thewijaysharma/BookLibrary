package com.library.book_library.model.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private Long authorId; // optional

    public String getTitle() { return title; }
    public Long getAuthorId() { return authorId; }
}
