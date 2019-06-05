package global.eska.ddk.api.client.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import global.eska.ddk.api.client.exceptions.ApplicationException;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import global.eska.ddk.api.client.model.MessageType;
import global.eska.ddk.api.client.model.TransactionType;
import global.eska.ddk.api.client.model.socket.MessageRequest;
import global.eska.ddk.api.client.model.socket.ResponseEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Utils {

    public JSONObject convertRequestToJsonObject(MessageRequest request) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageType.class, new MessageTypeSerializer());
        gsonBuilder.registerTypeAdapter(TransactionType.class, new TransactionTypeSerializer());
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(request);
        System.out.println("STRING JSON" + json);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("jsonObj: " + jsonObj);
        return jsonObj;
    }

    public MessageRequest createRequest(ActionMessageCode code, Map<String, Object> data) {
        String uuid = UUID.randomUUID().toString();
        Headers headers = new Headers(uuid, MessageType.REQUEST);
        return new MessageRequest(headers, code, data);
    }

    public <T> T convertMapToObj(ResponseEntity response, Class<T> clazz) throws ApplicationException {
        Gson gson = getGsonBuilder(response);
        String json = gson.toJson(response.getData());
        T t = gson.fromJson(json, clazz);
        return t;
    }


    public <T> T convertMapTrsListToObj(ResponseEntity response, TypeToken<T> typeToken) throws ApplicationException {
        Gson gson = getGsonBuilder(response);
        Map<String, Object> mapData = (Map<String, Object>) response.getData();
        String json = gson.toJson(mapData.get("transactions"));
        T t = gson.fromJson(json, typeToken.getType());
        return t;
    }

    private Gson getGsonBuilder(ResponseEntity response) throws ApplicationException {
        if (response.getErrors().size() != 0) {
            throw new ApplicationException("Errors: " + response.getErrors());
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageType.class, new MessageTypeSerializer());
        gsonBuilder.registerTypeAdapter(TransactionType.class, new TransactionTypeSerializer());
        return gsonBuilder.create();
    }
}
