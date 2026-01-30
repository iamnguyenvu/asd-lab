package io.github.iamnguyenvu.observer;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN - Task Management
 */

@Getter
public class Task implements Subject {
    private String id;
    private String title;
    private String description;
    private String status; // TODO, IN_PROGRESS, IN_REVIEW, DONE
    private String assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Observer> observers = new ArrayList<>();

    public Task(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "TODO";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("📌 " + observer.getName() + " đã theo dõi task: " + title);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("📌 " + observer.getName() + " đã hủy theo dõi task: " + title);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }

    public void setStatus(String newStatus) {
        String oldStatus = this.status;
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();

        System.out.println("\n🔔 Task Update: \"" + title + "\"");
        System.out.println("   Status Changed: " + oldStatus + " → " + newStatus);
        notifyObservers();
    }

    public void setAssignee(String newAssignee) {
        String oldAssignee = this.assignee != null ? this.assignee : "Unassigned";
        this.assignee = newAssignee;
        this.updatedAt = LocalDateTime.now();

        System.out.println("\n🔔 Task Update: \"" + title + "\"");
        System.out.println("   Assignee Changed: " + oldAssignee + " → " + newAssignee);
        notifyObservers();
    }
}

@Getter
class TeamMember implements Observer {
    private String name;
    private String role; // Developer, QA, Manager, Designer
    private List<Notification> notifications = new ArrayList<>();

    public TeamMember(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public void update(Object subject) {
        if (subject instanceof Task task) {
            Notification notification = new Notification(
                LocalDateTime.now(),
                task.getId(),
                task.getTitle(),
                "Task updated: " + task.getStatus()
            );
            notifications.add(notification);

            System.out.println("  📢 " + name + " (" + role + "): Nhận thông báo");
            reactBasedOnRole(task);
        }
    }

    private void reactBasedOnRole(Task task) {
        switch (role) {
            case "Manager":
                if ("DONE".equals(task.getStatus())) {
                    System.out.println("     ✅ Manager will review completed task");
                }
                break;
            case "Developer":
                if (name.equals(task.getAssignee())) {
                    System.out.println("     🔨 Developer will start working on task");
                }
                break;
            case "QA":
                if ("IN_REVIEW".equals(task.getStatus())) {
                    System.out.println("     🧪 QA will start testing");
                }
                break;
        }
    }

    @Getter
    static class Notification {
        LocalDateTime timestamp;
        String taskId;
        String taskTitle;
        String message;

        Notification(LocalDateTime timestamp, String taskId, String taskTitle, String message) {
            this.timestamp = timestamp;
            this.taskId = taskId;
            this.taskTitle = taskTitle;
            this.message = message;
        }
    }
}
