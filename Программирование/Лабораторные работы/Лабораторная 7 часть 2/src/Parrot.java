/**
 * Created by vladasheleg on 20.10.16.
 */
public class Parrot extends Pet {
    public Parrot() {
        super();
    }

    public Parrot(String name, int age, int weight) {
        super(name, age, weight);
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.parrot_name) + getName() + "\n" +
                AppLocale.getString(AppLocale.parrot_age) + getAge() + "\n" +
                AppLocale.getString(AppLocale.parrot_weight) + getWeight() + "\n" +
                getCreationDate();
    }
}
