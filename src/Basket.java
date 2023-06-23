import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Basket implements Serializable {
    private String[] products;
    private int[] prices;
    private int[] quantities;
    private int totalCost;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.quantities = new int[products.length];
        this.totalCost = 0;
    }

    public void addToCart(int productNum, int amount) {
        quantities[productNum - 1] += amount;
        totalCost += prices[productNum - 1] * amount;
    }

    public void printCart() {
        System.out.println("Your shopping basket:");
        for (int i = 0; i < products.length; i++) {
            if (quantities[i] > 0) {
                System.out.println(products[i] + " x " + quantities[i]);
            }
        }
        System.out.println("Total cost: " + totalCost);
    }

    public void saveTxt(File textFile) {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (int i = 0; i < products.length; i++) {
                writer.println(products[i] + "," + quantities[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
    try (Scanner scanner = new Scanner(textFile)) {
        List<String> products = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();
        int totalCost = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String product = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            int price = Integer.parseInt(parts[2]);

            products.add(product);
            quantities.add(quantity);
            prices.add(price);

            totalCost += price * quantity;
        }

        String[] productsArray = products.toArray(new String[0]);
        int[] quantitiesArray = quantities.stream().mapToInt(Integer::intValue).toArray();
        int[] pricesArray = prices.stream().mapToInt(Integer::intValue).toArray();

        Basket basket = new Basket(productsArray, pricesArray);
        basket.setQuantities(quantitiesArray);
        basket.setTotalCost(totalCost);
        return basket;
    } catch (FileNotFoundException e) {
        e.printStackTrace();
        return new Basket(new String[0], new int[0]);
    }
}

    public String[] getProducts() {
        return products;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public void setQuantities(int[] quantities) {
        this.quantities = quantities;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
