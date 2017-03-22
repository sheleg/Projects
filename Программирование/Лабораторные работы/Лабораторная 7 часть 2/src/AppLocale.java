import java.util.Locale;
import java.util.ResourceBundle;

class AppLocale {
    private static final String strMsg = "Msg";
    private static Locale locale = Locale.UK;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(AppLocale.strMsg, AppLocale.locale);

    static Locale getLocale() {
        return AppLocale.locale;
    }

    static void setLocale(Locale locale) {
        AppLocale.locale = locale;
        resourceBundle = ResourceBundle.getBundle(AppLocale.strMsg, AppLocale.locale);
    }

    static ResourceBundle getResourceBundle() {
        return AppLocale.resourceBundle;
    }

    static String getString(String key) {
        return AppLocale.resourceBundle.getString(key);
    }

    public static final String pet_name = "pet_name";
    public static final String pet_age = "pet_age";
    public static final String pet_weight = "pet_weight";
    public static final String dog_name = "dog_name";
    public static final String dog_age = "dog_age";
    public static final String dog_weight = "dog_weight";
    public static final String cat_name = "cat_name";
    public static final String cat_age = "cat_age";
    public static final String cat_weight = "cat_weight";
    public static final String parrot_name = "parrot_name";
    public static final String parrot_age = "parrot_age";
    public static final String parrot_weight = "parrot_weight";
}
