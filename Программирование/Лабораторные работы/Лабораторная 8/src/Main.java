import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Created by vladasheleg on 26.10.16.
 */
public class Main {
    static boolean optionZip = true;

    public static void main(String[] args) {
        try {
            if ( args.length >= 1 ) {
                if ( args[0].compareTo( "-a" )== 0 ) {
                    // Append file with new object from System.in
                    append_file();
                }
                else if ( args[0].compareTo( "-p" )== 0 ) {
                    // Prints data file
                    print_file();
                }
                else if ( args[0].compareTo( "-d" )== 0 ) {
                    // Delete data file
                    delete_file();
                }
                else {
                    System.err.println( "Option is not realised: " + args[0] );
                    System.exit(1);
                }
            }
            else {
                System.err.println( "Members: Nothing to do!" );
            }
        }
        catch ( Exception e ) {
            System.err.println( "Run/time error: " + e );
            System.exit(1);
        }
        System.out.println( "Members finished..." );
        return;
    }

    static final String filename = "Members.dat";

    private static Scanner fin = new Scanner( System.in );

    static ComplexMember read_member() throws ParseException {
        if ( fin.hasNextLine()) {
            return ComplexMember.read(fin);
        }

        return null;
    }

    static void delete_file() {
        File f = new File( filename );
        f.delete();
    }

    static void append_file() throws FileNotFoundException, IOException, ParseException {
        ComplexMember member;
        System.out.println( "Enter member data: " );
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            while (( member = read_member())!= null ) {
                Buffer.writeObject( raf, member );
            }
        }
    }

    static void print_file()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            long pos;
            while (( pos = raf.getFilePointer()) < raf.length() ) {
                ComplexMember member = (ComplexMember) Buffer.readObject( raf, pos );
                System.out.println( pos + ": " + member );
            }
        }
    }
}
