package global.eska.ddk.api.client.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.Message;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageListener implements Emitter.Listener {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final Middleware middleware;

    @Autowired
    public MessageListener(Middleware middleware) {
        this.middleware = middleware;
    }

    @Override
    public void call(Object... objects) {
        JSONObject obj = (JSONObject) objects[0];
        try {
            Message response = objectMapper.readValue(obj.toString(), Message.class);
            String requestId = middleware.getRequest() != null ? middleware.getRequest().getHeaders().getId() : null;
            String responseId = response.getHeaders().getId();
            if (responseId.equals(requestId)) {
                middleware.onMessage(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

