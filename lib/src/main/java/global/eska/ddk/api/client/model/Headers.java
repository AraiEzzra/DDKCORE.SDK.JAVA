package global.eska.ddk.api.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Headers {

    private String id;
    private MessageType type;

    public Headers() {
    }

    public Headers(String id, MessageType type) {
        this.id = id;
        this.type = type;
    }
}
