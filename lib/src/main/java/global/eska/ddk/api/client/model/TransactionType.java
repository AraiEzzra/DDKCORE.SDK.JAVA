package global.eska.ddk.api.client.model;

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
}
