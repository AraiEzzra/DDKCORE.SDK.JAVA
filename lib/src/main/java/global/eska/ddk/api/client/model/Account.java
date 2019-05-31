package global.eska.ddk.api.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

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
    private Stake[] stakes;

    public Account(BigDecimal address, String publicKey, String secondPublicKey, Long actualBalance, Delegate delegate, String[] referrals, Stake[] stakes) {
        this.address = address;
        this.publicKey = publicKey;
        this.secondPublicKey = secondPublicKey;
        this.actualBalance = actualBalance;
        this.delegate = delegate;
        this.referrals = referrals;
        this.stakes = stakes;
    }

}
