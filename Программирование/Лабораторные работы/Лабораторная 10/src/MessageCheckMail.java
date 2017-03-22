/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageCheckMail extends Message {
    private static final long serialVersionUID = 1L;

    MessageCheckMail() {
        super(Protocol.CMD_CHECK_MAIL);
    }
}