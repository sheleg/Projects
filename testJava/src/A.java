/**
 * Created by vladasheleg on 10/1/17.
 */
public class A {
    int x, y;

    public A(int x1, int y) {
        x = x1;
    }

    @Override
    public String toString() {
        return "A{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static void main(String[] args) {
        A a = new A(5, 7);
        System.out.println(a);
    }
}