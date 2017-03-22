import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by vladasheleg on 26.10.16.
 */
public class ComplexMember implements Serializable{

    private static final long serialVersionUID = 1L;

    String name;
    public static final String P_name = "Name";
    String sectionCode;
    public static final String P_sectionCode = "Section code";
    String typeOfActivities;
    public static final String P_typeOfActivities = "Type of activities";
    String trainer;
    public static final String P_trainer = "Trainer";
    Date startTime;
    public static final String P_startTime = "Start time";
    int duration;
    public static final String P_duration = "Duration";
    double price;
    public static final String P_price = "Price";

    public static Boolean nextRead( Scanner fin, PrintStream out ) {
        return nextRead( P_name, fin, out );
    }

    static Boolean nextRead( final String prompt, Scanner fin, PrintStream out ) {
        out.print( prompt );
        out.print( ": " );
        return fin.hasNextLine();
    }

    public static ComplexMember read(Scanner fin, PrintStream out) throws ParseException {
        ComplexMember member = new ComplexMember();
        fin.nextLine();
        member.name = fin.nextLine();
        if ( ! nextRead(P_sectionCode, fin, out )) return null;
        member.sectionCode = fin.nextLine();
        if ( ! nextRead(P_typeOfActivities, fin, out )) return null;
        member.typeOfActivities = fin.nextLine();
        if ( ! nextRead(P_trainer, fin, out )) return null;
        member.trainer = fin.nextLine();
        if ( ! nextRead(P_startTime, fin, out )) return null;
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        member.startTime = parser.parse(fin.nextLine());
        if ( !nextRead(P_duration, fin, out )) return null;
        member.duration = fin.nextInt();
        if ( !nextRead(P_price, fin, out )) return null;
        member.price = fin.nextDouble();


        return member;
    }

    @Override
    public String toString() {
        return name + " | " +
                sectionCode + " | " +
                typeOfActivities + " | " +
                trainer + " | " +
                startTime + " | " +
                duration + " | " +
                price;
    }
}
