package global.eska.ddk.api.client.socket;

import global.eska.ddk.api.client.listeners.MessageListener;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.socket.Message;
import global.eska.ddk.api.client.model.socket.MessageResponse;
import global.eska.ddk.api.client.model.socket.ResponseEntity;
import global.eska.ddk.api.client.service.Blocker;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.Map;

@Component
public class SocketClient implements DDKSocket {

    private Socket socket;
    private final String url = "http://31.28.161.187:7008";
//    private final String url = "http://localhost:7008";
    private final Middleware middleware;
    private final MessageListener messageListener;
    @Autowired
    private Blocker blocker;

    @Autowired
    public SocketClient(Middleware middleware, MessageListener messageListener) {
        this.middleware = middleware;
        this.messageListener = messageListener;
        connect();
    }

    private void connect() {
        try {
            System.out.println("Socket init");
            socket = IO.socket(url);
            socket.on(Socket.EVENT_MESSAGE, messageListener);
            socket.connect();
            System.out.println("Socket connected");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public MessageResponse getResponse() {
        return middleware.getResponse();
    }

    public ResponseEntity getResponseData() {
        MessageResponse response = middleware.getResponse();
        return response.getBody();
    }

    @Override
    public void send(ActionMessageCode code, Map<String, Object> data) {
        middleware.send(socket, code, data);
        blocker.lock();
    }

    @Override
    public void clear() {
        middleware.clear();
    }

}
