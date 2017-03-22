package laba4;

import java.security.cert.CertPathChecker;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class laba4 {
	static ArrayList<Integer> arrayList = new ArrayList<>();
	
	public static void main(String[] args){
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
		
		
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				int tmp = random.nextInt();
				mat[i][j] = tmp % (n + 1);
				if (mat[i][j] < 0)
					System.out.print(mat[i][j] + "       ");
				else 
					System.out.print(mat[i][j] + "        ");
			}
			System.out.println();
		}
		
		int[][] newMatrix = new int [n + 2][n + 2];
		
		for (int i = 0; i < n + 2; i++) {
			for (int j = 0; j < n + 2; j++) {
				newMatrix[i][j] = -n - 1;
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				newMatrix[i + 1][j + 1] = mat[i][j];
			}
		}
		
		/*for (int i = 0; i < n + 2; i++) {
			for (int j = 0; j < n + 2; j++) {
				System.out.print(newMatrix[i][j] + "        ");
			}
			System.out.println();
		}*/
		int tmp = check(newMatrix, n + 2);
		if (tmp != -1)
			System.out.println("Maximum of local maximum:" + tmp);
		else {
			System.out.println("Local maximum isn'n found");
		}
		
		in.close();
	}
	
	public static int check(int[][] matrix, int n){
		for (int i = 1; i < n - 1; i++) {
			for (int j = 1; j < n - 1; j++) {
				if ((matrix[i][j] > matrix[i + 1][j]) && (matrix[i][j] > matrix[i + 1][j - 1])
						&& (matrix[i][j] > matrix[i + 1][j + 1]) && (matrix[i][j] > matrix[i][j + 1])
						&& (matrix[i][j] > matrix[i][j - 1]) && (matrix[i][j] > matrix[i - 1][j])
						&& (matrix[i][j] > matrix[i - 1][j - 1]) && (matrix[i][j] > matrix[i - 1][j + 1]))
					arrayList.add(new Integer(matrix[i][j]));
			}
		}
		
		if (arrayList.size() > 0){
			int a = arrayList.get(0);
			for (int i = 1; i < arrayList.size(); i++) {
				if (arrayList.get(i) > a)
					a = arrayList.get(i);
			}
		
			return a;
		}
		else {
			return -1;
		}
	}
}
