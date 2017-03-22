/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageLetter extends Message {
    private static final long serialVersionUID = 1L;

    private String text;

    String getText() {
        return text;
    }

    MessageLetter(String text) {
        super(Protocol.CMD_LETTER);
        this.text = text;
    }
}