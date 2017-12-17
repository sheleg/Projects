import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by vladasheleg on 07.02.17.
 * Изобразить пятиконечную звезду, вписанную в окружность.
 * Задать вращение фигуры вокруг выбранной точки по часовой стрелке.
 */

public class StarInCircle extends Canvas implements Shape {
    private static Point2D.Double CENTRE_OF_SHAPE;
    private static double BIG_RADIUS, SMALL_RADIUS;
    private static double ANGLE = 0;

    AffineTransform affineTransform;

    public static int penWidth;
    public static Color penColor;
    public static Color fillColor;

    public StarInCircle(Point2D.Double centre, double radBig, double radSmall, int pWidth, Color pColor, Color fColor) {
        CENTRE_OF_SHAPE = centre;
        BIG_RADIUS = radBig;
        SMALL_RADIUS = radSmall;
        penWidth = pWidth;
        penColor = pColor;
        fillColor = fColor;
    }

    public StarInCircle() {}

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(penColor);
        BasicStroke pen = new BasicStroke(penWidth);
        g2D.setStroke(pen);

        g2D.drawOval((int)(CENTRE_OF_SHAPE.getX() - BIG_RADIUS), (int)(CENTRE_OF_SHAPE.getY() - BIG_RADIUS),
                (int)(BIG_RADIUS * 2), (int)(BIG_RADIUS * 2));

        affineTransform = AffineTransform.getRotateInstance(ANGLE, CENTRE_OF_SHAPE.getX(), CENTRE_OF_SHAPE.getY());
        g2D.setTransform(affineTransform);


        g2D.setColor(fillColor);
        paintPolygon(g2D);

        ANGLE += Math.PI / 128;
    }

    private void paintPolygon(Graphics2D g2D) {
        int[] pointsX = new int[11];
        int[] pointsY = new int[11];
        double a = ANGLE, da = Math.PI / 5, l;
        for (int k = 0; k < 11; k++) {
            l = k % 2 == 0 ? SMALL_RADIUS : BIG_RADIUS;
            pointsX[k] = (int) (CENTRE_OF_SHAPE.getX() + l * Math.cos(a));
            pointsY[k] = (int) (CENTRE_OF_SHAPE.getY() + l * Math.sin(a));
            a += da;
        }

        g2D.fillPolygon(pointsX, pointsY, 11);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return null;
    }
}
