import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] products = {"Milk", "Bread", "Butter", "Eggs", "Ham"};
        int[] prices = {50, 30, 60, 10, 100};
        File file = new File("basket.bin");
        Basket basket = Basket.loadFromBinFile(file);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Available products:");
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ". " + products[i] + " - " + prices[i] + " per unit");
            }

            System.out.println("Enter product number and quantity (e.g. 3 2 for 2 units of product #3), or 'end' to finish shopping:");
            String input = scanner.nextLine();

            if ("end".equals(input)) {
                basket.saveBin(file);
                break;
            }

            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]);
            int quantity = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, quantity);
        }

        basket.printCart();
    }
}