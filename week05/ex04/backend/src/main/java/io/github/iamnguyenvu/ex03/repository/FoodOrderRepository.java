package io.github.iamnguyenvu.ex03.repository;

import io.github.iamnguyenvu.ex03.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
}
