/**
 * Created by vladasheleg on 16.11.16.
 */
import java.io.Serializable;

class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private byte id;

    byte getID() {
        return id;
    }

    protected Message(byte id) {
        assert Protocol.validID(id);
        this.id = id;
    }
}