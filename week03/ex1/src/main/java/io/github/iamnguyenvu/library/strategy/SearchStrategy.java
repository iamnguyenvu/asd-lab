package io.github.iamnguyenvu.library.strategy;

import io.github.iamnguyenvu.library.model.Book;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * STRATEGY PATTERN - Search Algorithms
 */
public interface SearchStrategy {
    List<Book> search(Collection<Book> books, String keyword);
    String getStrategyName();
}

/**
 * Search by Title
 */
class SearchByTitle implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getStrategyName() {
        return "Search by Title";
    }
}

/**
 * Search by Author
 */
class SearchByAuthor implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getStrategyName() {
        return "Search by Author";
    }
}

/**
 * Search by Genre
 */
class SearchByGenre implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getGenre().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getStrategyName() {
        return "Search by Genre";
    }
}

/**
 * Search by ISBN
 */
class SearchByISBN implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public String getStrategyName() {
        return "Search by ISBN";
    }
}

/**
 * Advanced Search - Search across all fields
 */
class AdvancedSearch implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book -> 
                    book.getTitle().toLowerCase().contains(lowerKeyword) ||
                    book.getAuthor().toLowerCase().contains(lowerKeyword) ||
                    book.getGenre().toLowerCase().contains(lowerKeyword) ||
                    book.getIsbn().contains(keyword)
                )
                .collect(Collectors.toList());
    }

    @Override
    public String getStrategyName() {
        return "Advanced Search (All Fields)";
    }
}

/**
 * Context class - uses strategy
 */
class BookSearchContext {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
        System.out.println("🔍 Search Strategy set to: " + strategy.getStrategyName());
    }

    public List<Book> executeSearch(Collection<Book> books, String keyword) {
        if (strategy == null) {
            throw new IllegalStateException("Search strategy not set!");
        }
        return strategy.search(books, keyword);
    }
}
