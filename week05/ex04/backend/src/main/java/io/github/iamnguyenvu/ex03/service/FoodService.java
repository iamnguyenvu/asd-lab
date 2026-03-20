package io.github.iamnguyenvu.ex03.service;

import io.github.iamnguyenvu.ex03.dto.CreateOrderRequest;
import io.github.iamnguyenvu.ex03.model.FoodItem;
import io.github.iamnguyenvu.ex03.model.FoodOrder;
import io.github.iamnguyenvu.ex03.repository.FoodItemRepository;
import io.github.iamnguyenvu.ex03.repository.FoodOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodItemRepository foodItemRepository;
    private final FoodOrderRepository foodOrderRepository;

    public FoodService(FoodItemRepository foodItemRepository, FoodOrderRepository foodOrderRepository) {
        this.foodItemRepository = foodItemRepository;
        this.foodOrderRepository = foodOrderRepository;
    }

    public List<FoodItem> getMenuItems() {
        return foodItemRepository.findAll();
    }

    public FoodOrder createOrder(CreateOrderRequest request) {
        FoodOrder order = new FoodOrder();
        order.setCustomerName(request.getCustomerName());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setItemName(request.getItemName());
        order.setQuantity(request.getQuantity());
        order.setStatus("PENDING");
        return foodOrderRepository.save(order);
    }

    public FoodOrder getOrder(Long id) {
        return foodOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public FoodOrder updateOrderStatus(Long id, String status) {
        FoodOrder order = getOrder(id);
        order.setStatus(status);
        return foodOrderRepository.save(order);
    }
}
