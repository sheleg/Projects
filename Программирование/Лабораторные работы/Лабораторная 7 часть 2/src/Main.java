import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Created by vladasheleg on 21.10.16.
 */
public class Main {

    static Locale createLocale(String[] args) {
        if (args.length == 2) {
            return new Locale(args[0], args[1]);
        } else if (args.length == 4) {
            return new Locale(args[2], args[3]);
        }

        return null;
    }

    static void setupConsole(String[] args) {
        if (args.length >= 2) {
            if (args[0].compareTo("-encoding") == 0) {
                try {
                    System.setOut(new PrintStream(System.out, true, args[1]));
                } catch (UnsupportedEncodingException e) {
                    System.err.println("Unsupported encoding: " + args[1]);
                    System.exit(1);
                }
            }
        }
    }

    public static void main(String[] args) {
        setupConsole(args);
        Locale locale = createLocale(args);

        if (locale == null) {
            System.err.println("Invalid argument(s)\n" +
                    "Syntax: [-encoding ENCODING_ID] language country\n" +
                    "Example: -encoding CP855 by BY");
            System.exit(1);
        }

        AppLocale.setLocale(locale);

        Dog dog = new Dog("Nan", 2, 11);
        //System.out.println(dog.toString());
        Connector c = new Connector();
        c.write(dog);
        System.out.println(dog);
    }
}
