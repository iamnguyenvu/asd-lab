package io.github.iamnguyenvu.observer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RestController
@RequestMapping("/api/observer")
public class ObserverController {

    @GetMapping("/demo")
    public String runDemo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        try {
            demoStockMarket();
            System.out.println("\n\n");
            demoTaskManagement();
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        return "<pre>" + baos.toString() + "</pre>";
    }

    private void demoStockMarket() {
        System.out.println("=".repeat(70));
        System.out.println("OBSERVER PATTERN - STOCK MARKET DEMO");
        System.out.println("=".repeat(70));

        // Create stocks
        Stock apple = new Stock("AAPL", 150.00);
        Stock google = new Stock("GOOGL", 2800.00);

        // Create investors
        Investor alice = new Investor("Alice", "aggressive");
        Investor bob = new Investor("Bob", "conservative");
        Investor charlie = new Investor("Charlie", "balanced");

        System.out.println("\n📈 ĐĂNG KÝ THEO DÕI:");
        System.out.println("─".repeat(70));
        
        apple.attach(alice);
        apple.attach(bob);
        google.attach(charlie);

        // Investors buy stocks
        System.out.println("\n💵 MUA CỔ PHIẾU:");
        System.out.println("─".repeat(70));
        alice.buy("AAPL", 100, 150.00);
        bob.buy("AAPL", 50, 150.00);
        charlie.buy("GOOGL", 10, 2800.00);

        // Price changes
        System.out.println("\n📊 THAY ĐỔI GIÁ:");
        System.out.println("─".repeat(70));
        
        apple.setPrice(155.00);  // +3.33%
        apple.setPrice(160.00);  // +6.67%
        google.setPrice(2700.00); // -3.57%
    }

    private void demoTaskManagement() {
        System.out.println("=".repeat(70));
        System.out.println("OBSERVER PATTERN - TASK MANAGEMENT DEMO");
        System.out.println("=".repeat(70));

        // Create team members
        TeamMember manager = new TeamMember("Sarah", "Manager");
        TeamMember dev1 = new TeamMember("John", "Developer");
        TeamMember qa = new TeamMember("Emma", "QA");

        // Create task
        Task task1 = new Task("TASK-001", "Implement Login Feature", 
                            "Create login page with JWT authentication");

        System.out.println("\n📌 THEO DÕI TASK:");
        System.out.println("─".repeat(70));
        task1.attach(manager);
        task1.attach(dev1);
        task1.attach(qa);

        // Task workflow
        System.out.println("\n🔄 WORKFLOW:");
        System.out.println("─".repeat(70));
        
        task1.setAssignee("John");
        task1.setStatus("IN_PROGRESS");
        task1.setStatus("IN_REVIEW");
        task1.setStatus("DONE");
    }
}
