package laba2;

public class TaylorRow {
	public static void main(String[] args){
		if (args.length != 2){
			System.err.println("Invalid number of argument");
			System.exit(1);
		}
		
		double x = Double.parseDouble(args[0]);
		if (x <= -1 || x >= 1){
			System.err.println("Invalid argument");
			System.exit(1);
		}
		
		int k = Integer.parseInt(args[1]);
		if (k <= 1){
			System.err.println("Invalid argument");
			System.exit(1);
		}
		
		double eps = 1 / Math.pow(10, k), step  = 1, result = 0;
		result += step;
		step = 0.5 * x;
		result += step;
		int n = 2;
		
		while (Math.abs(step) >= eps){
			step *= (-1) * (((2 * n - 3) * x) / (2 * n));
			result += step;
			n++;
		}
		
		System.out.printf("1 = %f", result);
		System.out.println();
		System.out.printf("2 = %f", Math.sqrt(1 + x));
		System.out.println();
	}
}
