import java.io.*;
import java.util.*;

class KeyComp implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        // right order:	
        if (o1 instanceof String && o2 instanceof String)
        	return ((String)o1).compareTo((String)o2);
        else
        	if(o1 instanceof Long && o2 instanceof Long)
        		return Long.compare((long)o1, (long)o2);
        	else
        		if (o1 instanceof Integer && o2 instanceof Integer)
        			return (int)o1- (int)o2;
        		else
        			return 0;
    }
}

class KeyCompReverse implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        // right order:	
        if (o1 instanceof String && o2 instanceof String)
        	return ((String)o2).compareTo((String)o1);
        else
        	if(o1 instanceof Long && o2 instanceof Long)
        		return Long.compare((long)o2, (long)o1);
        	else
        		if (o1 instanceof Integer && o2 instanceof Integer)
        			return (int)o2- (int)o1;
        		else
        			return 0;
    }
}

interface IndexBase {
    Object[] getKeys( Comparator<Object> comp );
    void put( Object key, long value );
    boolean contains( Object key );
    long[] get( Object key );
}

class MultiIndex implements Serializable, IndexBase {
    // Not unique keys
    // class release version:
    private static final long serialVersionUID = 1L;

    private final TreeMap<Object, long[]> map;
	
    public MultiIndex() {
        map = new TreeMap<> ();
    }
	
    @Override
    public Object[] getKeys( Comparator<Object> comp ) {
        Object[] result = map.keySet().toArray();
        Arrays.sort( result, comp );
        return result;
    }
	
    @Override
    public void put( Object key, long value ) {
        long[] arr = map.get(key);
        arr = ( arr != null ) ? Index.InsertValue( arr, value ) : new long[] {value};		
        map.put(key, arr);
    }
	
    @Override
    public boolean contains(Object  key ) {
        return map.containsKey(key);
    }
	
    @Override
    public long[] get( Object key ) {
        return map.get( key );
    }
}

public class Index implements Serializable, Closeable {
    // class release version:
    private static final long serialVersionUID = 1L;

    public static long[] InsertValue( long[] arr, long value ) {
        int length = ( arr == null ) ? 0 : arr.length;
        long [] result = new long[length + 1];
        System.arraycopy(arr, 0, result, 0, length);
        result[length] = value;
        return result;
    }
		
    MultiIndex Name_i;
    MultiIndex Trainer_i;
    MultiIndex StartTime_i;
    
    public void put( ComplexMember cmpMember, long value ){
        Name_i.put( cmpMember.name, value );
        Trainer_i.put( cmpMember.trainer, value);
        StartTime_i.put( cmpMember.startTime, value);		
    }
	
    public Index()  {
        Name_i = new MultiIndex();
        Trainer_i = new MultiIndex();
        StartTime_i= new MultiIndex();
        
    }
	
    public static Index load( String name ) 
            throws IOException, ClassNotFoundException {
        Index obj = null;
        try {
            FileInputStream file = new FileInputStream( name );
            try ( ObjectInputStream ois = new ObjectInputStream( file )) {
                   obj = (Index)ois.readObject();
            }
        } catch ( FileNotFoundException e ) {
            obj = new Index();
        }
        if ( obj != null ) {
            obj.save( name );
        }
        return obj;
    }
	
    private transient String filename = null; 
	
    public void save( String name ) {
        filename = name;
    }
	
    public void saveAs( String name ) throws IOException {
        FileOutputStream fos = new FileOutputStream( name );
       try ( ObjectOutputStream oos = new ObjectOutputStream( fos )) {
           oos.writeObject(this);
                oos.flush();
                oos.close();
            }
        }
	
    @Override
    public void close() throws IOException {
        saveAs( filename );
    }
}
