package global.eska.ddk.api.client.middleware;

import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.socket.MessageResponse;
import io.socket.client.Socket;

import java.util.Map;

public interface DDKMiddleware {

    void send(Socket socket, ActionMessageCode code, Map<String, Object> data);

    void onMessage(MessageResponse data);

    MessageResponse getResponse();
}

