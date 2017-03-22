import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by vladasheleg on 30.11.16.
 */
public class Plot extends Canvas {
    public static final int X_AXIS_START_COORDINATE = 50;
    public static final int X_AXIS_END_COORDINATE = 600;
    public static final int X_AXIS_Y_STATIC = 600;
    public static final int X_AXIS_LENGTH = X_AXIS_END_COORDINATE - X_AXIS_START_COORDINATE;


    public static final int Y_AXIS_START_COORDINATE = 50;
    public static final int Y_AXIS_END_COORDINATE = 600;
    public static final int Y_AXIS_X_STATIC = 305;
    public static final int Y_AXIS_LENGTH = Y_AXIS_END_COORDINATE - Y_AXIS_START_COORDINATE;

    public static final int ARROW_HEIGHT = 10;
    public static final int ARROW_WIDTH = 5;

    public static final Point2D CENTRE = new Point2D.Double(305.0, 600.0);

    ArrayList<Point2D> points = new ArrayList<>();

    public Plot(ArrayList<Point2D> points) {
        this.points = points;
        setBackground(Color.LIGHT_GRAY);
        setMaximumSize(AppletTest.dimension);
        setBounds(0, 0, AppletTest.dimension.width, AppletTest.dimension.height);
    }

    @Override
    public void paint(Graphics g) {
        int xCoordinateNumber = 15;
        int yCoordinateNumber = 21;
        int yLength = (Y_AXIS_END_COORDINATE - Y_AXIS_START_COORDINATE) / yCoordinateNumber;
        int xLength = (X_AXIS_END_COORDINATE - X_AXIS_START_COORDINATE) / xCoordinateNumber;


        g.drawLine(X_AXIS_START_COORDINATE, X_AXIS_Y_STATIC, X_AXIS_END_COORDINATE, X_AXIS_Y_STATIC);
        g.drawLine(X_AXIS_END_COORDINATE, Y_AXIS_END_COORDINATE,
                X_AXIS_END_COORDINATE - ARROW_HEIGHT, Y_AXIS_END_COORDINATE + ARROW_WIDTH);
        g.drawLine(X_AXIS_END_COORDINATE, Y_AXIS_END_COORDINATE,
                X_AXIS_END_COORDINATE - ARROW_HEIGHT, Y_AXIS_END_COORDINATE - ARROW_WIDTH);

        g.drawLine(Y_AXIS_X_STATIC, Y_AXIS_START_COORDINATE, Y_AXIS_X_STATIC, Y_AXIS_END_COORDINATE);
        g.drawLine(Y_AXIS_X_STATIC, Y_AXIS_START_COORDINATE,
                Y_AXIS_X_STATIC + ARROW_WIDTH, Y_AXIS_START_COORDINATE + ARROW_HEIGHT);
        g.drawLine(Y_AXIS_X_STATIC, Y_AXIS_START_COORDINATE,
                Y_AXIS_X_STATIC - ARROW_WIDTH, Y_AXIS_START_COORDINATE + ARROW_HEIGHT);

        g.drawString("Temperature", X_AXIS_END_COORDINATE - 4, Y_AXIS_END_COORDINATE + 15);
        g.drawString("Humidity", Y_AXIS_X_STATIC + 4, Y_AXIS_START_COORDINATE + 4);

        for (int i = 1; i < yCoordinateNumber; i++) {
            g.drawString("" + (i * 5), Y_AXIS_X_STATIC + 4, Y_AXIS_END_COORDINATE - (yLength * i));
            g.drawLine(Y_AXIS_X_STATIC - 3, Y_AXIS_END_COORDINATE - (yLength * i),
                    Y_AXIS_X_STATIC + 3, Y_AXIS_END_COORDINATE - (yLength * i));
        }

        for (int i = 0, j = -70; i < xCoordinateNumber; i++, j += 10) {
            if (j == 0) {
                g.drawString("" + j, X_AXIS_START_COORDINATE + (xLength * i) ,X_AXIS_Y_STATIC + 15);
                continue;
            }
            g.drawString("" + j, X_AXIS_START_COORDINATE + (xLength * i),X_AXIS_Y_STATIC + 15);
            g.drawLine(X_AXIS_START_COORDINATE + (xLength * i), X_AXIS_Y_STATIC - 3,
                    X_AXIS_START_COORDINATE + (xLength * i), X_AXIS_Y_STATIC + 3);
        }


        int P_X;
        int P_Y;
        List<Point> actualPoints = new LinkedList<>();

        for (Point2D p : points) {
            P_X = X_AXIS_START_COORDINATE + (int)(((p.getX() + 70) / 150) * X_AXIS_LENGTH - 5);
            P_Y = (int)(Y_AXIS_END_COORDINATE - (p.getY() / 110) * Y_AXIS_LENGTH - 8);
            actualPoints.add(new Point(P_X, P_Y));
        }

        g.fillOval(actualPoints.get(0).x - 3, actualPoints.get(0).y - 3, 10, 10);
        for (int i = 1; i < actualPoints.size(); i++) {
            g.drawLine(actualPoints.get(i - 1).x, actualPoints.get(i - 1).y,
                    actualPoints.get(i).x, actualPoints.get(i).y);
            g.fillOval(actualPoints.get(i).x - 3, actualPoints.get(i).y - 3, 10, 10);
        }
    }
}
