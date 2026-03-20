package io.github.iamnguyenvu.ex02.repository;

import io.github.iamnguyenvu.ex02.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
