import java.applet.Applet;
import java.awt.*;

public class Main extends Applet {
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0xFAEBD7));
        g.fillRect(0, 0, 200, 1000);
        g.setColor(new Color(0x6495ED));
        g.fillRect(200, 0, 500, 1000);
    }
}