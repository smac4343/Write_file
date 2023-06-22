import java.io.*;
import java.util.Scanner;


public class Basket implements Serializable {
    private String[] products;
    private int[] prices;
    private int[] quantities;
    private int totalCost;

    public void saveBin(File file) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromBinFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (Basket) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Basket(new String[5], new int[5]);
        }
    }

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
            String[] products = new String[5];
            int[] quantities = new int[5];
            int[] prices = {50, 30, 60, 10, 100};
            int totalCost = 0;

            int index = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                products[index] = parts[0];
                quantities[index] = Integer.parseInt(parts[1]);
                totalCost += prices[index] * quantities[index];
                index++;
            }

            Basket basket = new Basket(products, prices);
            basket.setQuantities(quantities);
            basket.setTotalCost(totalCost);
            return basket;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Basket(new String[5], new int[5]);
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
