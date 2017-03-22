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
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		
		int min = n, indexI = -1, indexJ = -1;
		
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				int tmp = random.nextInt();
				mat[i][j] = tmp % (n + 1);
				System.out.print(mat[i][j] + "    ");
				if (mat[i][j] < min){
					min = mat[i][j];
					indexI = i;
					indexJ = j;
				}
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Enter new indexI = ");
		int newI = in.nextInt();
		System.out.println("Enter new indexJ = ");
		int newJ = in.nextInt();
		in.close();
		
		if (newI >= n || newI < 0 || newJ < 0 || newJ >= n){
			System.err.println("Invalid data");
			System.exit(1);
		}
		
		if (indexJ != newJ){
			for (int i = 0; i < n; i++){
				int d = mat[i][indexJ];
				mat[i][indexJ] = mat[i][newJ];
				mat[i][newJ] = d;
			}
		}
		
		if (indexI != newI){
			int[]tmp = mat[newI];
			mat[newI] = mat[indexI];
			mat[indexI] = tmp;
		}
		
	    for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				System.out.print(mat[i][j] + "     ");
			}
			System.out.print("\n");
		}
	}

}
