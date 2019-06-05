package global.eska.ddk.api.client.socket;

import global.eska.ddk.api.client.model.ActionMessageCode;

import java.util.Map;

public interface DDKSocket {

    void send(ActionMessageCode code, Map<String, Object> data);

    void clear();


}
