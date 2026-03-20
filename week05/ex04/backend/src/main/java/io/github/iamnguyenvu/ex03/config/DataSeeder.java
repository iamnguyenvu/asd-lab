package io.github.iamnguyenvu.ex03.config;

import io.github.iamnguyenvu.ex03.model.FoodItem;
import io.github.iamnguyenvu.ex03.repository.FoodItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedFoodItems(FoodItemRepository foodItemRepository) {
        return args -> {
            if (foodItemRepository.count() == 0) {
                foodItemRepository.saveAll(List.of(
                        new FoodItem("Com Ga Nuong", "Grilled chicken rice", 45000, true),
                        new FoodItem("Bun Bo Hue", "Spicy beef noodle soup", 50000, true),
                        new FoodItem("Che Thai", "Sweet dessert", 25000, true)
                ));
            }
        };
    }
}
