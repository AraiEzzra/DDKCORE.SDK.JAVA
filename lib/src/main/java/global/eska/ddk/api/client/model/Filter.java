package global.eska.ddk.api.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filter {

    private TransactionType type;
    private String blockId;
    private String senderPublicKey;

    @Override
    public String toString() {
        return "Filter{" +
                "type=" + type +
                ", blockId='" + blockId + '\'' +
                ", senderPublicKey='" + senderPublicKey + '\'' +
                '}';
    }
}
