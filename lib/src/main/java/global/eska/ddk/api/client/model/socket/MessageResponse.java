package global.eska.ddk.api.client.model.socket;

import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse extends Message {

    private ResponseEntity body;

    public MessageResponse() {
    }

    public MessageResponse(Headers headers, ActionMessageCode code, ResponseEntity body) {
        super(headers, code);
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "body=" + body +
                "} " + super.toString();
    }
}
