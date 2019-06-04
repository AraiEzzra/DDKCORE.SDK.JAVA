package global.eska.ddk.api.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import global.eska.ddk.api.client.model.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    public Message createRequest(ActionMessageCode code, Map<String, Object> data) {
        String uuid = UUID.randomUUID().toString();
        Headers headers = new Headers(uuid, MessageType.REQUEST);
        Message request = new Message(headers, code, data);
        return request;
    }

    public JsonNode getMessageBody(Map<String, Object> messageBody) {
        ObjectNode body = objectMapper.createObjectNode();
        body.set("body", objectMapper.valueToTree(messageBody));
        return body;
    }

    public <T> T convertResponseToCorrectObject(Message response, Class<T> type){
        T obj = null;
        try {
            obj = objectMapper.treeToValue(objectMapper.valueToTree(response.getBody()).get("data"), type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
