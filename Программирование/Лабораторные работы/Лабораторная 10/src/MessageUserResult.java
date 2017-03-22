/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageUserResult extends MessageResult {
    private static final long serialVersionUID = 1L;

    private String[] userNicks = null;

    String[] getUserNicks() {
        return userNicks;
    }

    MessageUserResult(String errorMessage) { // error
        super(Protocol.CMD_USER, errorMessage);
    }

    MessageUserResult(String[] userNicks) { // No errors
        super(Protocol.CMD_USER);
        this.userNicks = userNicks;
    }
}
