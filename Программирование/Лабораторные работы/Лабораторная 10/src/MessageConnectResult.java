/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageConnectResult extends MessageResult {
    private static final long serialVersionUID = 1L;

    MessageConnectResult(String errorMessage) { // error
        super(Protocol.CMD_CONNECT, errorMessage);
    }

    MessageConnectResult() { // No error
        super(Protocol.CMD_CONNECT);
    }
}