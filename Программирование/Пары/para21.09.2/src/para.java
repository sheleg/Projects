import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class para {

	public static void main(String[] args) throws IOException{
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter n: ");
		int n = in.nextInt();
		
		if (n <= 1){
			System.err.println("Invalid n");
			System.exit(1);
		}
		
		int[][]mat = new int[n][n];
		int[][] mins = new int[n][2];
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		for (int i = 0; i < n; i++){
			mins[i][0] = n;
			mins[i][1] = -1;
		}
		
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				int tmp = random.nextInt();
				mat[i][j] = tmp % (n + 1);
				System.out.print(mat[i][j] + "    ");
				if (mat[i][j] < mins[i][0]){
					mins[i][0] = mat[i][j];
					mins[i][1] = i;
				}
			}
			System.out.println();
		}
		
		int counter = n;
		
		for (int j = 0; j < n; j++){
			for (int i = 0; i < n; i++){
				if (mat[i][j] > mins[j][0]){
					counter--;
					break;
				}
			}
		}
		
		System.out.println(n);
	}
}
  