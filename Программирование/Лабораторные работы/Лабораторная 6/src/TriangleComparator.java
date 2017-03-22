import java.util.Comparator;

public class TriangleComparator {
    public static Comparator<Triangle> getComparator(int i) {
        switch (i) {
            case 1:
                return new TriangleFirstSideComparator();
            case 2:
                return new TriangleSecondSideComparator();
            case 3:
                return new TriangleAngleComparator();
            case 4:
                return new TriangleAreaComparator();
            case 5:
                return new TrianglePerimetrComparator();
            default:
                return null;
        }
    }

    private static class TriangleAngleComparator implements Comparator<Triangle> {
        @Override
        public int compare(Triangle o1, Triangle o2) {
            return Double.compare(o1.getAngle(), o2.getAngle());
        }
    }

    private static class TriangleAreaComparator implements Comparator<Triangle> {
        @Override
        public int compare(Triangle o1, Triangle o2) {
            return Double.compare(o1.getArea(), o2.getArea());
        }
    }

    private static class TriangleFirstSideComparator implements Comparator<Triangle> {
        @Override
        public int compare(Triangle o1, Triangle o2) {
            return Double.compare(o1.getA(), o2.getA());
        }
    }

    private static class TrianglePerimetrComparator implements Comparator<Triangle> {
        @Override
        public int compare(Triangle o1, Triangle o2) {
            return Double.compare(o1.getPerimetr(), o2.getPerimetr());
        }
    }

    private static class TriangleSecondSideComparator implements Comparator<Triangle> {
        @Override
        public int compare(Triangle o1, Triangle o2) {
            return Double.compare(o1.getB(), o2.getB());
        }
    }
}
