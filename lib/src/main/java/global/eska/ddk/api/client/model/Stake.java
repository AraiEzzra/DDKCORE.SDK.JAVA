package global.eska.ddk.api.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@ToString
public class Stake {

    private Integer createdAt;
    private Boolean isActive;
    private Long amount;
    private Integer voteCount;
    private Long nextVoteMilestone;
    private Map<BigDecimal, Integer> airdropReward;
    private String sourceTransactionId;
}
