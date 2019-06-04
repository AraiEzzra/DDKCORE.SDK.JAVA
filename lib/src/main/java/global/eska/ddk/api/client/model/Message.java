package global.eska.ddk.api.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Component
@ToString
public class Message {

    private Headers headers;
    private ActionMessageCode code;
    private Map<String, Object> body;

    public Message(Headers headers, ActionMessageCode code, Map<String, Object> body) {
        this.headers = headers;
        this.code = code;
        this.body = body;
    }
}
