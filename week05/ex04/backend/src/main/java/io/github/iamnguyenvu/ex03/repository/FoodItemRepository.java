package io.github.iamnguyenvu.ex03.repository;

import io.github.iamnguyenvu.ex03.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
