import java.io.Serializable;
import java.util.Date;

/**
 * Created by vladasheleg on 20.10.16.
 */
public class Pet implements Serializable{
    private String name;
    private int age;
    private int weight;
    private Date date;

    public Pet() {
        name = "";
        age = 0;
        weight = 0;
        date = new Date();
    }

    public Pet(String name, int age, int weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        date = new Date();
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public void creationDate() { System.out.println(date); }

    public Date getCreationDate() { return date; }

    @Override
    public String toString() {
        return "Pet's name " + name + "\n" +
                "Pet's age " + age + "\n" +
                "Pet's weight " + weight + "\n" +
                getCreationDate();
    }
}
