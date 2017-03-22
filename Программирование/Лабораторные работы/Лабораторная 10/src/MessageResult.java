/**
 * Created by vladasheleg on 16.11.16.
 */
class MessageResult extends Message {
    private static final long serialVersionUID = 1L;

    private int errorCode;
    private String errorMessage;

    int getErrorCode() {
        return errorCode;
    }

    boolean error() {
        return errorCode != Protocol.RESULT_CODE_OK;
    }

    String getErrorMessage() {
        return errorMessage;
    }

    protected MessageResult(byte id, String errorMessage) {
        super(id);
        this.errorCode = Protocol.RESULT_CODE_ERROR;
        this.errorMessage = errorMessage;
    }

    protected MessageResult(byte id) {
        super(id);
        this.errorCode = Protocol.RESULT_CODE_OK;
        this.errorMessage = "";
    }
}