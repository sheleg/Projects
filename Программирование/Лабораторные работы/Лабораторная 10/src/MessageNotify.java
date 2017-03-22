/**
 * Created by vladasheleg on 16.11.16.
 */
abstract class MessageNotify extends Message {
    private static final long serialVersionUID = 1L;

    private String userNick;

    String getUserNick() {
        return userNick;
    }

    MessageNotify(String userNick, byte id) {
        super(id);
        this.userNick = userNick;
    }
}