import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Created by vladasheleg on 26.10.16.
 */
public class ComplexMember implements Serializable{

    public ComplexMember(String name, String sectionCode, 
            String typeOfActivities, String trainer, 
            String startTime, int duration, double price) {
        this.name = name;
        this.sectionCode = sectionCode;
        this.typeOfActivities = typeOfActivities;
        this.trainer = trainer;
        this.startTime = startTime;
        this.duration = duration;
        this.price = price;
    }
    
    private static final long serialVersionUID = 1L;

    public String name;
    String sectionCode;
    String typeOfActivities;
    String trainer;
    String startTime;
    int duration;
    double price;
    
    public static final String P_name = "Name";
    public static final String P_sectionCode = "Section code";
    public static final String P_typeOfActivities = "Type of activities";
    public static final String P_trainer = "Trainer";
    public static final String P_startTime = "Start time";
    public static final String P_duration = "Duration";
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
        ComplexMember member = null;
        GregorianCalendar curCalendar = new GregorianCalendar();
        member.name = fin.nextLine();
        if ( ! nextRead(P_sectionCode, fin, out )) return null;
        member.sectionCode = fin.nextLine();
        if ( ! nextRead(P_typeOfActivities, fin, out )) return null;
        member.typeOfActivities = fin.nextLine();
        if ( ! nextRead(P_trainer, fin, out )) return null;
        member.trainer = fin.nextLine();
        if ( ! nextRead(P_startTime, fin, out )) return null;
        String date_ = fin.nextLine();
	String[] date = date_.split("-");
	if (date.length != 3 || date[0].length() != 4 || date[1].length() != 2 || date[2].length() != 2)
		return null;
	if (Integer.parseUnsignedInt(date[0]) > curCalendar.get( Calendar.YEAR ))
		return null;
	if (Integer.parseUnsignedInt(date[1]) > 12 || Integer.parseUnsignedInt(date[2]) > 31)
		return null;
	member.startTime = date_;
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
