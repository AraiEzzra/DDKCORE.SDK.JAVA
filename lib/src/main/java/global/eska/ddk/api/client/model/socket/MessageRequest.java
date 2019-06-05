package global.eska.ddk.api.client.model.socket;

import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MessageRequest extends Message {

    private Map<String, Object> body;

    public MessageRequest() {
    }

    public MessageRequest(Headers headers, ActionMessageCode code, Map<String, Object> body) {
        super(headers, code);
        this.body = body;
    }


    @Override
    public String toString() {
        return "MessageRequest{" +
                "body=" + body +
                '}';
    }
}
