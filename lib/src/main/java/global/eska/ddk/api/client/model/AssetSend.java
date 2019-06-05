package global.eska.ddk.api.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssetSend {
    private String recipientAddress;
    private Long amount;
}
