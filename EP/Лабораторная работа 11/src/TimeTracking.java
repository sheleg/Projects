import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by АДМИН on 01.05.2017.
 */
public interface TimeTracking extends Remote{
    String anyRequest(String name) throws RemoteException;
    String setWorker(String name) throws RemoteException;
    String deleteWorker(String name) throws RemoteException;
}
