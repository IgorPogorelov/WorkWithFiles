import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        String[] products = {"Соль", "Вода", "Гречка"};
        int[] prices = {10, 20, 50};

        Basket basket = new Basket(products, prices);

        if (Files.exists(Path.of("basket.txt"))) {
            basket = Basket.loadFromTxtFile(new File("basket.txt"));
        }

        for (int i = 0; i < products.length; i++) {
            int num = i + 1;
            System.out.println(num + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, productCount);
            basket.saveText(new File("basket.txt"));
        }

        basket.printCart();
    }
}