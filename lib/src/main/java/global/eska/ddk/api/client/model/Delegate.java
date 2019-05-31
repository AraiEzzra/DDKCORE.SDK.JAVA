package global.eska.ddk.api.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Delegate {

    private String userName;
    private String url;
    private Integer missedBlocks;
    private Integer forgedBlocks;
    private Account account;
    private Integer votes;

}
