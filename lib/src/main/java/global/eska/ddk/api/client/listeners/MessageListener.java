package global.eska.ddk.api.client.listeners;

import com.google.gson.Gson;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.socket.Message;
import global.eska.ddk.api.client.model.socket.MessageResponse;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

public class MessageListener implements Emitter.Listener {

    private final Middleware middleware;
    private final Gson gson;

    public MessageListener(Middleware middleware, Gson gson) {
        this.middleware = middleware;
        this.gson = gson;
    }

    @Override
    public void call(Object... objects) {
        Message request = middleware.getRequest();
        JSONObject jsonObject = (JSONObject) objects[0];
        MessageResponse response = gson.fromJson(jsonObject.toString(), MessageResponse.class);
        String requestId = request != null ? request.getHeaders().getId() : null;
        String responseId = response.getHeaders().getId();
        if (responseId.equals(requestId)) {
            middleware.onMessage(response);
        }
    }
}

