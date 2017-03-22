/**
 * Created by vladasheleg on 16.11.16.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class Server {
    public static void main(String[] args) {
        System.out.println("Binding to port " + Protocol.PORT + ", please wait...");
        try (ServerSocket serverSocket = new ServerSocket(Protocol.PORT)) {
            System.out.println("Server started: " +
                    serverSocket.getInetAddress() + ", " + serverSocket.getLocalPort());

            while (true) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress() + " connected");
                ServerThread server = new ServerThread(socket);
                server.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    static final Object syncMap = new Object();
    static final Map<String, ServerThread> users = new TreeMap<>();
}
