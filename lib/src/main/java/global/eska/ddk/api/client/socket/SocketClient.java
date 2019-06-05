package global.eska.ddk.api.client.socket;

import global.eska.ddk.DDKSocketClientConfiguration;
import global.eska.ddk.api.client.listeners.MessageListener;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.socket.MessageResponse;
import global.eska.ddk.api.client.model.socket.ResponseEntity;
import global.eska.ddk.api.client.service.Blocker;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.Map;

public class SocketClient implements DDKSocket {

    private Socket socket;
    private final Middleware middleware;
    private final MessageListener messageListener;
    private final Blocker blocker;
    private final DDKSocketClientConfiguration ddkSocketClientConfiguration;

    public SocketClient(Middleware middleware, MessageListener messageListener, Blocker blocker, DDKSocketClientConfiguration ddkSocketClientConfiguration) {
        this.middleware = middleware;
        this.messageListener = messageListener;
        this.blocker = blocker;
        this.ddkSocketClientConfiguration = ddkSocketClientConfiguration;
        connect();
    }

    private void connect() {
        try {
            System.out.println("Socket init");
            socket = IO.socket(ddkSocketClientConfiguration.getUrl());
            socket.on(Socket.EVENT_MESSAGE, messageListener);
            socket.connect();
            System.out.println("Socket connected");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(ActionMessageCode code, Map<String, Object> data) {
        middleware.send(socket, code, data);
        blocker.lock();
    }

    public ResponseEntity request(ActionMessageCode code, Map<String, Object> data) {
        middleware.send(socket, code, data);
        blocker.lock();
        MessageResponse response = middleware.getResponse();
        middleware.clear();
        return response.getBody();
    }

    @Override
    public void clear() {
        middleware.clear();
    }

}
