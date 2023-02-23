import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public void saveJSON(File jsonFile) {
        JSONObject obj = new JSONObject();

        JSONArray productsJSON =new JSONArray();
        Collections.addAll(productsJSON, products);
        obj.put("products", productsJSON);

        JSONArray pricesJSON =new JSONArray();
        for (int price : prices) {
            pricesJSON.add(price);
        }
        obj.put("prices", pricesJSON);

        JSONArray countsJSON =new JSONArray();
        for (int count : counts) {
            countsJSON.add(count);
        }
        obj.put("counts", countsJSON);

        try (FileWriter file = new FileWriter("basket.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Basket loadFromJSON(File textFile) throws Exception {
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("basket.json"));
            JSONObject jsonObject = (JSONObject) obj;

            List<String> productsTmp2 = new ArrayList<>();
            JSONArray productsTmp1 = (JSONArray) jsonObject.get("products");
            for (Object product : productsTmp1) {
                productsTmp2.add((String) product);
            }
            String[] products = new String[productsTmp2.size()];
            for (int i = 0; i < productsTmp2.size(); i++) {
                products[i] = productsTmp2.get(i);
            }

            List<Integer> pricesTmp2 = new ArrayList<>();
            JSONArray pricesTmp1 = (JSONArray) jsonObject.get("prices");
            for (Object price : pricesTmp1) {
                pricesTmp2.add(Math.toIntExact((Long) price));
            }
            int[] prices = new int[pricesTmp2.size()];
            for (int i = 0; i < pricesTmp2.size(); i++) {
                prices[i] = pricesTmp2.get(i);
            }

            List<Integer> countsTmp2 = new ArrayList<>();
            JSONArray countsTmp1 = (JSONArray) jsonObject.get("counts");
            for (Object count : countsTmp1) {
                countsTmp2.add(Math.toIntExact((Long) count));
            }
            int[] counts = new int[countsTmp2.size()];
            for (int i = 0; i < countsTmp2.size(); i++) {
                counts[i] = countsTmp2.get(i);
            }

            Basket basket = new Basket(products, prices);
            basket.counts = counts;
            return basket;

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }
}