/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageUser extends Message {
    private static final long serialVersionUID = 1L;

    MessageUser() {
        super(Protocol.CMD_USER);
    }
}