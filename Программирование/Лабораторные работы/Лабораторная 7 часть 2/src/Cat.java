/**
 * Created by vladasheleg on 20.10.16.
 */
public class Cat extends Pet {
    public Cat() {
        super();
    }

    public Cat(String name, int age, int weight) {
        super(name, age, weight);
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.cat_name) + getName() + "\n" +
                AppLocale.getString(AppLocale.cat_age) + getAge() + "\n" +
                AppLocale.getString(AppLocale.cat_weight) + getWeight() + "\n" +
                getCreationDate();
    }
}
