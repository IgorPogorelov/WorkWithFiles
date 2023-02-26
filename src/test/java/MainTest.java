import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    String[] products = {"Соль", "Вода", "Гречка"};
    int[] prices = {10, 20, 50};

    Basket basket = new Basket(products, prices);

    @Test
    void checkAddToCart() {
        int[] resCounts = {1, 0, 0};
        String[] testParts = "1 1".split(" ");
        int testProductNumber = Integer.parseInt(testParts[0]) - 1;
        int testProductCount = Integer.parseInt(testParts[1]);
        basket.addToCart(testProductNumber, testProductCount);

        Assertions.assertArrayEquals(resCounts, basket.getCounts());
    }

    @Test
    void checkSaveTxt() throws IOException {
        int[] testCounts = {10, 0, 0};
        String[] testParts = "1 10".split(" ");
        int testProductNumber = Integer.parseInt(testParts[0]) - 1;
        int testProductCount = Integer.parseInt(testParts[1]);
        basket.addToCart(testProductNumber, testProductCount);
        basket.saveText(new File("testBasket.txt"));
        String[] resProducts = null;
        int[] resPrices = null;
        int[] resCounts = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(new File("testBasket.txt")))) {
            resProducts = reader.readLine().split(" ");
            resPrices = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            resCounts = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        Assertions.assertArrayEquals(products, resProducts);
        Assertions.assertArrayEquals(prices, resPrices);
        Assertions.assertArrayEquals(testCounts, resCounts);

    }

    @Test
    void testLoadFromTxt() throws Exception {
        basket = Basket.loadFromTxtFile(new File("testBasket.txt"));
        int[] testCounts = {10, 0, 0};
        int[] result = basket.getCounts();

        Assertions.assertArrayEquals(testCounts, result);
    }
}