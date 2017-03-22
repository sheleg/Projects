/**
 * Created by vladasheleg on 29.11.16.
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class AppletTest extends Applet {

    public static Dimension dimension = new Dimension(675, 650);

    String param = "param_";
    ArrayList<Point2D> points = new ArrayList<>();

    @Override
    public void init() {
        String str, date[];
        Double humidity, temperature;

        int i = 1;
        while ((str = getParameter(param + i++)) != null) {
            date = str.split(" ");
            humidity = Double.parseDouble(date[0]);
            temperature = Double.parseDouble(date[1]);

            if (humidity <= 100 && humidity >= 0 && temperature >= -70 && temperature <= 70) {
                points.add(new Point2D.Double(temperature, humidity));
            }
        }

        points.sort((o1, o2) -> new Double(o1.getX()).compareTo(o2.getX()));

        add(new Plot(points));
    }
}