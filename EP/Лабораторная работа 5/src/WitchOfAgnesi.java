import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;

public class WitchOfAgnesi implements Shape, Serializable, Cloneable, Transferable {
    private int param = 0;
    //    private final double delta = 0.001;
    private double startAngle = 0.01;
    private double endAngle = startAngle + Math.PI - 2 * 0.01;
    private float centerX = 0;
    private float centerY = 0;

    public WitchOfAgnesi(int param, int centerX, int centerY) {
        this.param = param;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public Rectangle getBounds() {
        // Надо
        return new Rectangle((int)(centerX - param), (int)(centerY - param), 2 * param, 2 * param);
    }

    @Override
    public Rectangle2D getBounds2D() {
        // Надо
        return new Rectangle.Float(centerX - param, centerY - param, 2 * param, 2 * param);
    }

    @Override
    public boolean contains(double x, double y) {
        // Не надо
        double x1 = x - centerX;
        double y1 = centerY - y;
        return Math.pow(x1, 2 / 3) + Math.pow(y1, 2 / 3) <= Math.pow(param, 2 / 3);
    }

    @Override
    public boolean contains(Point2D p) {
        return contains(p.getX(), p.getY());
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        // Точно нужно реализовать
        return getBounds().intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return getBounds().contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        // Не надо
        return contains(x, y) && contains(x + w, y) && contains(x, y + h) &&     contains(x + w, y + h);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        // Не надо
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new PlotIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new PlotIterator(at, flatness);
    }


    class PlotIterator implements PathIterator {
        AffineTransform transform;
        double flatness ;
        double angle = startAngle;
        double step = 10;
        boolean done = false;

        public PlotIterator(AffineTransform transform, double flatness) {
            this.transform = transform;
            this.flatness = flatness;
        }

        public PlotIterator(AffineTransform transform) {
            this.transform = transform;
            this.flatness = 0;
        }

        @Override
        public int getWindingRule() {
            return WIND_NON_ZERO;
        }

        @Override
        public boolean isDone() {
            return done;
        }

        @Override
        public void next() {
            if (done)
                return;
            if (flatness == 0)
                step = 0.05;
            else
                step = flatness;
            angle += step;
            if (angle >= endAngle)
                done = true;
        }

        @Override
        public int currentSegment(float[] coords) {
            coords[0] = (float) (param * Math.tan(angle)) + centerX;
            coords[1] = -(float) (param * Math.pow(Math.cos(angle), 2)) + centerY;
            if (angle >= endAngle) done = true;
            if (angle == startAngle) return SEG_MOVETO;
            else return SEG_LINETO;
        }

        @Override
        public int currentSegment(double[] coords) {
            coords[0] = (float) (param * Math.tan(angle)) + centerX;
            coords[1] = -(float) (param * Math.pow(Math.cos(angle), 2)) + centerY;
            if (angle >= endAngle) done = true;
            if (angle == startAngle) return SEG_MOVETO;
            else return SEG_LINETO;
        }
    }

    // ---------------- new in lab 05 ---------------------

    static DataFlavor decDataFlavor = new DataFlavor(WitchOfAgnesi.class, "Witch of Agnesi");

    // This is a list of the flavors we know how to work with
    private static DataFlavor[] supportedFlavors = {decDataFlavor, DataFlavor.stringFlavor};


    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors.clone();
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        return (dataFlavor.equals(decDataFlavor) || dataFlavor.equals(DataFlavor.stringFlavor));
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException {
        if (dataFlavor.equals(decDataFlavor)) {
            return this;
        } else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            return toString();
        } else
            throw new UnsupportedFlavorException(dataFlavor);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) { // This should never happen
            return this;
        }
    }


    @Override
    public String toString() {
        return param + " " + centerX + " " + centerY ;
    }


    static WitchOfAgnesi getFromString(String line) {

        String[] arr = line.split(" ");
        return new WitchOfAgnesi(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                Integer.parseInt(arr[2]));
    }

    void translate(int x, int y) {
        centerX += x;
        centerY += y;
    }

}


