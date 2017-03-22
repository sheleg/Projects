/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageCheckMailResult extends MessageResult {
    private static final long serialVersionUID = 1L;

    private String[] letters = null;

    String[] getLetters() {
        return letters;
    }

    MessageCheckMailResult(String errorMessage) { // error
        super(Protocol.CMD_CHECK_MAIL, errorMessage);
    }

    MessageCheckMailResult(String[] letters) { // No errors
        super(Protocol.CMD_CHECK_MAIL);
        this.letters = letters;
    }
}