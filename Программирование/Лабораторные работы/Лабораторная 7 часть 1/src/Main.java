/**
 * Created by vladasheleg on 21.10.16.
 */
public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog("Nan", 2, 11);
        //System.out.println(dog.toString());
        Connector c = new Connector();
        c.write(dog);
        System.out.println((Dog)c.read());
    }
}
