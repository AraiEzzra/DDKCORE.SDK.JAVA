package global.eska.ddk.api.client.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.model.Account;
import global.eska.ddk.api.client.model.ActionMessageCode;
import global.eska.ddk.api.client.model.EventType;
import global.eska.ddk.api.client.model.Message;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Component
public class DDKService implements Service {

    // TODO make Look up method for CountDownLatch
//    @Autowired
//    private CountDownLatch doneSignal;
    @Autowired
    private CountDownLatch doneSignal;
    private final PassphraseGenerator passphraseGenerator;
    private final SocketClient socketClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public DDKService(PassphraseGenerator passphraseGenerator, SocketClient socketClient,
                      ObjectMapper objectMapper) {
        this.passphraseGenerator = passphraseGenerator;
        this.socketClient = socketClient;
        this.objectMapper = objectMapper;

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String createPassphrase() {
        return passphraseGenerator.createPassphrase();
    }

    @Override
    public void subscribe(EventType eventType) {

    }

    @Override
    public Account getAccount(String address) {
        System.out.println("doneSignal.getCount1: " + doneSignal.getCount());
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        JsonNode messageBody = prepareMessageBodyForRequest(map);
        try {
            socketClient.send(ActionMessageCode.GET_ACCOUNT, messageBody);
            System.out.println("doneSignal.countDown11: " + doneSignal.getCount());
            doneSignal.await();
            System.out.println("doneSignal.countDown22: " + doneSignal.getCount());
            System.out.println("doneSignal.countDown33: " + doneSignal.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Account account = null;
        Message response = socketClient.getResponse();
        System.out.println("GGGGGGGGGGG: " + response);
        try {
            account = objectMapper.readValue(response.getBody().get("data").toString(), Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketClient.clear();
        System.out.println("doneSignal.getCount2: " + doneSignal.getCount());
        return account;
    }

    @Override
    public Long getAccountBalance(String address) {
        System.out.println("doneSignal.getCount3: " + doneSignal.getCount());
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        JsonNode messageBody = prepareMessageBodyForRequest(map);
        socketClient.send(ActionMessageCode.GET_ACCOUNT_BALANCE, messageBody);
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long balance = null;
        Message response = socketClient.getResponse();
        System.out.println("DDDDDDD: " + response);
        try {
            balance = objectMapper.readValue(response.getBody().get("data").toString(), Long.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("FFFFFFFF");
        socketClient.clear();
        System.out.println("doneSignal.getCount4: " + doneSignal.getCount());
        return balance;
    }

    private JsonNode prepareMessageBodyForRequest(Map<String, String> messageBody) {
        JSONObject obj = new JSONObject();
        JsonNode data = null;
        try {
            obj.put("body", new JSONObject(messageBody));
            data = objectMapper.readTree(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Lookup
    public CountDownLatch getCountDownLatch() {
        return null;
    }

}
