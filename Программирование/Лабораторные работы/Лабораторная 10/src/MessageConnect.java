/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageConnect extends Message {
    private static final long serialVersionUID = 1L;

    private String userNick;
    private String userFullName;

    String getUserNick() {
        return userNick;
    }

    String getUserFullName() {
        return userFullName;
    }

    MessageConnect(String userNick, String userFullName) {
        super(Protocol.CMD_CONNECT);
        this.userNick = userNick;
        this.userFullName = userFullName;
    }
}