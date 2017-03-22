/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageDisconnect extends Message {
    private static final long serialVersionUID = 1L;

    MessageDisconnect() {
        super(Protocol.CMD_DISCONNECT);
    }
}