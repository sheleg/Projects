import java.io.*;

/**
 * Created by vladasheleg on 21.10.16.
 */
public class Connector {
    public static final String path = "data.dat";

    public static void write(Object object) {
        try(ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(path))) {
            fout.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object read() {
        try(ObjectInputStream fin = new ObjectInputStream(new FileInputStream(path))) {
            return fin.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
