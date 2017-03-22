/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageConnectUser extends MessageNotify {
    private static final long serialVersionUID = 1L;

    MessageConnectUser(String userNick) {
        super(userNick, Protocol.CMD_CONNECT_USER);
    }
}