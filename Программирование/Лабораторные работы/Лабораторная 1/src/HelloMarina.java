public class HelloMarina {
	private String M;
	private int age;
	
	public HelloMarina(String s) {
		this.M = new String(s);
	}
	
	public HelloMarina(String M, int age) {
		this.M = new String(M);
		this.age = age;
	}
	
	@Override
	public String toString() {
		return M + " " + age;
	}
}
