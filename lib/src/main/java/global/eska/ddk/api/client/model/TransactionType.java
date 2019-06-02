package global.eska.ddk.api.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    REGISTER(0),
    SEND(10),
    SIGNATURE(20),
    DELEGATE(30),
    STAKE(40),
    VOTE(60);


    private final int typeNumber;

    TransactionType(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    @JsonCreator
    public static TransactionType getNameByValue(final int value) {
        for (final TransactionType s : TransactionType.values()) {
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
