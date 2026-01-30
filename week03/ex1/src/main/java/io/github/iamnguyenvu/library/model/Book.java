package io.github.iamnguyenvu.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String type; // Physical, EBook, AudioBook
    private boolean available;
    private String borrowedBy;
    private LocalDate dueDate;

    // Additional properties based on type
    private Integer pages;        // For Physical Books
    private Double fileSize;      // For EBooks (MB)
    private Integer duration;     // For AudioBooks (minutes)

    // Constructor for basic book
    public Book(String isbn, String title, String author, String genre, String type) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.type = type;
        this.available = true;
    }
}
