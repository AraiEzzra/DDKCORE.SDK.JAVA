package global.eska.ddk.api.client.socket;

import com.fasterxml.jackson.databind.JsonNode;
import global.eska.ddk.api.client.model.ActionMessageCode;
import io.socket.client.Socket;

public interface DDKSocket {

    void send(ActionMessageCode code, JsonNode data);

    void clear();


}
