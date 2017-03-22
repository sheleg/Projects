import java.util.Iterator;
import java.util.NoSuchElementException;

class TriangleIterator implements Iterator<Double> {
    private Triangle triangle;
    private int index = -1;

    TriangleIterator(Triangle triangle) {
        this.triangle = triangle;
    }

    @Override
    public boolean hasNext() {
        return (index >= -1 && index <= 3);
    }

    @Override
    public Double next() {
        switch (index++) {
            case -1:
                return triangle.getA();
            case 0:
                return triangle.getB();
            case 1:
                return triangle.getAngle();
            case 2:
                return triangle.getArea();
            case 3:
                return triangle.getPerimetr();
        }
        throw new NoSuchElementException();
    }

    public void reset() {
        index = -1;
    }
}
