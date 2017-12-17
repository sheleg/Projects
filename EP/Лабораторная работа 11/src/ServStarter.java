import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by АДМИН on 02.05.2017.
 */
public class ServStarter extends UnicastRemoteObject {

    String  userName;

    public ServStarter(String userName) throws RemoteException {
        this.userName = userName;
    }

    protected ServStarter() throws RemoteException {
    }

    public static void main(String[] argv) throws RemoteException, AlreadyBoundException, MalformedURLException {
        Server obj = new Server();
        TimeTracking stub = (TimeTracking) UnicastRemoteObject.exportObject(obj, 0);
        LocateRegistry.createRegistry(2030);
        Naming.rebind("rmi://" + "127.0.0.1" + ":2030/" + "TimeTracking" , stub);
//        Naming.rebind("rmi://" + "127.0.0.1" + ":2020/" + "TimeTracking" , stub);
        System.out.println("Connecting");
    }
}
