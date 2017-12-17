package bsu.fpmi.education_practice;

import java.awt.*;
import java.util.*;


public class FlatHorizontalLine extends Canvas {
    // User-specified properties
    private int width = 2;
    private Color color = new Color(0, 0, 0);

    private int X_START_COORDINATE = 100;
    private int X_END_COORDINATE = 400;

    private int Y_COORDINATE = 100;

    public FlatHorizontalLine() {}

    public FlatHorizontalLine(int width, Color color) {
        this.width = width;
        this.color = color;
    }

    public FlatHorizontalLine(int width) {
        this.width = width;
    }

    public FlatHorizontalLine(Color color) {
        this.color = color;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        BasicStroke pen = new BasicStroke(width);

        graphics2D.setStroke(pen);
        graphics2D.setColor(color);

        graphics2D.drawLine(X_START_COORDINATE, Y_COORDINATE, X_END_COORDINATE, Y_COORDINATE);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setX_START_COORDINATE(int x_START_COORDINATE) {
        X_START_COORDINATE = x_START_COORDINATE;
    }

    public void setX_END_COORDINATE(int x_END_COORDINATE) {
        X_END_COORDINATE = x_END_COORDINATE;
    }

    public void setY_COORDINATE(int y_COORDINATE) {
        Y_COORDINATE = y_COORDINATE;
    }

}
