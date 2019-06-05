package global.eska.ddk.api.client.listeners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.MessageType;
import global.eska.ddk.api.client.model.TransactionType;
import global.eska.ddk.api.client.model.socket.MessageResponse;
import global.eska.ddk.api.client.utils.MessageTypeSerializer;
import global.eska.ddk.api.client.utils.TransactionTypeSerializer;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener implements Emitter.Listener {

    private final Middleware middleware;

    @Autowired
    public MessageListener(Middleware middleware) {
        this.middleware = middleware;
    }

    @Override
    public void call(Object... objects) {
//        JSONObject obj = (JSONObject) objects[0];
//        try {
//            System.out.println("MessageListener1 : "+ obj.toString());
//            MessageResponse response = objectMapper.readValue(obj.toString(), new TypeReference<MessageResponse>(){});
//            System.out.println("MessageListener3: "+ response);
//            String requestId = middleware.getRequest() != null ? middleware.getRequest().getHeaders().getId() : null;
//            String responseId = response.getHeaders().getId();
//            if (responseId.equals(requestId)) {
//                System.out.println("MessageListener2 : "+ response);
//                middleware.onMessage(response);
//            }
//        } catch (IOException e) {
//            middleware.getBlocker().unlock();
//            e.printStackTrace();
//        }
        JSONObject jsonObject = (JSONObject) objects[0];
        System.out.println("MessageListener1 : " + jsonObject);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageType.class, new MessageTypeSerializer());
        gsonBuilder.registerTypeAdapter(TransactionType.class, new TransactionTypeSerializer());
        Gson gson = gsonBuilder.create();

        System.out.println("MessageListener2.1 : ");
        MessageResponse response = gson.fromJson(jsonObject.toString(), MessageResponse.class);
        System.out.println("MessageListener2.2 : " + response);

        String requestId = middleware.getRequest() != null ? middleware.getRequest().getHeaders().getId() : null;
        String responseId = response.getHeaders().getId();
        if (responseId.equals(requestId)) {
            System.out.println("MessageListener3 : " + response);
            middleware.onMessage(response);
        }
    }
}

