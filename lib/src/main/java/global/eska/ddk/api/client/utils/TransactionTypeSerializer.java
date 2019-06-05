package global.eska.ddk.api.client.utils;

import com.google.gson.*;
import global.eska.ddk.api.client.model.TransactionType;

import java.lang.reflect.Type;

public class TransactionTypeSerializer implements JsonDeserializer<TransactionType>, JsonSerializer<TransactionType> {

    @Override
    public TransactionType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        int key = jsonElement.getAsInt();
        return TransactionType.fromKey(key);
    }

    @Override
    public JsonElement serialize(TransactionType transactionType, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(transactionType.getKey());
    }
}
