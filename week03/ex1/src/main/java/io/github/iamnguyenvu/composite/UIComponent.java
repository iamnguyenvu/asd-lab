package io.github.iamnguyenvu.composite;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * COMPOSITE PATTERN - UI Components
 */
public abstract class UIComponent {
    @Getter
    protected String name;

    public UIComponent(String name) {
        this.name = name;
    }

    public abstract void render(int indent);

    // Default implementation
    public void add(UIComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(UIComponent component) {
        throw new UnsupportedOperationException();
    }
}

/**
 * LEAF - Button
 */
@Getter
class Button extends UIComponent {
    private String label;

    public Button(String name, String label) {
        super(name);
        this.label = label;
    }

    @Override
    public void render(int indent) {
        String prefix = "  ".repeat(indent);
        System.out.println(prefix + "<Button name=\"" + name + "\" label=\"" + label + "\" />");
    }

    public void click() {
        System.out.println("🖱️  Button \"" + label + "\" clicked!");
    }
}

/**
 * LEAF - TextBox
 */
@Getter
class TextBox extends UIComponent {
    private String placeholder;
    private String value;

    public TextBox(String name, String placeholder) {
        super(name);
        this.placeholder = placeholder;
        this.value = "";
    }

    @Override
    public void render(int indent) {
        String prefix = "  ".repeat(indent);
        System.out.println(prefix + "<TextBox name=\"" + name + 
                         "\" placeholder=\"" + placeholder + "\" />");
    }

    public void setValue(String value) {
        this.value = value;
        System.out.println("📝 TextBox \"" + name + "\" value = \"" + value + "\"");
    }
}

/**
 * COMPOSITE - Panel
 */
@Getter
class Panel extends UIComponent {
    private List<UIComponent> children = new ArrayList<>();

    public Panel(String name) {
        super(name);
    }

    @Override
    public void add(UIComponent component) {
        children.add(component);
    }

    @Override
    public void remove(UIComponent component) {
        children.remove(component);
    }

    @Override
    public void render(int indent) {
        String prefix = "  ".repeat(indent);
        System.out.println(prefix + "<Panel name=\"" + name + "\">");
        children.forEach(child -> child.render(indent + 1));
        System.out.println(prefix + "</Panel>");
    }
}

/**
 * COMPOSITE - Dialog
 */
@Getter
class Dialog extends UIComponent {
    private String title;
    private List<UIComponent> children = new ArrayList<>();

    public Dialog(String name, String title) {
        super(name);
        this.title = title;
    }

    @Override
    public void add(UIComponent component) {
        children.add(component);
    }

    @Override
    public void remove(UIComponent component) {
        children.remove(component);
    }

    @Override
    public void render(int indent) {
        String prefix = "  ".repeat(indent);
        System.out.println(prefix + "<Dialog name=\"" + name + "\" title=\"" + title + "\">");
        children.forEach(child -> child.render(indent + 1));
        System.out.println(prefix + "</Dialog>");
    }

    public void show() {
        System.out.println("\n🪟 Opening Dialog: \"" + title + "\"");
        System.out.println("═".repeat(60));
        render(0);
        System.out.println("═".repeat(60));
    }
}
