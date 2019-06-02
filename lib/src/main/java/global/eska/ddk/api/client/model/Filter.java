package global.eska.ddk.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
