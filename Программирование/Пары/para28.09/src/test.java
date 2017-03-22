
public class test {
	static final int NUM = 10;
	
	public static void main(String[] args) {
		Matrix[] array = new Matrix[NUM];
		for (int i = 0; i < NUM; i++){
			System.out.println(i + 1);
			array[i] = new Matrix(2);
			array[i].Print();
		}
		
		int min = Integer.MAX_VALUE, k = -1;
		for (int i = 0; i < NUM; i++){
			int norm = array[i].norm_2();
			if (norm < min) {
				min = norm; 
				k = i + 1;
			}
		}
		
		System.out.println("Matrix #" + k + " has min norm_2:" + min);
	}
}
