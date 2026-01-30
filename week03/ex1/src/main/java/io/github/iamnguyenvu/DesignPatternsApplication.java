package io.github.iamnguyenvu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Week 03 - Design Patterns Lab
 * Main Spring Boot Application
 * 
 * @author Nguyen Hoang Nguyen Vu
 */
@SpringBootApplication
public class DesignPatternsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesignPatternsApplication.class, args);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("WEEK 03 - DESIGN PATTERNS LAB");
        System.out.println("Spring Boot Application Started Successfully!");
        System.out.println("=".repeat(80));
        System.out.println("\nAPI Endpoints:");
        System.out.println("  • http://localhost:8080/");
        System.out.println("  • http://localhost:8080/api/composite/demo");
        System.out.println("  • http://localhost:8080/api/observer/demo");
        System.out.println("  • http://localhost:8080/api/adapter/demo");
        System.out.println("  • http://localhost:8080/api/library/demo");
        System.out.println("=".repeat(80) + "\n");
    }
}
