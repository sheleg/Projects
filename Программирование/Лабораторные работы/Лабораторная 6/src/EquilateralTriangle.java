
public class EquilateralTriangle extends Triangle {
    public EquilateralTriangle(double a) {
        super(a, a, Math.toRadians(60));
    }

    public EquilateralTriangle(String s) {
        super(s + " " + s + " " + Math.toRadians(60));
    }

    @Override
    protected double Area() {
        return getA() * getA() * getA();
    }

    @Override
    protected double Perimetr() {
        return 3 * getA();
    }

    @Override
    public String toString() {
        return "Sides: " + String.format("%.2f", getA()) + System.lineSeparator() +
                "Angle (radians): " + String.format("%.2f", getAngle()) + System.lineSeparator() +
                "Angle (degrees): " + String.format("%.2f", Math.toDegrees(getAngle())) + System.lineSeparator() +
                "Area: " + String.format("%.2f", getArea()) + System.lineSeparator() +
                "Perimetr: " + getPerimetr() + System.lineSeparator();
    }
}
