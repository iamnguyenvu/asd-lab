package io.github.iamnguyenvu.ex03.controller;

import io.github.iamnguyenvu.ex03.dto.CreateOrderRequest;
import io.github.iamnguyenvu.ex03.model.FoodItem;
import io.github.iamnguyenvu.ex03.model.FoodOrder;
import io.github.iamnguyenvu.ex03.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/items")
    public List<FoodItem> getItems() {
        return foodService.getMenuItems();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public FoodOrder createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return foodService.createOrder(request);
    }

    @GetMapping("/orders/{id}")
    public FoodOrder getOrder(@PathVariable Long id) {
        return foodService.getOrder(id);
    }

    @PatchMapping("/orders/{id}/status")
    public FoodOrder updateStatus(@PathVariable Long id, @RequestParam String status) {
        return foodService.updateOrderStatus(id, status);
    }
}
