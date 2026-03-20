package io.github.iamnguyenvu.ex04.catalog.config;

import io.github.iamnguyenvu.ex04.catalog.model.CatalogItem;
import io.github.iamnguyenvu.ex04.catalog.repository.CatalogItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CatalogDataSeeder {

    @Bean
    CommandLineRunner seedCatalogItems(CatalogItemRepository catalogItemRepository) {
        return args -> {
            if (catalogItemRepository.count() == 0) {
                catalogItemRepository.saveAll(List.of(
                        new CatalogItem("Pizza Hai San", "Seafood pizza", 95000, true),
                        new CatalogItem("Mi Y", "Italian pasta", 85000, true),
                        new CatalogItem("Tra Chanh", "Lemon tea", 18000, true)
                ));
            }
        };
    }
}
