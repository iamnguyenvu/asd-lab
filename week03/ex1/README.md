# Week 03 - Design Patterns Lab

## 📚 Spring Boot Implementation

Bài tập Design Patterns sử dụng **Spring Boot** (Java) thay vì Node.js Express.

## 🎯 Patterns Implemented

### 1. **Composite Pattern**
- 📁 File System: Cấu trúc cây thư mục và file
- 🪟 UI Components: Cấu trúc component lồng nhau

### 2. **Observer Pattern**
- 📊 Stock Market: Hệ thống thông báo giá cổ phiếu
- 📋 Task Management: Quản lý task với thông báo team

### 3. **Adapter Pattern**
- 🔄 XML to JSON: Chuyển đổi giữa legacy system (XML) và modern system (JSON)

### 4. **Library Management System** (Tích hợp 5 Patterns)
- 🔒 **Singleton**: Library instance duy nhất
- 🏭 **Factory Method**: Tạo các loại sách (Physical, EBook, AudioBook)
- 🔍 **Strategy**: Các thuật toán tìm kiếm (Title, Author, Genre, ISBN, Advanced)
- 📢 **Observer**: Thông báo sự kiện thư viện
- 🎨 **Decorator**: Nâng cấp loan (Extended, Premium, Digital Access)

## 🚀 Quick Start

### Yêu cầu
- Java 17 hoặc cao hơn
- Maven 3.6+

### Cài đặt và chạy

```bash
# Di chuyển vào thư mục project
cd week03/ex1

# Build project
mvn clean install

# Chạy application
mvn spring-boot:run
```

Application sẽ chạy tại: **http://localhost:8080**

## 🌐 API Endpoints

Mở trình duyệt và truy cập:

| Pattern | URL | Description |
|---------|-----|-------------|
| Home | http://localhost:8080/ | Trang chủ với danh sách demos |
| Composite | http://localhost:8080/api/composite/demo | File System & UI Components |
| Observer | http://localhost:8080/api/observer/demo | Stock Market & Task Management |
| Adapter | http://localhost:8080/api/adapter/demo | XML to JSON Conversion |
| Library System | http://localhost:8080/api/library/demo | Tích hợp 5 patterns |

## 📁 Cấu trúc Project

```
src/main/java/com/asd/lab/week03/
├── DesignPatternsApplication.java     # Main Spring Boot App
├── composite/
│   ├── FileSystemComponent.java       # File & Directory
│   ├── UIComponent.java               # Button, Panel, Dialog
│   └── CompositeController.java       # REST API
├── observer/
│   ├── Stock.java                     # Stock & Investor
│   ├── Task.java                      # Task & TeamMember
│   └── ObserverController.java        # REST API
├── adapter/
│   ├── XMLToJSONAdapter.java          # Adapter + DTOs
│   └── AdapterController.java         # REST API
├── library/
│   ├── singleton/
│   │   └── Library.java               # Singleton Library
│   ├── factory/
│   │   └── BookFactory.java           # Factory Method
│   ├── strategy/
│   │   └── SearchStrategy.java        # Search algorithms
│   ├── observer/
│   │   └── LibraryObserver.java       # Librarian, Member observers
│   ├── decorator/
│   │   └── Loan.java                  # Loan decorators
│   ├── model/
│   │   ├── Book.java                  # Book entity
│   │   └── Member.java                # Member entity
│   └── LibraryController.java         # Integration demo
└── controller/
    └── HomeController.java            # Home page
```

## 🧪 Testing

### Test từng pattern riêng lẻ:
1. Mở trình duyệt
2. Truy cập vào endpoint tương ứng
3. Xem kết quả console output

### Test toàn bộ:
- Truy cập http://localhost:8080/ để xem trang chủ
- Click vào từng demo link

## 📖 Learning Guide

### Bước 1: Hiểu từng Pattern
- Đọc code trong từng package
- Chú ý các comment giải thích
- Chạy demo và xem output

### Bước 2: Phân tích UML
- Xác định: Component, Leaf, Composite (Composite Pattern)
- Xác định: Subject, Observer (Observer Pattern)  
- Xác định: Target, Adaptee, Adapter (Adapter Pattern)
- Xác định: Component, Decorator (Decorator Pattern)

### Bước 3: Tích hợp Patterns
- Xem LibraryController để hiểu cách tích hợp
- Thấy cách 5 patterns làm việc cùng nhau

### Bước 4: Thực hành
- Tạo thêm loại sách mới (Factory)
- Thêm search strategy mới (Strategy)
- Thêm decorator mới cho loan (Decorator)
- Thêm observer mới (Observer)

## 🎓 Design Patterns Cheat Sheet

### Composite
- **Mục đích**: Xử lý objects và compositions đồng nhất
- **Khi nào dùng**: Cấu trúc cây (file system, UI components)
- **Components**: Component (interface), Leaf (đơn), Composite (nhiều)

### Observer
- **Mục đích**: Notify nhiều objects khi state thay đổi
- **Khi nào dùng**: Event systems, notifications
- **Components**: Subject (observable), Observer (subscriber)

### Adapter
- **Mục đích**: Convert interface này sang interface khác
- **Khi nào dùng**: Tích hợp legacy system, third-party libraries
- **Components**: Target, Adaptee, Adapter

### Singleton
- **Mục đích**: Đảm bảo chỉ 1 instance duy nhất
- **Khi nào dùng**: Database connections, configuration, logging
- **Cách implement**: Private constructor, static getInstance()

### Factory Method
- **Mục đích**: Tạo objects mà không chỉ định class cụ thể
- **Khi nào dùng**: Nhiều loại object cùng interface
- **Cách implement**: Factory class với create methods

### Strategy
- **Mục đích**: Định nghĩa family of algorithms, có thể thay đổi runtime
- **Khi nào dùng**: Nhiều cách xử lý cùng 1 task
- **Components**: Context, Strategy (interface), ConcreteStrategies

### Decorator
- **Mục đích**: Add responsibilities dynamically
- **Khi nào dùng**: Cần extend functionality mà không sửa class
- **Components**: Component, ConcreteComponent, Decorator, ConcreteDecorators

## 🛠️ Technologies

- **Spring Boot 3.2.1**: Web framework
- **Java 17**: Programming language
- **Maven**: Build tool
- **Lombok**: Reduce boilerplate code
- **Jackson**: JSON/XML processing

## 📝 Notes

- Tất cả demos chạy qua REST API, dễ dàng test bằng browser
- Output được format dạng `<pre>` để dễ đọc
- Code có nhiều comments tiếng Việt giúp hiểu rõ hơn
- Mỗi pattern có ví dụ thực tế dễ hình dung

## ✅ Checklist

- [x] Composite Pattern với 2 ví dụ
- [x] Observer Pattern với 2 ví dụ  
- [x] Adapter Pattern với XML/JSON
- [x] Singleton Pattern trong Library
- [x] Factory Method cho Books
- [x] Strategy Pattern cho Search
- [x] Decorator Pattern cho Loans
- [x] Tích hợp 5 patterns trong Library System
- [x] REST API endpoints cho tất cả demos
- [x] Home page với navigation

## 🎯 Mục tiêu học tập

Sau khi hoàn thành lab này, bạn sẽ:
- ✅ Hiểu rõ 7 design patterns quan trọng
- ✅ Biết khi nào nên dùng pattern nào
- ✅ Có thể implement patterns trong Spring Boot
- ✅ Hiểu cách tích hợp nhiều patterns cùng nhau
- ✅ Áp dụng được vào project thực tế

---

**Author**: ASD Lab  
**Version**: 1.0.0  
**Framework**: Spring Boot 3.2.1
