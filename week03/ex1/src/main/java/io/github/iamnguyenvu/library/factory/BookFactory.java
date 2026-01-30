package io.github.iamnguyenvu.library.factory;

import io.github.iamnguyenvu.library.model.Book;

/**
 * FACTORY METHOD PATTERN
 * Tạo các loại Book khác nhau
 */
public class BookFactory {

    public static Book createBook(String type, String isbn, String title, String author, String genre) {
        Book book = new Book(isbn, title, author, genre, type);

        switch (type.toLowerCase()) {
            case "physical":
                book.setPages(350);
                System.out.println("📕 Created Physical Book: " + title + " (" + book.getPages() + " pages)");
                break;

            case "ebook":
                book.setFileSize(2.5);
                System.out.println("📱 Created EBook: " + title + " (" + book.getFileSize() + " MB)");
                break;

            case "audiobook":
                book.setDuration(420);
                System.out.println("🎧 Created AudioBook: " + title + " (" + book.getDuration() + " minutes)");
                break;

            default:
                System.out.println("📘 Created Generic Book: " + title);
        }

        return book;
    }

    // Convenience methods
    public static Book createPhysicalBook(String isbn, String title, String author, String genre, int pages) {
        Book book = createBook("physical", isbn, title, author, genre);
        book.setPages(pages);
        return book;
    }

    public static Book createEBook(String isbn, String title, String author, String genre, double fileSize) {
        Book book = createBook("ebook", isbn, title, author, genre);
        book.setFileSize(fileSize);
        return book;
    }

    public static Book createAudioBook(String isbn, String title, String author, String genre, int duration) {
        Book book = createBook("audiobook", isbn, title, author, genre);
        book.setDuration(duration);
        return book;
    }
}
