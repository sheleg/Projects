import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by vladasheleg on 26.10.16.
 */
public class ComplexMember implements Serializable{

    String name;
    String sectionCode;
    String typeOfActivities;
    String trainer;
    Date startTime;
    int duration;
    double price;


    public static ComplexMember read(Scanner fin) throws ParseException {
        ComplexMember member = new ComplexMember();
        member.name = fin.nextLine();
        if ( ! fin.hasNextLine()) return null;
        member.sectionCode = fin.nextLine();
        if ( ! fin.hasNextLine()) return null;
        member.typeOfActivities = fin.nextLine();
        if ( ! fin.hasNextLine()) return null;
        member.trainer = fin.nextLine();
        if ( ! fin.hasNextLine()) return null;
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        member.startTime = parser.parse(fin.nextLine());
        if ( ! fin.hasNextInt()) return null;
        member.duration = fin.nextInt();
        if ( ! fin.hasNextDouble()) return null;
        member.price = fin.nextDouble();
       /* if (fin.nextInt() == -1 || !fin.hasNext())
            return member;*/
        return member;
    }

    @Override
    public String toString() {
        return new String(name + " | " +
                sectionCode + " | " +
                typeOfActivities + " | " +
                trainer + " | " +
                startTime +  " | " +
                duration + " | " +
                price
        );
    }
}
