import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    static String name;


    public Client(String name) {
        this.name = name;
    }

    public static void main(String[] argv) throws RemoteException, NotBoundException, MalformedURLException {

//        String hostname = argv[0];


        String answer;
        Scanner fin = new Scanner(System.in);
        System.out.println("Input name:");
        name = fin.nextLine();
        Registry reg = LocateRegistry.getRegistry();
        TimeTracking stub = (TimeTracking) Naming.lookup("rmi://" + "127.0.0.1" + ":2020/" + "TimeTracking");

        do {
            System.out.println("1. Set conection\n2. Any Request\n3. Exi\nInput answer:");
            answer = fin.nextLine();

            switch (answer) {
                case "1":
                    System.out.println("Result:    " + stub.setWorker(name));
                    break;
                case "2":
                    System.out.println("Result:    " + stub.anyRequest(name));
                    break;
                case "3":
                    System.out.println("Result:    " + stub.deleteWorker(name));
                    break;
                default:
                    System.out.println("You haven't chosen action.\n");
            }
        } while (!answer.equals("3"));

    }
}
