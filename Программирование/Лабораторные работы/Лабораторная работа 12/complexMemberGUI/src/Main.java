import java.io.*;
import java.util.*;

public class Main {
    static String filename    = "test_buf.dat";
    static String filenameBak = "test_buf.~dat";
    static String idxname     = "test_buf.idx";
    static String idxnameBak  = "test_buf.~idx";
    public static int SortParam = 1;
    
    public static ComplexMember createComplexMember(String name, String sectionCode, 
                                            String typeOfActivities, String trainer, 
                                            String startTime, int duration, double price) {
        GregorianCalendar curCalendar = new GregorianCalendar();
        String date_ = startTime;
	String[] date = date_.split("-");
        if (name.length() == 0 || sectionCode.length() == 0 
                || typeOfActivities.length() == 0 || trainer.length() == 0
                || startTime.length() == 0)
            return null;
	if (date.length != 3 || date[0].length() != 4 || date[1].length() != 2 || date[2].length() != 2)
		return null;
	if (Integer.parseUnsignedInt(date[0]) > curCalendar.get( Calendar.YEAR ))
		return null;
	if (Integer.parseUnsignedInt(date[1]) > 12 || Integer.parseUnsignedInt(date[2]) > 31)
		return null;
	startTime = date_;

        ComplexMember member = new ComplexMember(name, sectionCode, 
                                                typeOfActivities, trainer, 
                                                startTime, duration, price);
        return member;
    }

    public static void appendFile(ComplexMember cmpMember) throws FileNotFoundException,
		IOException, ClassNotFoundException {
        try (Index idx = Index.load(idxname)) {
            RandomAccessFile raf = new RandomAccessFile(filename, "rw");
            long pos = Buffer.writeObject(raf, cmpMember);
            idx.put(cmpMember, pos);
        }
    }
    
    static Object ParamType(String Key) throws NumberFormatException {
		return Key;
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

	static boolean deleteFile(String Key) throws ClassNotFoundException,
			IOException {
		long[] poss = null;
		try (Index idx = Index.load(idxname)) {
			IndexBase pidx = indexByArg(idx);
			if (pidx.contains(ParamType(Key)) == false) {
				return false;
			}
			poss = pidx.get(ParamType(Key));
			idx.close();
		}
		backup();
		Arrays.sort(poss);
		try (Index idx = Index.load(idxname);
				RandomAccessFile fileBak = new RandomAccessFile(filenameBak,
						"rw");
				RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
			long pos;
			while ((pos = fileBak.getFilePointer()) < fileBak.length()) {
				ComplexMember cmpMember = (ComplexMember) Buffer.readObject(fileBak,
						pos);
				if (Arrays.binarySearch(poss, pos) < 0) {
					long ptr = Buffer.writeObject(file, cmpMember);
					idx.put(cmpMember, ptr);
				}
			}
			idx.close();
		}
		return true;
	}
        
        private static void backup() {
		deleteBackup();
		new File(filename).renameTo(new File(filenameBak));
		new File(idxname).renameTo(new File(idxnameBak));
	}

	private static String printRecord(RandomAccessFile raf, long pos)
			throws ClassNotFoundException, IOException {
                String s;
		ComplexMember cmpMember = (ComplexMember) Buffer.readObject(raf, pos);
		s = " запись на позиции " + pos + ": \n" + cmpMember;
                return s+"\n";
	}
        
        private static String printRecord(RandomAccessFile raf, Object key,
			IndexBase pidx) throws ClassNotFoundException, IOException {
            String s = "";
            long[] poss = pidx.get(key);
		for (long pos : poss) {
			s+="*** Ключ: " + key + " указывает на";
			s+=printRecord(raf, pos);
		}
                return s + "\n";
	}

	static String printFile() throws FileNotFoundException, IOException,
			ClassNotFoundException {
		long pos;
		int rec = 0;
                String s ="";
		try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			while ((pos = raf.getFilePointer()) < raf.length()) {
				s+= "#" + (++rec);
				s+= printRecord(raf, pos);
			}
		}
                return s;
	}
        
        private static IndexBase indexByArg(Index idx) {
		IndexBase pidx = null;
		switch (SortParam)
                {
                    case 1: pidx = idx.Name_i; break;
                    case 2: pidx = idx.Trainer_i; break;
                    case 3: pidx = idx.StartTime_i; break;
                }
		return pidx;	
	}
        
        static String printFile(boolean reverse)
			throws ClassNotFoundException, IOException {
            String s = "";
		try (Index idx = Index.load(idxname);
				RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			IndexBase pidx = indexByArg(idx);
			Object[] keys = pidx.getKeys(reverse ? new KeyCompReverse()
					: new KeyComp());
			for (Object key : keys) {
				s+= printRecord(raf, key, pidx);
			}
		}
		return s;
	}
        
        static String findByKey(String Key) throws ClassNotFoundException,
			IOException {
            String s = "";
		try (Index idx = Index.load(idxname);
				RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			IndexBase pidx = indexByArg(idx);
			if (pidx.contains(ParamType(Key)) == false) {
				return "Нет записи с ключем: " + Key;
			}
			s += printRecord(raf, ParamType(Key), pidx);
		}
		return s;
	}

	static String findByKey(String Key, Comparator<Object> comp)
			throws ClassNotFoundException, IOException {
            String s = "";
		try (Index idx = Index.load(idxname);
				RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			IndexBase pidx = indexByArg(idx);
			if (pidx.contains(ParamType(Key)) == false) 
				return "Нет записи с ключем: " + Key;
			
			Object[] keys = pidx.getKeys(comp);
                for (Object key : keys) {
                    if (key.equals(ParamType(Key)))
                        break;
                    s+=printRecord(raf, key, pidx);
                }
		}
		return s;
	}

    }
