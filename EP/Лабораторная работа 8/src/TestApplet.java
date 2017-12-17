import bsu.fpmi.education_practice.FlatHorizontalLine;

import java.applet.Applet;
import java.awt.*;

/**
 * Created by vladasheleg on 07.02.17.
 */

public class TestApplet extends Applet{
    private FlatHorizontalLine flatHorizontalLine;

    @Override
    public void init() {
        flatHorizontalLine = new FlatHorizontalLine(10, new Color(0x6C689F));
    }

    @Override
    public void paint(Graphics g) {
        flatHorizontalLine.paint(g);
    }

}
