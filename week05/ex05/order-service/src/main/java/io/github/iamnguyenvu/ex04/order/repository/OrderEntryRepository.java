package io.github.iamnguyenvu.ex04.order.repository;

import io.github.iamnguyenvu.ex04.order.model.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntryRepository extends JpaRepository<OrderEntry, Long> {
}
