package global.eska.ddk.api.client.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ToString
public class Message {

    private Headers headers;
    private ActionMessageCode code;
    //TODO temp decision maybe change to generic or another type
    private JsonNode body;

    public Message(Headers headers, ActionMessageCode code, JsonNode body) {
        this.headers = headers;
        this.code = code;
        this.body = body;
    }
}
