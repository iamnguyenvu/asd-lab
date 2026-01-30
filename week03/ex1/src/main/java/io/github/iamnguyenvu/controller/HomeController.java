package io.github.iamnguyenvu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
            <html>
            <head>
                <title>Week 03 - Design Patterns Lab</title>
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        max-width: 900px;
                        margin: 50px auto;
                        padding: 20px;
                        background: #f5f5f5;
                    }
                    .container {
                        background: white;
                        padding: 40px;
                        border-radius: 10px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                    }
                    h1 {
                        color: #2c3e50;
                        border-bottom: 3px solid #3498db;
                        padding-bottom: 10px;
                    }
                    h2 {
                        color: #34495e;
                        margin-top: 30px;
                    }
                    .endpoint {
                        background: #ecf0f1;
                        padding: 15px;
                        margin: 10px 0;
                        border-radius: 5px;
                        border-left: 4px solid #3498db;
                    }
                    .endpoint a {
                        color: #2980b9;
                        text-decoration: none;
                        font-weight: bold;
                        font-size: 16px;
                    }
                    .endpoint a:hover {
                        color: #3498db;
                        text-decoration: underline;
                    }
                    .description {
                        color: #7f8c8d;
                        margin-top: 5px;
                        font-size: 14px;
                    }
                    .badge {
                        display: inline-block;
                        padding: 4px 8px;
                        background: #3498db;
                        color: white;
                        border-radius: 3px;
                        font-size: 12px;
                        margin-right: 10px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>📚 Week 03 - Design Patterns Lab</h1>
                    <p><strong>Spring Boot Application - Java Implementation</strong></p>
                    <p>Explore different design patterns through interactive demos!</p>
                    
                    <h2>🎯 Available Demos</h2>
                    
                    <div class="endpoint">
                        <span class="badge">COMPOSITE</span>
                        <a href="/api/composite/demo" target="_blank">/api/composite/demo</a>
                        <div class="description">📁 File System & 🪟 UI Components - Tree structures</div>
                    </div>
                    
                    <div class="endpoint">
                        <span class="badge">OBSERVER</span>
                        <a href="/api/observer/demo" target="_blank">/api/observer/demo</a>
                        <div class="description">📊 Stock Market & 📋 Task Management - Event notification</div>
                    </div>
                    
                    <div class="endpoint">
                        <span class="badge">ADAPTER</span>
                        <a href="/api/adapter/demo" target="_blank">/api/adapter/demo</a>
                        <div class="description">🔄 XML to JSON Conversion - Interface compatibility</div>
                    </div>
                    
                    <div class="endpoint">
                        <span class="badge">LIBRARY SYSTEM</span>
                        <a href="/api/library/demo" target="_blank">/api/library/demo</a>
                        <div class="description">📚 Complete Library System - Integrates 5 Patterns</div>
                        <div class="description" style="margin-left: 20px;">
                            ✓ Singleton (Library Instance)<br>
                            ✓ Factory Method (Create Books)<br>
                            ✓ Strategy (Search Algorithms)<br>
                            ✓ Observer (Notifications)<br>
                            ✓ Decorator (Loan Enhancements)
                        </div>
                    </div>
                    
                    <h2>📖 Pattern Summary</h2>
                    <ul>
                        <li><strong>Composite:</strong> Compose objects into tree structures</li>
                        <li><strong>Observer:</strong> Define one-to-many dependency for notifications</li>
                        <li><strong>Adapter:</strong> Convert interface to match client expectations</li>
                        <li><strong>Singleton:</strong> Ensure only one instance exists</li>
                        <li><strong>Factory Method:</strong> Create objects without specifying exact class</li>
                        <li><strong>Strategy:</strong> Define family of algorithms, make them interchangeable</li>
                        <li><strong>Decorator:</strong> Add responsibilities to objects dynamically</li>
                    </ul>
                    
                    <h2>🚀 Quick Start</h2>
                    <ol>
                        <li>Click any demo link above to see it in action</li>
                        <li>View the formatted console output in your browser</li>
                        <li>Check the source code in <code>src/main/java/com/asd/lab/week03/</code></li>
                    </ol>
                </div>
            </body>
            </html>
            """;
    }
}
