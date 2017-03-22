/**
 * Created by vladasheleg on 16.11.16.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

class ServerThread extends Thread {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private InetAddress address;

    private String userNick;
    private String userFullName;

    private Vector<String> letters = new Vector<>();

    ServerThread(Socket socket) throws IOException {
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        address = socket.getInetAddress();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message msg = (Message) is.readObject();

                switch (msg.getID()) {
                    case Protocol.CMD_CONNECT:
                        connect((MessageConnect) msg);
                        break;
                    case Protocol.CMD_DISCONNECT:
                        synchronized (Server.syncMap) {
                            Server.users.remove(userNick);
                        }
                        break;
                    case Protocol.CMD_USER:
                        user((MessageUser) msg);
                        break;
                    case Protocol.CMD_CHECK_MAIL:
                        checkMail((MessageCheckMail) msg);
                        break;
                    case Protocol.CMD_LETTER:
                        letter((MessageLetter) msg);
                        break;
                    case Protocol.CMD_CONNECT_USER:
                    case Protocol.CMD_DISCONNECT_USER:
                        notify((MessageNotify) msg);
                        break;
                }
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void notify(MessageNotify msg) {
        ServerThread thread;

        String state;
        if (msg.getID() == Protocol.CMD_CONNECT_USER) {
            state = " connected";
        } else {
            state = " disconnected";
        }

        for (String user : Server.users.keySet()) {
            thread = Server.users.get(user);
            if (thread.letters == null) {
                thread.letters = new Vector<>();
            }

            if (!msg.getUserNick().equals(user)) {
                thread.letters.add("> " + msg.getUserNick() + state);
            }
        }
    }


    private boolean connect(MessageConnect msg) throws IOException {
        userNick = msg.getUserNick();
        userFullName = msg.getUserFullName();

        ServerThread old;
        synchronized (Server.syncMap) {
            old = Server.users.get(userNick);
            if (old == null) {
                Server.users.put(userNick, this);
            }
        }

        if (old == null) {
            os.writeObject(new MessageConnectResult());
            return true;
        } else {
            os.writeObject(new MessageConnectResult("> User " + old.userFullName +
                    " already connected as " + userNick));
            return false;
        }
    }

    private void user(MessageUser msg) throws IOException {
        synchronized (Server.syncMap) {
            if (Server.users.isEmpty()) {
                os.writeObject(new MessageUserResult("> Unable to get users list"));
            } else {
                String[] nicks = Server.users.keySet().toArray(new String[Server.users.keySet().size()]);
                os.writeObject(new MessageUserResult(nicks));
            }
        }
    }

    private void letter(MessageLetter msg) throws IOException {
        ServerThread thread;
        synchronized (Server.users) {
            if (Server.users.isEmpty()) {
                os.writeObject(new MessageLetterResult("> Users not found"));
            } else {
                for (String user : Server.users.keySet()) {
                    thread = Server.users.get(user);
                    if (thread.letters == null) {
                        thread.letters = new Vector<>();
                    }

                    thread.letters.add(userNick + ": " + msg.getText());
                }

                os.writeObject(new MessageLetterResult());
            }
        }
    }

    private void checkMail(MessageCheckMail msg) throws IOException {
        String[] lts;
        synchronized (this) {
            lts = letters.toArray(new String[letters.size()]);
//            letters.clear();
        }

        os.writeObject(new MessageCheckMailResult(lts));
    }

    private void disconnect() {
        try {
            System.out.println("> " + address + " disconnected");
            os.close();
            is.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            interrupt();
        }
    }
}