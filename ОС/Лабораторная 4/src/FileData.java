import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladasheleg on 19.04.17.
 */
public class FileData {
    int m;
    int n;
    int k;

    List<Double> prices;

    private int BULL_DAY = 1;
    private int BEAR_DAY = 0;


    public FileData() {
        m = 0;
        n = 0;
        k = 0;
        prices = new ArrayList<>();
    }

    public FileData(int m, int n, int k, List<Double> prices) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.prices = prices;
    }

    FileData getDataFromFile(String path) {
        return getDataFromFile(new File(path));
    }

    FileData getDataFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            String[] arrayOfData = line.split(" ");

            m = Integer.parseInt(arrayOfData[0]);
            n = Integer.parseInt(arrayOfData[1]);
            k = Integer.parseInt(arrayOfData[2]);

            prices.add(0.0);

            while ((line = reader.readLine()) != null) {
                prices.add(Double.parseDouble(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return this;
    }

    public String easyTraiding() {
        List<Integer> daysDescriptions = new ArrayList<>();
        daysDescriptions.add(0);

        for (int i = 1; i < k + 1; ++i) {
            daysDescriptions.add(getDescriptionOfDay(i, n, m, daysDescriptions));
        }

        String answer = "";

        for (int i = 1; i < k + 1; ++i) {
            if (daysDescriptions.get(i) == BULL_DAY) {
                answer += "BUY ON DAY " + i + "\n";
                while (daysDescriptions.get(++i) != BEAR_DAY) {}
                answer += "SELL ON DAY " + i + "\n";
                prices.clear();
                return answer;
            }
        }
        return answer;
    }

    int getDescriptionOfDay(int i, int n, int m, List<Integer> daysDescriptions) {
        if (i < n) {
            return -2;
        }

        double n_average = average(i, n);
        double m_average = average(i, m);


        if (n_average < m_average && (i == n || daysDescriptions.get(i - 1) == BEAR_DAY || i == k)) {
            return BULL_DAY;
        }

        if (n_average > m_average && (i == n || daysDescriptions.get(i - 1) == BULL_DAY || i == k)) {
            return BEAR_DAY;
        }
        return -2;
    }

    double average(int day, int count) {
        double tempPrice = 0;
        for (int i = 0; i < count; ++i) {
            tempPrice += prices.get(day - i);
        }
        return tempPrice / count;
    }
}
