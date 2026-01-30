package io.github.iamnguyenvu.library.observer;

import io.github.iamnguyenvu.library.model.Book;
import io.github.iamnguyenvu.library.model.Member;
import lombok.Getter;

import java.util.Map;

/**
 * OBSERVER PATTERN
 */
public interface LibraryObserver {
    void update(String event, Object data);
    String getName();
}

/**
 * Concrete Observer - Librarian
 */
@Getter
class Librarian implements LibraryObserver {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    @Override
    public void update(String event, Object data) {
        System.out.println("👨‍💼 Librarian " + name + " notified: " + event);
        
        switch (event) {
            case "BOOK_ADDED":
                if (data instanceof Book book) {
                    System.out.println("   📌 Action: Update catalog, assign shelf location");
                }
                break;
            case "BOOK_BORROWED":
                System.out.println("   📌 Action: Update loan records");
                break;
            case "BOOK_RETURNED":
                System.out.println("   📌 Action: Check condition, reshelve book");
                break;
            case "MEMBER_REGISTERED":
                if (data instanceof Member member) {
                    System.out.println("   📌 Action: Send welcome email to " + member.getEmail());
                }
                break;
        }
    }
}

/**
 * Concrete Observer - Member (receives notifications about new books in their interest)
 */
@Getter
class MemberObserver implements LibraryObserver {
    private String name;
    private String interestedGenre;

    public MemberObserver(String name, String interestedGenre) {
        this.name = name;
        this.interestedGenre = interestedGenre;
    }

    @Override
    public void update(String event, Object data) {
        if ("BOOK_ADDED".equals(event) && data instanceof Book book) {
            if (book.getGenre().equalsIgnoreCase(interestedGenre)) {
                System.out.println("📧 Member " + name + " notified: New " + interestedGenre + 
                                 " book available: \"" + book.getTitle() + "\"");
            }
        }
    }
}
