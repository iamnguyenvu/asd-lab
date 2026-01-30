package io.github.iamnguyenvu.observer;

import lombok.Getter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OBSERVER PATTERN - Stock Market
 */

// Subject Interface
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// Observer Interface
interface Observer {
    void update(Stock stock);
    String getName();
}

/**
 * CONCRETE SUBJECT - Stock
 */
@Getter
public class Stock implements Subject {
    private String symbol;
    private double price;
    private List<Observer> observers = new ArrayList<>();

    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("📊 " + observer.getName() + " đã đăng ký theo dõi " + symbol);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("📊 " + observer.getName() + " đã hủy theo dõi " + symbol);
    }

    @Override
    public void notifyObservers() {
        System.out.println("\n🔔 Thông báo giá cổ phiếu " + symbol + ": $" + price);
        observers.forEach(observer -> observer.update(this));
    }

    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price = newPrice;
        double change = ((newPrice - oldPrice) / oldPrice * 100);
        String arrow = newPrice > oldPrice ? "📈" : "📉";

        System.out.println(String.format("\n%s %s: $%.2f → $%.2f (%+.2f%%)",
                arrow, symbol, oldPrice, newPrice, change));
        notifyObservers();
    }
}

/**
 * CONCRETE OBSERVER - Investor
 */
@Getter
class Investor implements Observer {
    private String name;
    private String strategy; // aggressive, conservative, balanced
    private Map<String, HoldingInfo> portfolio = new HashMap<>();

    public Investor(String name, String strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    @Override
    public void update(Stock stock) {
        String symbol = stock.getSymbol();
        double currentPrice = stock.getPrice();

        System.out.println("  👤 " + name + " nhận thông báo " + symbol + ": $" + currentPrice);
        reactToChange(stock);
    }

    private void reactToChange(Stock stock) {
        String symbol = stock.getSymbol();
        double currentPrice = stock.getPrice();

        if (!portfolio.containsKey(symbol)) {
            System.out.println("     👀 " + name + ": Theo dõi " + symbol + " (" + strategy + ")");
            return;
        }

        HoldingInfo holding = portfolio.get(symbol);
        double profitPercent = ((currentPrice - holding.avgPrice) / holding.avgPrice * 100);

        String action = decideAction(profitPercent);
        System.out.println("     " + getActionIcon(action) + " " + name + ": " + 
                         getActionText(action, symbol) + " (" + strategy + ")");
    }

    private String decideAction(double profitPercent) {
        if (strategy.equals("aggressive")) {
            if (profitPercent > 5) return "SELL";
            if (profitPercent < -3) return "BUY_MORE";
        } else if (strategy.equals("conservative")) {
            if (profitPercent > 10) return "SELL";
            if (profitPercent < -1) return "HOLD";
        } else {
            if (profitPercent > 8) return "SELL";
            if (profitPercent < -2) return "BUY_MORE";
        }
        return "HOLD";
    }

    private String getActionIcon(String action) {
        return switch (action) {
            case "BUY_MORE" -> "✅";
            case "SELL" -> "💰";
            case "HOLD" -> "🤝";
            default -> "👀";
        };
    }

    private String getActionText(String action, String symbol) {
        return switch (action) {
            case "BUY_MORE" -> "Mua thêm " + symbol;
            case "SELL" -> "Bán " + symbol + " để chốt lời";
            case "HOLD" -> "Giữ " + symbol;
            default -> "Theo dõi " + symbol;
        };
    }

    public void buy(String symbol, int shares, double price) {
        if (portfolio.containsKey(symbol)) {
            HoldingInfo holding = portfolio.get(symbol);
            int totalShares = holding.shares + shares;
            double totalCost = (holding.shares * holding.avgPrice) + (shares * price);
            holding.shares = totalShares;
            holding.avgPrice = totalCost / totalShares;
        } else {
            portfolio.put(symbol, new HoldingInfo(shares, price));
        }
        System.out.println("💵 " + name + " mua " + shares + " cổ phiếu " + symbol + " @ $" + price);
    }

    @Getter
    static class HoldingInfo {
        int shares;
        double avgPrice;

        HoldingInfo(int shares, double avgPrice) {
            this.shares = shares;
            this.avgPrice = avgPrice;
        }
    }
}
