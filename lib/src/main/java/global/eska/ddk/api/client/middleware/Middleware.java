package global.eska.ddk.api.client.middleware;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import global.eska.ddk.api.client.model.Message;
import global.eska.ddk.api.client.model.MessageType;
import global.eska.ddk.api.client.service.Blocker;
import global.eska.ddk.api.client.utils.Utils;
import io.socket.client.Socket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Setter
@Service
public class Middleware implements DDKMiddleware {

    private final ObjectMapper objectMapper;
    private final Blocker blocker;
    private final Utils utils;
    private Message request;
    private Message response;

    @Autowired
    public Middleware(ObjectMapper objectMapper, Blocker blocker, Utils utils) {
        this.objectMapper = objectMapper;
        this.blocker = blocker;
        this.utils = utils;
    }

    public void send(Socket socket, ActionMessageCode code, JsonNode data) {
        Message request = utils.createRequest(code, data);
        setRequest(request);
        socket.emit(Socket.EVENT_MESSAGE, utils.convertRequestToJsonObject(request));
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
