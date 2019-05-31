package global.eska.ddk.api.client.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.listeners.MessageListener;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Message;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;


@Component
public class SocketClient implements DDKSocket {

    private Socket socket;
    private final String url = "http://31.28.161.187:7008";
    private final Middleware middleware;

    private final MessageListener messageListener;

    ObjectMapper objectMapper = new ObjectMapper();

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

    public Message getResponse(){
        return middleware.getResponse();
    }

    @Override
    public Socket getConnection() {
        return null;
    }

    @Override
    public void send(ActionMessageCode code, JsonNode data) {
        middleware.send(socket, code, data);
    }

    @Override
    public void clear() {
        middleware.clear();
    }

}
