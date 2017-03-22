import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Lagrange extends JFrame {

    private double start = 0;
    private double end = 10;


    private Lagrange() throws HeadlessException {
        super("01 Lagrange");

        ChartPanel chartPanel = new ChartPanel(createChar());

        this.setContentPane(chartPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setVisible(true);
    }

    private double function(double x) {
        return Math.pow(2, -x) + Math.log(x + 2) - Math.cos(x);
    }

    private double polynomial(double x) {
        double res = 0;

        int param_n = 4;
        double step = (end - start) / param_n;

        for (int i = 0; i <= param_n; i++) {
            double x_i = start + i * step;
            double sum = function(x_i);

            for (int j = 0; j <= param_n; j++) {
                if (j != i) {
                    double x_j = start + j * step;
                    sum *= (x - x_j) / (i - j);
                }
            }
            res += sum;
        }

        res /= Math.pow(step, param_n);

        return res;
    }

    private XYDataset createDataset () {
        double deltaForPlot = 0;
        double plotStart = start - deltaForPlot;
        double plotEnd = end + deltaForPlot;
        int countOfDotesForPlot = 40;

        double step = (end - start) / (countOfDotesForPlot - 1);
        XYSeries functionArray = new XYSeries("function");
        XYSeries polynomialArray = new XYSeries("polynomial");
        for (double i = plotStart; i <= plotEnd; i += step) {
            functionArray.add(i, function(i));
            polynomialArray.add(i, polynomial(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(functionArray);
        dataset.addSeries(polynomialArray);
        return dataset;
    }

    private JFreeChart createChar() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                "x", "y",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, false, false);


        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        int FUNCTION = 0;
        renderer.setSeriesPaint(FUNCTION, new Color(78, 176, 61));
        int POLYNOMIAL = 1;
        renderer.setSeriesPaint(POLYNOMIAL, new Color(36, 65, 165));
        renderer.setSeriesStroke(FUNCTION, new BasicStroke(2f));
        renderer.setSeriesStroke(POLYNOMIAL, new BasicStroke(2f));

        plot.setRenderer(renderer);

        return chart;
    }

    public static void main(String[] args) {
        new Lagrange();
    }

}
