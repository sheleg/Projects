import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "-?":
                    case "-h":
                        System.out.println(
                                "Syntax:\n" +
                                        "\t-a  [file [encoding]] - append data\n" +
                                        "\t-az [file [encoding]] - append data, compress every record\n" +
                                        "\t-d                    - clear all data\n" +
                                        "\t-dk  {n|t|s} key      - clear data by key\n" +
                                        "\t-p                    - print data unsorted\n" +
                                        "\t-ps  {n|t|s}          - print data sorted by name/trainer/start time\n" +
                                        "\t-psr {n|t|s}          - print data reverse sorted by name/trainer/start time\n" +
                                        "\t-f   {n|t|s} key      - find record by key\n"+
                                        "\t-fr  {n|t|s} key      - find records > key\n"+
                                        "\t-fl  {n|t|s} key      - find records < key\n"+
                                        "\t-?, -h                - command line syntax\n"
                        );     break;
                    case "-a":
                        // Append file with new object from System.in
                        // -a [file [encoding]]
                        appendFile(args, false);
                        break;
                    case "-az":
                        // Append file with compressed new object from System.in
                        // -az [file [encoding]]
                        appendFile(args, true);
                        break;
                    case "-p":
                        // Prints data file
                        printFile();
                        break;
                    case "-ps":
                        // Prints data file sorted by key
                        if (printFile(args, false)== false) {
                            System.exit(1);
                        }   break;
                    case "-psr":
                        // Prints data file reverse-sorted by key
                        if (printFile(args, true)== false) {
                            System.exit(1);
                        }   break;
                    case "-d":
                        // delete files
                        if (args.length != 1) {
                            System.err.println("Invalid number of arguments");
                            System.exit(1);;
                        }   deleteFile();
                        break;
                    case "-dk":
                        // Delete records by key
                        if (deleteFile(args)== false) {
                            System.exit(1);
                        }   break;
                    case "-f":
                        // Find record(s) by key
                        if (findByKey(args)== false) {
                            System.exit(1);
                        }   break;
                    case "-fr":
                        // Find record(s) by key large then key
                        if (findByKey(args, new KeyCompReverse())== false) {
                            System.exit(1);
                        }   break;
                    case "-fl":
                        // Find record(s) by key less then key
                        if (findByKey(args, new KeyComp())== false) {
                            System.exit(1);
                        }   break;
                    default:
                        System.err.println("Option is not realised: " + args[0]);
                        System.exit(1);
                }
            }
            else {
                System.err.println("ComplexMembers: Nothing to do! Enter -? for options");
            }
        }
        catch (Exception e) {
            System.err.println("Run/time error: " + e);
            System.exit(1);
        }
        System.err.println("ComplexMembers finished...");
        System.exit(0);
    }

    static final String filename    = "ComplexMembers.dat";
    static final String filenameBak = "ComplexMembers.~dat";
    static final String idxname     = "ComplexMembers.idx";
    static final String idxnameBak  = "ComplexMembers.~idx";

    // input file encoding:
    private static String encoding = "Cp866";
    private static PrintStream memberOut = System.out;

    static ComplexMember readComplexMember(Scanner fin) throws IOException, ParseException {
        return ComplexMember.nextRead(fin, memberOut)
                ? ComplexMember.read(fin, memberOut) : null;
    }

    private static void deleteBackup() {
        new File(filenameBak).delete();
        new File(idxnameBak).delete();
    }

    static void deleteFile() {
        deleteBackup();
        new File(filename).delete();
        new File(idxname).delete();
    }

    private static void backup() {
        deleteBackup();
        new File(filename).renameTo(new File(filenameBak));
        new File(idxname).renameTo(new File(idxnameBak));
    }

    static boolean deleteFile(String[] args)
            throws ClassNotFoundException, IOException, KeyNotUniqueException {
        //-dk  {n|t|s} key      - clear data by key
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        long[] poss = null;
        try (Index idx = Index.load(idxname)) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            if (pidx.contains(args[2])== false) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            poss = pidx.get(args[2]);
        }
        backup();
        Arrays.sort(poss);
        try (Index idx = Index.load(idxname);
              RandomAccessFile fileBak= new RandomAccessFile(filenameBak, "rw");
              RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            boolean[] wasZipped = new boolean[] {false};
            long pos;
            while ((pos = fileBak.getFilePointer()) < fileBak.length()) {
                ComplexMember member = (ComplexMember) 
                        Buffer.readObject(fileBak, pos, wasZipped);
                if (Arrays.binarySearch(poss, pos) < 0) { // if not found in deleted
                    long ptr = Buffer.writeObject(file, member, wasZipped[0]);
                    idx.put(member, ptr);
                }
            }
        }
        return true;
    }

    static void appendFile(String[] args, Boolean zipped)
            throws FileNotFoundException, IOException, ClassNotFoundException,
            KeyNotUniqueException, ParseException {
        if (args.length >= 2) {
            FileInputStream stdin = new FileInputStream(args[1]);
            System.setIn(stdin);
            if (args.length == 3) {
                encoding = args[2];
            }
            // hide output:
            memberOut = new PrintStream("nul");
        }
        appendFile(zipped);
    }

    static void appendFile(Boolean zipped)
            throws FileNotFoundException, IOException, ClassNotFoundException,
            KeyNotUniqueException, ParseException {
        Scanner fin = new Scanner(System.in, encoding);
        memberOut.println("Enter member data: ");
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            for(;;) {
                ComplexMember member = readComplexMember(fin);
                if (member == null)
                    break;
                idx.test(member);
                long pos = Buffer.writeObject(raf, member, zipped);
                idx.put(member, pos);
            }
        }
    }



    private static void printRecord(RandomAccessFile raf, long pos)
            throws ClassNotFoundException, IOException {
        boolean[] wasZipped = new boolean[] {false};
        ComplexMember member = (ComplexMember) Buffer.readObject(raf, pos, wasZipped);
        if (wasZipped[0] == true) {
            System.out.print(" compressed");
        }
        System.out.println(" record at position "+ pos + ": \n" + member);
    }

    private static void printRecord(RandomAccessFile raf, String key,
                                     IndexBase pidx) throws ClassNotFoundException, IOException {
        long[] poss = pidx.get(key);
        for (long pos : poss) {
            System.out.print("*** Key: " +  key + " points to");
            printRecord(raf, pos);
        }
    }

    static void printFile()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        long pos;
        int rec = 0;
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            while ((pos = raf.getFilePointer()) < raf.length()) {
                System.out.print("#" + (++rec));
                printRecord(raf, pos);
            }
            System.out.flush();
        }
    }

    private static IndexBase indexByArg(String arg, Index idx) {
        IndexBase pidx = null;
        if (arg.equals("n")) {
            pidx = idx.name;
        }
        else if (arg.equals("t")) {
            pidx = idx.trainer;
        }
        else if (arg.equals("s")) {
            pidx = idx.startTime;
        }
        else {
            System.err.println("Invalid index specified: " + arg);
        }
        return pidx;
    }

    static boolean printFile(String[] args, boolean reverse)
            throws ClassNotFoundException, IOException {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            String[] keys =
                    pidx.getKeys(reverse ? new KeyCompReverse() : new KeyComp());
            for (String key : keys) {
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }

    static boolean findByKey(String[] args)
            throws ClassNotFoundException, IOException, ParseException {
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (args[1].compareTo("s") == 0) {
                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                Date d;
                d = parser.parse(args[2]);
                if (!pidx.contains(d.toString())) {
                    System.err.println("Key not found: " + args[2]);
                    return false;
                }
            } else {
                if (!pidx.contains(args[2])) {
                    System.err.println("Key not found: " + args[2]);
                    return false;
                }
            }
            printRecord(raf, args[2], pidx);
        }
        return true;
    }

    static boolean findByKey(String[] args, Comparator<String> comp)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx.contains(args[2])== false) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            String[] keys = pidx.getKeys(comp);
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if (key.equals(args[2])) {
                    break;
                }
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }
}
