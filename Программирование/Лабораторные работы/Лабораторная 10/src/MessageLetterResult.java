/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageLetterResult extends MessageResult {
    private static final long serialVersionUID = 1L;

    MessageLetterResult(String errorMessage) { //error
        super(Protocol.CMD_LETTER, errorMessage);
    }

    MessageLetterResult() { // No errors
        super(Protocol.CMD_LETTER);
    }
}