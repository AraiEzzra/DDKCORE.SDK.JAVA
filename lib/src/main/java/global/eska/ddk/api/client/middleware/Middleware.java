package global.eska.ddk.api.client.middleware;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import global.eska.ddk.api.client.model.Message;
import global.eska.ddk.api.client.model.MessageType;
import io.socket.client.Socket;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Getter
@Setter
@Service
public class Middleware implements DDKMiddleware {

    // TODO make look up method for CountDownLatch
    @Autowired
    private CountDownLatch doneSignal;
    private Message request;
    private Message response;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void send(Socket socket, ActionMessageCode code, JsonNode data) {
        String uuid = UUID.randomUUID().toString();
        Headers headers = new Headers(uuid, MessageType.REQUEST);
        Message request = new Message(headers, code, data.get("body"));
        setRequest(request);
        try {
            JSONObject jsonRequest = new JSONObject(objectMapper.writeValueAsString(request));
            socket.emit(Socket.EVENT_MESSAGE, jsonRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message response) {
        this.response = response;
        System.out.println("doneSignal.countDown111: " + doneSignal.getCount());
        doneSignal.countDown();
        System.out.println("doneSignal.countDown222: " + doneSignal.getCount());

    }

    @Override
    public Message getResponse() {
        System.out.println("getResponse: " + response);
        return response;
    }

    public void clear() {
        request = null;
        response = null;
    }
}
