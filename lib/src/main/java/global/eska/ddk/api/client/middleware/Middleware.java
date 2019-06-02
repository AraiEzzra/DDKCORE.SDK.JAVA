package global.eska.ddk.api.client.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import global.eska.ddk.api.client.model.Message;
import global.eska.ddk.api.client.model.MessageType;
import global.eska.ddk.api.client.service.Blocker;
import io.socket.client.Socket;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Setter
@Service
public class Middleware implements DDKMiddleware {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Blocker blocker;
    private Message request;
    private Message response;

    public void send(Socket socket, ActionMessageCode code, JsonNode data) {
        Message request = createRequest(code, data);
        socket.emit(Socket.EVENT_MESSAGE, wrapRequestToJsonObject(request));
    }

    private JSONObject wrapRequestToJsonObject(Message request) {
        JSONObject jsonRequest = null;
        try {
            jsonRequest = new JSONObject(objectMapper.writeValueAsString(request));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonRequest;
    }

    private Message createRequest(ActionMessageCode code, JsonNode data) {
        String uuid = UUID.randomUUID().toString();
        Headers headers = new Headers(uuid, MessageType.REQUEST);
        Message request = new Message(headers, code, data.get("body"));
        setRequest(request);
        return request;
    }

    @Override
    public void onMessage(Message response) {
        this.response = response;
        blocker.unlock();
    }

    @Override
    public Message getResponse() {
        return response;
    }

    public void clear() {
        request = null;
        response = null;
    }
}
