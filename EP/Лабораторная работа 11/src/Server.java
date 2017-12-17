import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Created by АДМИН on 01.05.2017.
 */
public class Server implements TimeTracking {
    HashMap<String, Long> workers;
    static int countRequest;

    protected Server() throws RemoteException {
        workers = new HashMap<>();
        countRequest = 0;
    }

    @Override
    public String anyRequest(String name) throws RemoteException {
        int res = updateTime(name);
        switch (res) {
            case 1:
                return "You are disconnected. (You weren't active within 5 minutes)";
            case 0:
                countRequest++;
                return "Number request: " + countRequest + " Name: " + name + " Time: " + System.currentTimeMillis();
            case -1:
                return "There is no worker with such name. Setup connection";
        }
        return "Error";
    }

    @Override
    public String setWorker(String name) throws RemoteException {
        if (!workers.containsKey(name)) {
            Long time = System.currentTimeMillis();
            workers.put(name, time);
            try (FileWriter fin = new FileWriter(new File("workers.txt"), true)) {
                fin.write("\nWorker: " + name + " is registred in " + time);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Addition is executed successfully!";
        } else
            return "Faild. The worker with such name already is!";
    }

    @Override
    public String deleteWorker(String name) throws RemoteException {
        if (workers.containsKey(name)) {
            workers.remove(name);
            Long time = System.currentTimeMillis();
            try (FileWriter fin = new FileWriter(new File("workers.txt"), true)) {
                fin.write("\nWorker: " + name + " is delete in " + time);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Removal is executed successfully!";
        }
        return "Failed! The worker with such name isn't found!";
    }

    private int updateTime(String name) {
        if (workers.containsKey(name)) {
            Long lastTime = workers.get(name);
            Long currentTime = System.currentTimeMillis();

            Long min = currentTime - lastTime;
            if (min / 60000 >= 1) {
                removeWorker(name);
                return 1;
            } else
                workers.put(name, currentTime);
            return 0;
        }
        return -1;
    }

    private boolean removeWorker(String name) {
        if (workers.containsKey(name)) {
            workers.remove(name);
            Long time = System.currentTimeMillis();
            try (FileWriter fin = new FileWriter(new File("workers.txt"), true)) {
                fin.write("\nWorker: " + name + " is remove in " + time);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


}
