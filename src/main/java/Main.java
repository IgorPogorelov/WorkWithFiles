import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String loadEnabled = null;
        String loadFileName = null;
        String loadFormat = null;
        String saveEnabled = null;
        String saveFileName = null;
        String saveFormat = null;
        String logEnabled = null;
        String logFileName = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        Node nodeShop = doc.getFirstChild();
        NodeList nodeList = nodeShop.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (Node.ELEMENT_NODE != nodeList.item(i).getNodeType()) {
                continue;
            }

            Element element = (Element) nodeList.item(i);
            NodeList childNodeList = element.getChildNodes();
            if (element.getTagName().equals("load")) {
                for (int j = 0; j < childNodeList.getLength(); j++) {
                    if (Node.ELEMENT_NODE != childNodeList.item(j).getNodeType()) {
                        continue;
                    }
                    Element childNodeListElement = (Element) childNodeList.item(j);
                    switch (childNodeListElement.getTagName()) {
                        case "enabled" -> {
                            loadEnabled = childNodeListElement.getTextContent();
                        }
                        case "fileName" -> {
                            loadFileName = childNodeListElement.getTextContent();
                        }
                        case "format" -> {
                            loadFormat = childNodeListElement.getTextContent();
                        }
                    }
                }
            }
        }

        String[] products = {"Соль", "Вода", "Гречка"};
        int[] prices = {10, 20, 50};

        Basket basket = null;

        if (loadEnabled.equals("true")) {
            if (loadFormat.equals("json")) {
                basket = Basket.loadFromJSON(new File(loadFileName));
            } else if (loadFormat.equals("text")) {
                basket = Basket.loadFromTxtFile(new File(loadFileName));
            }
        }
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < products.length; i++) {
            int num = i + 1;
            System.out.println(num + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        ClientLog clientLog = new ClientLog();

        while (true) {

            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }

            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);

            clientLog.log(productNumber + 1, productCount);

            basket.addToCart(productNumber, productCount);

        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (Node.ELEMENT_NODE != nodeList.item(i).getNodeType()) {
                continue;
            }

            Element element = (Element) nodeList.item(i);
            NodeList childNodeList = element.getChildNodes();
            if (element.getTagName().equals("save")) {
                for (int j = 0; j < childNodeList.getLength(); j++) {
                    if (Node.ELEMENT_NODE != childNodeList.item(j).getNodeType()) {
                        continue;
                    }
                    Element childNodeListElement = (Element) childNodeList.item(j);
                    switch (childNodeListElement.getTagName()) {
                        case "enabled" -> {
                            saveEnabled = childNodeListElement.getTextContent();
                        }
                        case "fileName" -> {
                            saveFileName = childNodeListElement.getTextContent();
                        }
                        case "format" -> {
                            saveFormat = childNodeListElement.getTextContent();
                        }
                    }
                }
            }
        }

        if (saveEnabled.equals("true")) {
            if (saveFormat.equals("json")) {
                basket.saveJSON(new File(saveFileName));
            } else if (saveFormat.equals("text")) {
                basket.saveText(new File(saveFileName));
            }
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (Node.ELEMENT_NODE != nodeList.item(i).getNodeType()) {
                continue;
            }

            Element element = (Element) nodeList.item(i);
            NodeList childNodeList = element.getChildNodes();
            if (element.getTagName().equals("log")) {
                for (int j = 0; j < childNodeList.getLength(); j++) {
                    if (Node.ELEMENT_NODE != childNodeList.item(j).getNodeType()) {
                        continue;
                    }
                    Element childNodeListElement = (Element) childNodeList.item(j);
                    switch (childNodeListElement.getTagName()) {
                        case "enabled" -> {
                            logEnabled = childNodeListElement.getTextContent();
                        }
                        case "fileName" -> {
                            logFileName = childNodeListElement.getTextContent();
                        }
                    }
                }
            }
        }

        if (logEnabled.equals("true")) {
            clientLog.exportAsCSV(new File(logFileName));
        }
        basket.printCart();
    }
}
