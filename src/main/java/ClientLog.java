import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {

    private List<String[]> listLog = new ArrayList<>();

    public void log (int productNum, int amount) {
        String [] logsTemp = {String.valueOf(productNum), String.valueOf(amount)};
        listLog.add(logsTemp);
    }

    public void exportAsCSV (File txtFile) {
        String[] header = "productNum,amount".split(",");
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            writer.writeNext(header);
            writer.writeAll(listLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}