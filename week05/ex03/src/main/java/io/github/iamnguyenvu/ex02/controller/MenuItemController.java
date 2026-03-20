package io.github.iamnguyenvu.ex02.controller;

import io.github.iamnguyenvu.ex02.model.MenuItem;
import io.github.iamnguyenvu.ex02.repository.MenuItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemRepository menuItemRepository;

    public MenuItemController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping
    public List<MenuItem> getAll() {
        return menuItemRepository.findAll();
    }
}
