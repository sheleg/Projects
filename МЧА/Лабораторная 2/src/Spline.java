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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Spline extends JFrame {

    private final int FUNCTION = 0;
    private final int SPLINE = 1;

    private double begin = 1; // начало отрезка
    private double end = 2; //конец отрезка
    private double deltaForPlot = 0.5; // на графике будет отображаться отрезок (begin-delta; end+delta)

    private int param_n = 3;
    private int countOfNodesForPlot = 40;


    private Spline() throws HeadlessException {
        super("01 Spline");
        buildSpline();

        ChartPanel chartPanel = new ChartPanel(createChar());

        this.setContentPane(chartPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setVisible(true);
    }


    private double function(double x) {
        return Math.pow(2, -x) + Math.log(x + 2) - Math.cos(x);
    }

    private SplineTuple[] splines;

    private class SplineTuple {
        double x;

        double a; // значение
        double b; // производная
        double c; // вторая производная
        double d; // третья

        SplineTuple(double x, double a) {
            this.x = x;
            this.a = a;
        }
    }

    private void buildSpline() {
        splines = new SplineTuple[param_n];
        double[] x = new double[param_n];
        double[] y = new double[param_n];

        double step = (end - begin) / (param_n - 1);

        for (int i = 0; i < param_n; ++i) {
            double x_i = begin + i * step;
            x[i] = x_i;
            y[i] = function(x_i);
            splines[i] = new SplineTuple(x[i], y[i]);
        }
        splines[0].c = splines[param_n - 1].c = 0.0;

        double[] alpha = new double[param_n - 1];
        double[] beta = new double[param_n - 1];
        alpha[0] = beta[0] = 0.0;

        for (int i = 1; i < param_n - 1; ++i) {
            double A = x[i] - x[i - 1];
            double B = x[i + 1] - x[i];

            double C = 2.0 * (A + B);
            double F = 6.0 * ((y[i + 1] - y[i]) / B - (y[i] - y[i - 1]) / A);

            double z = (A * alpha[i - 1] + C);
            alpha[i] = -B / z;
            beta[i] = (F - A * beta[i - 1]) / z;
        }

        for (int i = param_n - 2; i > 0; --i) {
            splines[i].c = alpha[i] * splines[i + 1].c + beta[i];
        }

        for (int i = param_n - 1; i > 0; --i) {
            double hi = x[i] - x[i - 1];
            splines[i].d = (splines[i].c - splines[i - 1].c) / hi;
            splines[i].b = hi * (2.0 * splines[i].c + splines[i - 1].c) / 6.0 + (y[i] - y[i - 1]) / hi;
        }
    }

    private double interpolate(double x) {
        if (splines == null) {
            return 0;
        }

        int n = splines.length;
        SplineTuple s;

        if (x <= splines[0].x) {
            s = splines[0];
        } else if (x >= splines[n - 1].x) {
            s = splines[n - 1];
        } else {
            int i = 0;
            int j = n - 1;
            while (i + 1 < j) {
                int k = i + (j - i) / 2;
                if (x <= splines[k].x) {
                    j = k;
                } else {
                    i = k;
                }
            }
            s = splines[j];
        }

        double dx = x - s.x;
        return s.a + (s.b + (s.c / 2.0 + s.d * dx / 6.0) * dx) * dx;
    }


    private XYDataset createDataset() {
        double plotBegin = begin - deltaForPlot;
        double plotEnd = end + deltaForPlot;

        double step = (end - begin) / (countOfNodesForPlot - 1);
        XYSeries functionSeries = new XYSeries("function");
        XYSeries polynomialSeries = new XYSeries("spline");
        for (double i = plotBegin; i <= plotEnd; i += step) {
            functionSeries.add(i, function(i));
            polynomialSeries.add(i, interpolate(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(functionSeries);
        dataset.addSeries(polynomialSeries);
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
        renderer.setSeriesPaint(FUNCTION, new Color(114, 15, 125));
        renderer.setSeriesPaint(SPLINE, new Color(58, 165, 126));
        renderer.setSeriesStroke(FUNCTION, new BasicStroke(2f));
        renderer.setSeriesStroke(SPLINE, new BasicStroke(2f));

        double size = 3;
        double delta = size / 2.0;
        Shape shape1 = new Rectangle2D.Double(-delta, -delta, size, size);
        Shape shape2 = new Ellipse2D.Double(-delta, -delta, size, size);
        renderer.setSeriesShape(FUNCTION, shape1);
        renderer.setSeriesShape(SPLINE, shape2);

        plot.setRenderer(renderer);

        return chart;
    }

    public static void main(String[] args) {
        JFrame frame = new Spline();
    }

}