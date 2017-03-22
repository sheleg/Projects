package laba5;

public class test {
	
	public static void main(String[] args) {
		Polynom p1 = new Polynom(6, 10);
		System.out.print("Polynom A = ");
		p1.Print();
		Polynom p2 = new Polynom(6, 5);
		System.out.print("Polynom B = ");
		p2.Print();
		System.out.print("Polynom A + B = ");
		p1.Add(p2).Print();
		System.out.print("Polynom A * B = ");
		p1.Mult(p2).Print();
	}	
}
