package io.github.iamnguyenvu.library;

import io.github.iamnguyenvu.library.decorator.*;
import io.github.iamnguyenvu.library.factory.BookFactory;
import io.github.iamnguyenvu.library.model.Book;
import io.github.iamnguyenvu.library.model.Member;
import io.github.iamnguyenvu.library.observer.LibraryObserver;
import io.github.iamnguyenvu.library.observer.Librarian;
import io.github.iamnguyenvu.library.observer.MemberObserver;
import io.github.iamnguyenvu.library.singleton.Library;
import io.github.iamnguyenvu.library.strategy.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

/**
 * Library System Integration Demo
 * Tích hợp tất cả 5 Design Patterns
 */
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @GetMapping("/demo")
    public String runDemo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        try {
            runLibraryDemo();
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        return "<pre>" + baos.toString() + "</pre>";
    }

    private void runLibraryDemo() {
        System.out.println("=".repeat(80));
        System.out.println("LIBRARY MANAGEMENT SYSTEM - DESIGN PATTERNS INTEGRATION");
        System.out.println("=".repeat(80));

        // 1. SINGLETON - Get Library Instance
        System.out.println("\n1️⃣  SINGLETON PATTERN - Library Instance");
        System.out.println("─".repeat(80));
        Library library = Library.getInstance();
        Library library2 = Library.getInstance();
        System.out.println("✅ Same instance? " + (library == library2));

        // 2. OBSERVER PATTERN - Attach observers
        System.out.println("\n2️⃣  OBSERVER PATTERN - Attach Observers");
        System.out.println("─".repeat(80));
        LibraryObserver librarian = new Librarian("Mr. Smith");
        LibraryObserver member1 = new MemberObserver("Alice", "Programming");
        LibraryObserver member2 = new MemberObserver("Bob", "Fiction");
        
        library.attach(librarian);
        library.attach(member1);
        library.attach(member2);

        // 3. FACTORY METHOD - Create Books
        System.out.println("\n3️⃣  FACTORY METHOD PATTERN - Create Books");
        System.out.println("─".repeat(80));
        Book book1 = BookFactory.createPhysicalBook(
            "978-0134685991", "Effective Java", "Joshua Bloch", "Programming", 416
        );
        Book book2 = BookFactory.createEBook(
            "978-0135166307", "Clean Code", "Robert Martin", "Programming", 3.2
        );
        Book book3 = BookFactory.createAudioBook(
            "978-0451524935", "1984", "George Orwell", "Fiction", 720
        );

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        // Register members
        System.out.println("\n👥 REGISTERING MEMBERS");
        System.out.println("─".repeat(80));
        Member alice = new Member("M001", "Alice Johnson", "alice@example.com", "Premium");
        Member bob = new Member("M002", "Bob Smith", "bob@example.com", "Standard");
        
        library.registerMember(alice);
        library.registerMember(bob);

        // 4. STRATEGY PATTERN - Search Books
        System.out.println("\n4️⃣  STRATEGY PATTERN - Search Books");
        System.out.println("─".repeat(80));
        
        BookSearchContext searchContext = new BookSearchContext();
        
        // Search by title
        searchContext.setStrategy(new SearchByTitle());
        List<Book> results = searchContext.executeSearch(library.getAllBooks(), "Java");
        System.out.println("Found " + results.size() + " book(s) with 'Java' in title");
        results.forEach(b -> System.out.println("  • " + b.getTitle()));

        // Search by genre
        searchContext.setStrategy(new SearchByGenre());
        results = searchContext.executeSearch(library.getAllBooks(), "Programming");
        System.out.println("Found " + results.size() + " Programming book(s)");
        results.forEach(b -> System.out.println("  • " + b.getTitle()));

        // 5. DECORATOR PATTERN - Loan Enhancements
        System.out.println("\n5️⃣  DECORATOR PATTERN - Loan Options");
        System.out.println("─".repeat(80));

        LocalDate today = LocalDate.now();

        // Standard Loan
        Loan standardLoan = new BaseLoan();
        System.out.println("📋 " + standardLoan.getDescription());
        System.out.println("   Duration: " + standardLoan.getLoanDuration() + " days");
        System.out.println("   Fee: $" + standardLoan.getFee());
        System.out.println("   Due: " + standardLoan.calculateDueDate(today));

        // Extended Loan
        Loan extendedLoan = new ExtendedLoan(new BaseLoan());
        System.out.println("\n📋 " + extendedLoan.getDescription());
        System.out.println("   Duration: " + extendedLoan.getLoanDuration() + " days");
        System.out.println("   Fee: $" + extendedLoan.getFee());
        System.out.println("   Due: " + extendedLoan.calculateDueDate(today));

        // Premium + Digital Access (stacked decorators)
        Loan premiumWithDigital = new DigitalAccessLoan(new PremiumLoan(new BaseLoan()));
        System.out.println("\n📋 " + premiumWithDigital.getDescription());
        System.out.println("   Duration: " + premiumWithDigital.getLoanDuration() + " days");
        System.out.println("   Fee: $" + premiumWithDigital.getFee());
        System.out.println("   Due: " + premiumWithDigital.calculateDueDate(today));

        // Borrow book with standard loan
        System.out.println("\n📚 BORROWING BOOKS");
        System.out.println("─".repeat(80));
        library.borrowBook(book1.getIsbn(), alice.getId(), standardLoan.calculateDueDate(today));
        library.borrowBook(book2.getIsbn(), bob.getId(), extendedLoan.calculateDueDate(today));

        // Return book
        System.out.println("\n📚 RETURNING BOOKS");
        System.out.println("─".repeat(80));
        library.returnBook(book1.getIsbn());

        // Statistics
        System.out.println();
        library.printStatistics();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("✅ ALL DESIGN PATTERNS SUCCESSFULLY INTEGRATED!");
        System.out.println("=".repeat(80));
    }
}
