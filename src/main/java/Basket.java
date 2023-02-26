import java.io.*;
import java.util.Arrays;

public class Basket {
    private String[] products;
    private int[] prices;
    private int[] counts;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.counts = new int[products.length];
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getCounts() {
        return counts;
    }

    public void addToCart(int productNum, int amount) {
        counts[productNum] += amount;
    }

    public void printCart() {
        int sumProducts = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println(products[i] + " - " + prices[i] + " руб/шт " + "Итого: " + (prices[i] * counts[i] + " руб"));
                sumProducts += prices[i] * counts[i];
            }
        }
        System.out.println("Итого: " + sumProducts + " руб");
    }

    public void saveText(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : products) {
                out.print(product + " ");
            }
            out.println();

            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();

            for (int count : counts) {
                out.print(count + " ");
            }
            out.println();
        }
    }

    static Basket loadFromTxtFile(File textFile) throws Exception {
        try(BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] products = reader.readLine().split(" ");
            int[] prices = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] counts = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            Basket basket = new Basket(products, prices);
            basket.counts = counts;
            return basket;
        }
    }
}