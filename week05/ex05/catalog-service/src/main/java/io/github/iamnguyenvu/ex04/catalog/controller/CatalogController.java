package io.github.iamnguyenvu.ex04.catalog.controller;

import io.github.iamnguyenvu.ex04.catalog.model.CatalogItem;
import io.github.iamnguyenvu.ex04.catalog.repository.CatalogItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/items")
public class CatalogController {

    private final CatalogItemRepository catalogItemRepository;

    public CatalogController(CatalogItemRepository catalogItemRepository) {
        this.catalogItemRepository = catalogItemRepository;
    }

    @GetMapping
    public List<CatalogItem> getAllItems() {
        return catalogItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public CatalogItem getItemById(@PathVariable Long id) {
        return catalogItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catalog item not found"));
    }
}
