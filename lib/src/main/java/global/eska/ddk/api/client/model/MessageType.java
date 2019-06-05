package global.eska.ddk.api.client.model;

public enum MessageType {
    REQUEST(1),
    RESPONSE(2),
    EVENT(3);

    private final int key;

    MessageType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public static MessageType fromKey(int key) {
        for (MessageType type : MessageType.values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        return null;
    }

}


