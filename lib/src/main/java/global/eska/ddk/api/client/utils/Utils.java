package global.eska.ddk.api.client.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import global.eska.ddk.api.client.exceptions.ApplicationException;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import global.eska.ddk.api.client.model.MessageType;
import global.eska.ddk.api.client.model.socket.MessageRequest;
import global.eska.ddk.api.client.model.socket.ResponseEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

public class Utils {

    private final Gson gson;

    public Utils(Gson gson) {
        this.gson = gson;
    }

    public JSONObject convertRequestToJsonObject(MessageRequest request) {
        String json = gson.toJson(request);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public MessageRequest createRequest(ActionMessageCode code, Map<String, Object> data) {
        String uuid = UUID.randomUUID().toString();
        Headers headers = new Headers(uuid, MessageType.REQUEST);
        return new MessageRequest(headers, code, data);
    }

    public <T> T convertMapToObject(ResponseEntity response, Class<T> clazz) throws ApplicationException {
        if (response.getErrors().size() != 0) {
            throw new ApplicationException("Errors: " + response.getErrors());
        }
        String json = gson.toJson(response.getData());
        T t = gson.fromJson(json, clazz);
        return t;
    }


    public <T> T convertMapTrsListToObj(ResponseEntity response, TypeToken<T> typeToken) throws ApplicationException {
        if (response.getErrors().size() != 0) {
            throw new ApplicationException("Errors: " + response.getErrors());
        }
        Map<String, Object> mapData = (Map<String, Object>) response.getData();
        String json = gson.toJson(mapData.get("transactions"));
        T t = gson.fromJson(json, typeToken.getType());
        return t;
    }

}
