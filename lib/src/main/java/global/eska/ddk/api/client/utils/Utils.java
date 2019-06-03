package global.eska.ddk.api.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.Headers;
import global.eska.ddk.api.client.model.Message;
import global.eska.ddk.api.client.model.MessageType;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utils {

    private final ObjectMapper objectMapper;

    @Autowired
    public Utils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public JSONObject convertRequestToJsonObject(Message request) {
        JSONObject jsonRequest = null;
        try {
            jsonRequest = new JSONObject(objectMapper.writeValueAsString(request));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonRequest;
    }

    public Message createRequest(ActionMessageCode code, JsonNode data) {
        String uuid = UUID.randomUUID().toString();
        Headers headers = new Headers(uuid, MessageType.REQUEST);
        Message request = new Message(headers, code, data.get("body"));
        return request;
    }


}
