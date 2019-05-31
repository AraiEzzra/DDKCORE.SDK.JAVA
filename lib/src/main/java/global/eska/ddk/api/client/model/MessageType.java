package global.eska.ddk.api.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {
    REQUEST(1),
    RESPONSE(2),
    EVENT(3);


    private final int typeNumber;

    MessageType(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    @JsonCreator
    public static MessageType getNameByValue(final int value) {
        for (final MessageType s : MessageType.values()) {
            if (s.typeNumber == value) {
                return s;
            }
        }
        return null;
    }

    @JsonValue
    public int getTypeNumber() {
        return typeNumber;
    }
}
