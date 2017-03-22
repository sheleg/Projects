/**
 * Created by vladasheleg on 20.10.16.
 */
public class Dog extends Pet {
    public Dog() {
        super();
    }

    public Dog(String name, int age, int weight) {
        super(name, age, weight);
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.dog_name) + getName() + "\n" +
                AppLocale.getString(AppLocale.dog_age) + getAge() + "\n" +
                AppLocale.getString(AppLocale.dog_weight) + getWeight() + "\n" +
                getCreationDate();
    }
}
