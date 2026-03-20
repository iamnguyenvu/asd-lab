package io.github.iamnguyenvu.ex04.catalog.repository;

import io.github.iamnguyenvu.ex04.catalog.model.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {
}
