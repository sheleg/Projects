import java.io.Serializable;
/**
 * Created by vladasheleg on 20.10.16.
 */
public class Pet implements Serializable{
    private String name;
    private int age;
    private int weight;

    public Pet() {
        name = "";
        age = 0;
        weight = 0;
    }

    public Pet(String name, int age, int weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    @Override
    public String toString() {
        return "Pet's name " + name + "\n" +
                "Pet's age " + age + "\n" +
                "Pet's weight " + weight;
    }
}
