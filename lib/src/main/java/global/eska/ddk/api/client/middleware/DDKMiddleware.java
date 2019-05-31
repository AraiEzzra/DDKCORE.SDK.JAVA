package global.eska.ddk.api.client.middleware;

import global.eska.ddk.api.client.model.Message;

public interface DDKMiddleware {

    //    void send(Object body, ActionMessageCode code);
    void onMessage(Message data);

    Message getResponse();
}

