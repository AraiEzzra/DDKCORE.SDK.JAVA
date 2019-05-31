package global.eska.ddk.api.client.service;

import global.eska.ddk.api.client.model.Account;
import global.eska.ddk.api.client.model.EventType;

public interface Service {

    String createPassphrase();
    void subscribe(EventType eventType);
    Account getAccount(String address);
    Long getAccountBalance(String address);
}
