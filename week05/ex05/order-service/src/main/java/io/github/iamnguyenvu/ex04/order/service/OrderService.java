package io.github.iamnguyenvu.ex04.order.service;

import io.github.iamnguyenvu.ex04.order.dto.CreateOrderRequest;
import io.github.iamnguyenvu.ex04.order.model.OrderEntry;
import io.github.iamnguyenvu.ex04.order.repository.OrderEntryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderEntryRepository orderEntryRepository;
    private final RestTemplate restTemplate;

    @Value("${catalog.service.base-url}")
    private String catalogServiceBaseUrl;

    public OrderService(OrderEntryRepository orderEntryRepository, RestTemplate restTemplate) {
        this.orderEntryRepository = orderEntryRepository;
        this.restTemplate = restTemplate;
    }

    public OrderEntry createOrder(CreateOrderRequest request) {
        Map<?, ?> item = restTemplate.getForObject(catalogServiceBaseUrl + "/" + request.getItemId(), Map.class);
        if (item == null || item.get("id") == null) {
            throw new IllegalArgumentException("Catalog item not found");
        }

        OrderEntry order = new OrderEntry();
        order.setCustomerName(request.getCustomerName());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setItemId(request.getItemId());
        order.setItemName(String.valueOf(item.get("name")));
        order.setQuantity(request.getQuantity());
        order.setStatus("PENDING");

        return orderEntryRepository.save(order);
    }

    public List<OrderEntry> getOrders() {
        return orderEntryRepository.findAll();
    }

    public OrderEntry getOrder(Long id) {
        return orderEntryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public OrderEntry updateStatus(Long id, String status) {
        OrderEntry order = getOrder(id);
        order.setStatus(status);
        return orderEntryRepository.save(order);
    }
}
