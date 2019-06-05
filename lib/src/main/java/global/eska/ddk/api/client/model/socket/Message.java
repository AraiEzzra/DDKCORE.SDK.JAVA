package global.eska.ddk.api.client.model.socket;

import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
public class Message {

    private Headers headers;
    private ActionMessageCode code;

    public Message() {
    }

    public Message(Headers headers, ActionMessageCode code) {
        this.headers = headers;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Message{" +
                "headers=" + headers +
                ", code=" + code +
                '}';
    }
}


