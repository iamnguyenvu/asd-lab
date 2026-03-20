package io.github.iamnguyenvu.ex02.config;

import io.github.iamnguyenvu.ex02.model.MenuItem;
import io.github.iamnguyenvu.ex02.repository.MenuItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedMenuData(MenuItemRepository menuItemRepository) {
        return args -> {
            if (menuItemRepository.count() == 0) {
                menuItemRepository.saveAll(List.of(
                        new MenuItem("Pho Bo", "Main", 55000),
                        new MenuItem("Banh Mi Thit", "Main", 25000),
                        new MenuItem("Tra Dao", "Drink", 20000)
                ));
            }
        };
    }
}
