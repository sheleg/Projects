/**
 * Created by vladasheleg on 8/7/17.
 */
public class Test {
    public static void main(String[] args) {
        double[][] A = {{0, 4, -1}, {0, 3, 0}, {1, 0, 0}};
//        double[][] b = {{0}, {-1}, {0}};
        double[] b = {0, -1, 0};
        Matrix matrixA = new Matrix(A);
        matrixA.solve(new Matrix(b)).show();
    }
}
