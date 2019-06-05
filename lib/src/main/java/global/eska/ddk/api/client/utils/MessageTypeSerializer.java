package global.eska.ddk.api.client.utils;

import com.google.gson.*;
import global.eska.ddk.api.client.model.MessageType;

import java.lang.reflect.Type;

public class MessageTypeSerializer implements JsonDeserializer<MessageType>, JsonSerializer<MessageType> {

    @Override
    public MessageType deserialize(JsonElement jsonElement,
                                   Type type,
                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        int key = jsonElement.getAsInt();
        return MessageType.fromKey(key);
    }

    @Override
    public JsonElement serialize(MessageType messageType, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(messageType.getKey());
    }
}
