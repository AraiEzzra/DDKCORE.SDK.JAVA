package global.eska.ddk.api.client.middleware;

import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.socket.MessageRequest;
import global.eska.ddk.api.client.model.socket.MessageResponse;
import global.eska.ddk.api.client.service.Blocker;
import global.eska.ddk.api.client.utils.Utils;
import io.socket.client.Socket;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Middleware implements DDKMiddleware {

    private final Blocker blocker;
    private final Utils utils;
    private MessageRequest request;
    private MessageResponse response;

    public Middleware(Blocker blocker, Utils utils) {
        this.blocker = blocker;
        this.utils = utils;
    }

    @Override
    public void send(Socket socket, ActionMessageCode code, Map<String, Object> data) {
        MessageRequest request = utils.createRequest(code, data);
        setRequest(request);
        socket.emit(Socket.EVENT_MESSAGE, utils.convertRequestToJsonObject(request));
    }

    @Override
    public void onMessage(MessageResponse response) {
        this.response = response;
        blocker.unlock();
    }

    @Override
    public MessageResponse getResponse() {
        return response;
    }

    public void clear() {
        request = null;
        response = null;
    }


    public Blocker getBlocker() {
        return blocker;
    }
}
