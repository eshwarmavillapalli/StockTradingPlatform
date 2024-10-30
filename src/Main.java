import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Map<String, Double> stockPrices = new HashMap<>();
    private static final Map<String, Integer> portfolio = new HashMap<>();
    private static double balance = 10000.00;  // Starting balance

    static {
        // Initializing stock prices for the platform
        stockPrices.put("AAPL", 150.0);  // Apple
        stockPrices.put("GOOGL", 2800.0);  // Alphabet
        stockPrices.put("AMZN", 3400.0);  // Amazon
        stockPrices.put("TSLA", 800.0);   // Tesla
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Stock Trading Platform");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock(scanner);
                    break;
                case 3:
                    sellStock(scanner);
                    break;
                case 4:
                    viewPortfolio();
                    break;
                case 5:
                    System.out.println("Exiting the platform. Thank you for trading!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
                    break;
            }
        }
    }

    // Method to display the market data
    private static void viewMarketData() {
        System.out.println("\nMarket Data:");
        for (Map.Entry<String, Double> entry : stockPrices.entrySet()) {
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        }
    }

    // Method to handle buying stocks
    private static void buyStock(Scanner scanner) {
        System.out.print("\nEnter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        if (!stockPrices.containsKey(symbol)) {
            System.out.println("Stock not found.");
            return;
        }
        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();
        double cost = stockPrices.get(symbol) * quantity;

        if (cost > balance) {
            System.out.println("Insufficient balance to buy " + quantity + " shares of " + symbol);
        } else {
            balance -= cost;
            portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
            System.out.println("Purchased " + quantity + " shares of " + symbol + " for $" + cost);
        }
    }

    // Method to handle selling stocks
    private static void sellStock(Scanner scanner) {
        System.out.print("\nEnter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) == 0) {
            System.out.println("You don't own any shares of " + symbol);
            return;
        }
        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();
        int currentShares = portfolio.get(symbol);

        if (quantity > currentShares) {
            System.out.println("You only own " + currentShares + " shares of " + symbol);
        } else {
            double saleAmount = stockPrices.get(symbol) * quantity;
            balance += saleAmount;
            portfolio.put(symbol, currentShares - quantity);
            System.out.println("Sold " + quantity + " shares of " + symbol + " for $" + saleAmount);
        }
    }

    // Method to display the portfolio
    private static void viewPortfolio() {
        System.out.println("\nPortfolio:");
        if (portfolio.isEmpty() || portfolio.values().stream().allMatch(shares -> shares == 0)) {
            System.out.println("No stocks in portfolio.");
        } else {
            double totalValue = 0;
            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                int shares = entry.getValue();
                if (shares > 0) {
                    double stockValue = shares * stockPrices.get(entry.getKey());
                    totalValue += stockValue;
                    System.out.println(entry.getKey() + ": " + shares + " shares, Value: $" + stockValue);
                }
            }
            System.out.println("Total Portfolio Value: $" + totalValue);
            System.out.println("Available Balance: $" + balance);
        }
    }
}