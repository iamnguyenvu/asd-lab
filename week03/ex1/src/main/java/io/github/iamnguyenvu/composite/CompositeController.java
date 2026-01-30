package io.github.iamnguyenvu.composite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * REST Controller for Composite Pattern Demo
 */
@RestController
@RequestMapping("/api/composite")
public class CompositeController {

    @GetMapping("/demo")
    public String runDemo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        try {
            demoFileSystem();
            System.out.println("\n\n");
            demoUIComponents();
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        return "<pre>" + baos.toString() + "</pre>";
    }

    private void demoFileSystem() {
        System.out.println("=".repeat(70));
        System.out.println("COMPOSITE PATTERN - FILE SYSTEM DEMO");
        System.out.println("=".repeat(70));

        // Tạo cấu trúc thư mục
        Directory root = new Directory("root");

        // Documents folder
        Directory documents = new Directory("documents");
        documents.add(new File("resume.pdf", 250));
        documents.add(new File("cover-letter.docx", 120));

        // Projects folder
        Directory projects = new Directory("projects");
        
        Directory webApp = new Directory("web-app");
        webApp.add(new File("index.html", 15));
        webApp.add(new File("style.css", 8));
        webApp.add(new File("app.js", 45));

        Directory mobileApp = new Directory("mobile-app");
        mobileApp.add(new File("MainActivity.java", 32));
        mobileApp.add(new File("activity_main.xml", 6));

        projects.add(webApp);
        projects.add(mobileApp);

        // Photos folder
        Directory photos = new Directory("photos");
        photos.add(new File("vacation.jpg", 2500));
        photos.add(new File("family.jpg", 1800));

        // Add to root
        root.add(documents);
        root.add(projects);
        root.add(photos);
        root.add(new File("readme.txt", 5));

        // Display
        System.out.println("\n📂 File System Structure:");
        System.out.println("─".repeat(70));
        root.display(0);

        // Statistics
        System.out.println("\n📊 Statistics:");
        System.out.println("─".repeat(70));
        System.out.println("Total size of root: " + root.getSize() + " KB");
        System.out.println("Total size of documents: " + documents.getSize() + " KB");
        System.out.println("Total size of projects: " + projects.getSize() + " KB");
        System.out.println("Total size of photos: " + photos.getSize() + " KB");
    }

    private void demoUIComponents() {
        System.out.println("=".repeat(70));
        System.out.println("COMPOSITE PATTERN - UI COMPONENTS DEMO");
        System.out.println("=".repeat(70));

        // Login Dialog
        Dialog loginDialog = new Dialog("loginDialog", "User Login");

        Panel header = new Panel("header");
        header.add(new TextBox("title", "Please enter your credentials"));

        Panel body = new Panel("body");
        body.add(new TextBox("username", "Enter username"));
        body.add(new TextBox("password", "Enter password"));
        body.add(new Button("remember", "Remember me"));

        Panel footer = new Panel("footer");
        footer.add(new Button("login", "Login"));
        footer.add(new Button("cancel", "Cancel"));

        loginDialog.add(header);
        loginDialog.add(body);
        loginDialog.add(footer);

        loginDialog.show();

        // Settings Dialog
        System.out.println("\n");
        Dialog settingsDialog = new Dialog("settingsDialog", "Application Settings");

        Panel generalPanel = new Panel("generalSettings");
        generalPanel.add(new TextBox("appName", "Application Name"));
        generalPanel.add(new Button("changeTheme", "Change Theme"));

        Panel advancedPanel = new Panel("advancedSettings");
        advancedPanel.add(new TextBox("apiUrl", "API URL"));

        Panel debugPanel = new Panel("debugPanel");
        debugPanel.add(new Button("enableDebug", "Enable Debug Mode"));
        advancedPanel.add(debugPanel);

        Panel settingsFooter = new Panel("footer");
        settingsFooter.add(new Button("save", "Save"));
        settingsFooter.add(new Button("close", "Close"));

        settingsDialog.add(generalPanel);
        settingsDialog.add(advancedPanel);
        settingsDialog.add(settingsFooter);

        settingsDialog.show();
    }
}
