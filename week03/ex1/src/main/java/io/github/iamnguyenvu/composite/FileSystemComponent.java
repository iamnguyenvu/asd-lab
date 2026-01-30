package io.github.iamnguyenvu.composite;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * COMPOSITE PATTERN - File System
 * 
 * Component: Interface chung cho File và Directory
 */
public abstract class FileSystemComponent {
    @Getter
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract long getSize();
    public abstract void display(int indent);
    
    // Default implementation cho các phương thức composite
    public void add(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot add to a file");
    }
    
    public void remove(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a file");
    }
    
    public List<FileSystemComponent> getChildren() {
        throw new UnsupportedOperationException("File has no children");
    }
}

/**
 * LEAF - File
 */
@Getter
class File extends FileSystemComponent {
    private long size; // in KB

    public File(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void display(int indent) {
        String prefix = "  ".repeat(indent);
        System.out.println(prefix + "📄 " + name + " (" + size + " KB)");
    }
}

/**
 * COMPOSITE - Directory
 */
class Directory extends FileSystemComponent {
    private List<FileSystemComponent> children = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    @Override
    public void add(FileSystemComponent component) {
        children.add(component);
    }

    @Override
    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public List<FileSystemComponent> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public long getSize() {
        return children.stream()
                .mapToLong(FileSystemComponent::getSize)
                .sum();
    }

    @Override
    public void display(int indent) {
        String prefix = "  ".repeat(indent);
        System.out.println(prefix + "📁 " + name + "/ (" + getSize() + " KB)");
        children.forEach(child -> child.display(indent + 1));
    }
}
