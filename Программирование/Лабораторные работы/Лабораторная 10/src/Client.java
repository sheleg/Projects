/**
 * Created by vladasheleg on 16.11.16.
 */

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class Client {
    private static Map<String, Byte> commands = new TreeMap<>();

    public static void main(String[] args) {
        if (args.length >= 3 && args.length <= 4) {
            try (Socket socket = args.length == 3 ?
                    new Socket(InetAddress.getLocalHost(), Integer.parseInt(args[2])) :
                    new Socket("FPMI507NB41", Integer.parseInt(args[3]))) {
                System.out.println("> Initialized");
                session(socket, args[0], args[1]);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                System.err.println("> Goodbye!");
            }
        } else {
            System.err.println("Invalid number of arguments!\n" +
                    "Usage: nick name [host] [port]");
        }
    }

    private static void session(Socket socket, String nick, String name) {
        ObjectInputStream is = null;
        ObjectOutputStream os = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

            Session session = new Session(nick, name);
            if (openSession(session, is, os)) {
                Message message;

                do {
                    message = getCommand(session, reader);
                } while (processCommand(message, is, os));
                closeSession(session, os);
            }
        } catch (ClassNotFoundException e) {
            try {
                os.close();
                is.close();
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    private static boolean openSession(Session session, ObjectInputStream is, ObjectOutputStream os)
            throws IOException, ClassNotFoundException {
        os.writeObject(new MessageConnect(session.userNick, session.userName));
        os.writeObject(new MessageConnectUser(session.userNick));

        MessageConnectResult result = (MessageConnectResult) is.readObject();
        if (!result.error()) {
            System.out.println("> Connected");
            session.connected = true;

            return true;
        } else {
            System.err.println("> Unable to connect: " + result.getErrorMessage());
            return false;
        }
    }

    private static void closeSession(Client.Session session, ObjectOutputStream os) throws IOException {
        if (session.connected) {
            session.connected = false;
            os.writeObject(new MessageDisconnect());
            os.writeObject(new MessageDisconnectUser(session.userNick));
        }
    }

    private static Message getCommand(Session session, BufferedReader reader) throws IOException {
        while (true) {
            printPrompt();

            String message = reader.readLine();
            byte id = translateCmd(message);

            switch (id) {
                case Protocol.CMD_CONNECT:
                    return new MessageConnect(session.userNick, session.userName);
                case Protocol.CMD_DISCONNECT:
                    return new MessageDisconnect();
                case Protocol.CMD_USER:
                    return new MessageUser();
                case Protocol.CMD_CHECK_MAIL:
                    return new MessageCheckMail();
                case Protocol.CMD_LETTER:
                    return inputLetter(reader);
                default:
                    System.err.println("> Unknown command");
            }
        }
    }

    private static MessageLetter inputLetter(BufferedReader reader) throws IOException {
        System.out.print("> Enter the message: ");
        return new MessageLetter(reader.readLine());
    }

    private static byte translateCmd(String cmd) {
        Byte command = commands.get(cmd.trim());
        return command == null ? 0 : (byte) command;
    }

    private static void printPrompt() {
        System.out.println();
        System.out.print("> (q)uit / (u)sers / (m)ail / (l)etter > ");
    }

    private static boolean processCommand(Message message, ObjectInputStream is, ObjectOutputStream os)
            throws IOException, ClassNotFoundException {
        if (message != null && message.getID() != CMD.CMD_DISCONNECT) {
            os.writeObject(message);

            MessageResult result = (MessageResult) is.readObject();
            if (result.error()) {
                System.err.println(result.getErrorMessage());
            } else {
                switch (result.getID()) {
                    case Protocol.CMD_USER:
                        printUsers((MessageUserResult) result);
                        break;
                    case Protocol.CMD_CHECK_MAIL:
                        printMail((MessageCheckMailResult) result);
                        break;
                    case Protocol.CMD_LETTER:
                        System.out.println("> Letter sent");
                        break;
                    default:
                        assert false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private static void printMail(MessageCheckMailResult result) {
        String[] letters = result.getLetters();
        if (letters.length > 0) {
            System.out.println("> Your mail:");
            for (String letter : letters) {
                System.out.println("\t" + letter);
            }
        } else {
            System.out.println("> No mail");
        }
    }

    private static void printUsers(MessageUserResult result) {
        String[] userNicks = result.getUserNicks();
        if (userNicks != null) {
            System.out.println("> Users: ");
            for (String nickName : userNicks) {
                System.out.println("\t" + nickName);
            }
        }
    }

    static {
        commands.put("q", Protocol.CMD_DISCONNECT);
        commands.put("quit", Protocol.CMD_DISCONNECT);
        commands.put("u", Protocol.CMD_USER);
        commands.put("users", Protocol.CMD_USER);
        commands.put("m", Protocol.CMD_CHECK_MAIL);
        commands.put("mail", Protocol.CMD_CHECK_MAIL);
        commands.put("l", Protocol.CMD_LETTER);
        commands.put("letter", Protocol.CMD_LETTER);
    }

    private static class Session {
        private boolean connected = false;
        private String userNick;
        private String userName;

        Session(String userNick, String userName) {
            this.userNick = userNick;
            this.userName = userName;
        }
    }
}