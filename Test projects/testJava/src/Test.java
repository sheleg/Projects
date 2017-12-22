public class Test {
    public static void func(float tay1, float tay2, float h) {
        int M = 20, N = (int) (50f / tay1);
        float sigma = 17f;
        float max = 0f, eps = 1f;   //

        int[] n = new int[6];
        for (int k = 0; k <= 5; k++) {
            n[k] = (int) (10 * k / tay1);
        }

        float[] y_old_1 = new float[M + 1];
        float[] y_new_1 = new float[M + 1];
        float[] y_old_2 = new float[M + 1];   //
        float[] y_new_2 = new float[M + 1];   //

        float x = 0f, pi = (float) Math.PI, pi2 = pi / 2;
        for (int i = 0; i <= M; i++) {
            if (x <= pi2) {
                y_old_1[i] = 9 * x;
                y_old_2[i] = 9 * x;  //
            } else {
                y_old_1[i] = 9 * (pi - x);
                y_old_2[i] = 9 * (pi - x);   //
            }
            x += h;
        }

        for (int i = 0; i <= M; i++) {
//            System.out.printf("(%f;%f) ", i * h, y_old_1[i]);
//            System.out.printf("(%f;%f) ", i * h, y_old_1[i]);
//            System.out.println(i * h);
        }
//        System.out.println();


        for (int i = 1; i <= N; i++) {
            y_new_1[0] = 0;
            y_new_1[M] = 0;
            y_new_2[0] = 0;   //
            y_new_2[M] = 0;   //

            for (int j = 1; j < M; j++) {
                y_new_1[j] = sigma * tay1 / h / h * (y_old_1[j - 1] - 2 * y_old_1[j] + y_old_1[j + 1]) + y_old_1[j];
                y_new_2[j] = sigma * tay2 / h / h * (y_old_2[j - 1] - 2 * y_old_2[j] + y_old_2[j + 1]) + y_old_2[j];  //
            }

            boolean flag = false;
            for (int k = 1; k <= 5; k++) {
                if (n[k] == i) {
                    flag = true;
                    break;
                }
            }

//            if (flag) {
//                for (int j = 0; j <= M; j++) {
////                    System.out.println(j * h);
//                    System.out.println(y_new_1[j]);
//                }
//                System.out.println();
//            }
//
//
            for (int k = 0; k <= M; k++) {                      /////////////
                if (Math.abs(y_new_1[k] - y_new_2[k]) > max) {
                    max = Math.abs(y_new_1[k] - y_new_2[k]);
                }
            }

            if (eps > max) {
                for (int j = 0; j <= M; j++) {
//                    System.out.print("(" + (j * h) + ";" + y_new_2[j] + ") ");
//                    System.out.print(j * h);
                    System.out.print(y_new_2[j]);
                    System.out.println();
                }
                System.out.println();
            } else {
                break;
            }
//
            for (int j = 0; j <= M; j++) {
                y_old_1[j] = y_new_1[j];
                y_old_2[j] = y_new_2[j];    //
            }
        }

    }

    public static void main(String[] args) {
        try {
            int M = 20;
            float sigma = 17f, tay1, tay2, h = (float) Math.PI / M;

            tay1 = 5f / 11 * h * h / sigma;
            tay2 = 5f / 9 * h * h / sigma;

            func(tay1, tay2, h);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

