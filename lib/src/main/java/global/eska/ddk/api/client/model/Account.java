package global.eska.ddk.api.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {

    private BigDecimal address;
    private String publicKey;
    private String secondPublicKey;
    private Long actualBalance;
    private Delegate delegate;
    private String[] referrals;
    private List<Stake> stakes;

}
