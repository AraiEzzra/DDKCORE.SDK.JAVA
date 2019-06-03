package global.eska.ddk.api.client.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction<T> {

    private String id;
    private String blockId;
    private TransactionType type;
    private String senderPublicKey;
    private String senderAddress;
    private String signature;
    private String secondSignature;
    private Long createdAt;
    private Long fee;
    private TransactionStatus status;
    private String salt;
    private Integer relay;
    private Integer confirmations;
    private T asset;
}
