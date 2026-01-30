package io.github.iamnguyenvu.library.singleton;

import io.github.iamnguyenvu.library.model.Book;
import io.github.iamnguyenvu.library.model.Member;
import io.github.iamnguyenvu.library.observer.LibraryObserver;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

/**
 * SINGLETON PATTERN - Library Management System
 * Đảm bảo chỉ có DUY NHẤT 1 instance của Library
 */
public class Library {
    
    // Eager initialization - Thread-safe
    private static final Library INSTANCE = new Library();

    @Getter
    private String name;
    private Map<String, Book> books;
    private Map<String, Member> members;
    private List<LibraryObserver> observers;

    // Private constructor - ngăn khởi tạo từ bên ngoài
    private Library() {
        this.name = "ASD Lab Library";
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.observers = new ArrayList<>();
        System.out.println("📚 Library System Initialized (Singleton)");
    }

    // Public method để lấy instance duy nhất
    public static Library getInstance() {
        return INSTANCE;
    }

    // Observer methods
    public void attach(LibraryObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("👀 Observer attached: " + observer.getName());
        }
    }

    public void notifyObservers(String event, Object data) {
        observers.forEach(observer -> observer.update(event, data));
    }

    // Book management
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
        System.out.println("📕 Book added: " + book.getTitle());
        notifyObservers("BOOK_ADDED", book);
    }

    public Book getBook(String isbn) {
        return books.get(isbn);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public void removeBook(String isbn) {
        Book book = books.remove(isbn);
        if (book != null) {
            System.out.println("📕 Book removed: " + book.getTitle());
            notifyObservers("BOOK_REMOVED", book);
        }
    }

    // Member management
    public void registerMember(Member member) {
        members.put(member.getId(), member);
        System.out.println("👤 Member registered: " + member.getName());
        notifyObservers("MEMBER_REGISTERED", member);
    }

    public Member getMember(String memberId) {
        return members.get(memberId);
    }

    public Collection<Member> getAllMembers() {
        return members.values();
    }

    // Loan management
    public boolean borrowBook(String isbn, String memberId, LocalDate dueDate) {
        Book book = getBook(isbn);
        Member member = getMember(memberId);

        if (book == null || member == null) {
            System.out.println("❌ Book or Member not found");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("❌ Book is not available");
            return false;
        }

        book.setAvailable(false);
        book.setBorrowedBy(memberId);
        book.setDueDate(dueDate);

        Map<String, Object> loanData = new HashMap<>();
        loanData.put("book", book);
        loanData.put("member", member);
        loanData.put("dueDate", dueDate);

        System.out.println("✅ " + member.getName() + " borrowed \"" + book.getTitle() + "\"");
        notifyObservers("BOOK_BORROWED", loanData);
        
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = getBook(isbn);
        if (book == null || book.isAvailable()) {
            System.out.println("❌ Invalid return");
            return false;
        }

        String memberId = book.getBorrowedBy();
        Member member = getMember(memberId);

        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setDueDate(null);

        Map<String, Object> returnData = new HashMap<>();
        returnData.put("book", book);
        returnData.put("member", member);

        System.out.println("✅ \"" + book.getTitle() + "\" returned");
        notifyObservers("BOOK_RETURNED", returnData);

        return true;
    }

    // Statistics
    public void printStatistics() {
        long availableBooks = books.values().stream().filter(Book::isAvailable).count();
        long borrowedBooks = books.size() - availableBooks;

        System.out.println("\n📊 LIBRARY STATISTICS");
        System.out.println("─".repeat(50));
        System.out.println("Total Books: " + books.size());
        System.out.println("Available: " + availableBooks);
        System.out.println("Borrowed: " + borrowedBooks);
        System.out.println("Total Members: " + members.size());
        System.out.println("Total Observers: " + observers.size());
        System.out.println("─".repeat(50));
    }
}
