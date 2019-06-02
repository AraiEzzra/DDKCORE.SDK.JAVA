package global.eska.ddk.api.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Component
public class DDKService implements Service {

    @Autowired
    private final PassphraseGenerator passphraseGenerator;
    private final SocketClient socketClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public DDKService(PassphraseGenerator passphraseGenerator,
                      SocketClient socketClient,
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
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT, getMessageBody(map));
        Account account = null;
        Message response = socketClient.getResponse();
        System.out.println("response!!!!!!!!!! " +response);
        try {
            account = objectMapper.readValue(response.getBody().get("data").toString(), Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketClient.clear();
        return account;
    }

    @Override
    public Long getAccountBalance(String address) {
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT_BALANCE, getMessageBody(map));
        Long balance = null;
        Message response = socketClient.getResponse();
        System.out.println("response!!!!!!!!!! " +response);
        try {
            balance = objectMapper.readValue(response.getBody().get("data").toString(), Long.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketClient.clear();
        return balance;
    }

    @Override
    public Transaction getTransaction(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        socketClient.send(ActionMessageCode.GET_TRANSACTION, getMessageBody(map));
        Transaction trs = null;
        Message response = socketClient.getResponse();
        System.out.println("response!!!!!!!!!! " +response);
        try {
            trs = objectMapper.readValue(response.getBody().get("data").toString(), new TypeReference<Transaction<AssetSend>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketClient.clear();
        return trs;
    }

    @Override
    public List<Transaction> getTransactions() {
        return null;
    }

    private JsonNode getMessageBody(Map<String, String> messageBody) {
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

}
