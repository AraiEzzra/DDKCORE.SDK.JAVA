package global.eska.ddk.api.client.model;

public enum TransactionType {
    REGISTER(0),
    SEND(10),
    SIGNATURE(20),
    DELEGATE(30),
    STAKE(40),
    VOTE(60);


    private final int key;

    TransactionType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public static TransactionType fromKey(int key) {
        for (TransactionType type : TransactionType.values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        return null;
    }
}
