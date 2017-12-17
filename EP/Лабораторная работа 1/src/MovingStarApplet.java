import java.applet.Applet;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by vladasheleg on 07.02.17.
 */

public class MovingStarApplet extends Applet implements Runnable{
    private Thread animation = null;
    private StarInCircle starInCircle = new StarInCircle();

    @Override
    public void init() {

        Color pen, fill;
        String[] penC = getParameter("penColor").split(" ");
        String[] fillC = getParameter("fillColor").split(" ");
        pen = new Color(Integer.parseInt(penC[0]), Integer.parseInt(penC[1]), Integer.parseInt(penC[2]));
        fill = new Color(Integer.parseInt(fillC[0]), Integer.parseInt(fillC[1]), Integer.parseInt(fillC[2]));

        add(new StarInCircle(new Point2D.Double(Double.parseDouble(getParameter("centrePointX")),
                Double.parseDouble(getParameter("centrePointY"))),
                Double.parseDouble(getParameter("bigRadius")),
                Double.parseDouble(getParameter("smallRadius")),
                Integer.parseInt(getParameter("penWidth")),
                pen, fill));
    }

    @Override
    public void paint(Graphics g) {
        starInCircle.paint(g);
    }

    public void start() {
        animation = new Thread(this);
        animation.start();
    }

    @Override
    public void run() {
        Thread threadCopy = Thread.currentThread();
        while (animation == threadCopy) {
            try {
                Thread.sleep(200);
                repaint();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void stop() {
        animation = null;
    }
}
