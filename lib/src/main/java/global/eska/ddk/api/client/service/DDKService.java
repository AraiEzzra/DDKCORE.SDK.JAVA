package global.eska.ddk.api.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

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
        Map<String, Object> map = new HashMap<>();
        map.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT, getMessageBody(map));
        Account account = null;
        Message response = socketClient.getResponse();
        System.out.println("response!!!!!!!!!! " + response);
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
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT_BALANCE, getMessageBody(rowDataForMessage));
        Long balance = null;
        Message response = socketClient.getResponse();
        System.out.println("response!!!!!!!!!! " + response);
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
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("id", id);
        socketClient.send(ActionMessageCode.GET_TRANSACTION, getMessageBody(rowDataForMessage));
        Transaction trs = null;
        Message response = socketClient.getResponse();
        System.out.println("response!!!!!!!!!! " + response);
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
    public List<Transaction> getTransactions(Filter filter, int limit, int offset, Sort... sorts) {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("filter", filter);
        rowDataForMessage.put("limit", limit);
        rowDataForMessage.put("offset", offset);

        String[][] arrSorts = new String[sorts.length][];

        for (int i = 0; i < sorts.length; i++){
            arrSorts[i] = new String[] {sorts[0].getFieldName(), sorts[0].getSortDirection().toString()};
        }

        rowDataForMessage.put("sort", arrSorts);
        socketClient.send(ActionMessageCode.GET_TRANSACTIONS, getMessageBody(rowDataForMessage));
        List<Transaction> transactions = null;
        Message response = socketClient.getResponse();
        try {
            System.out.println("response!!!!!!!!!! " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            transactions = objectMapper.readValue(response.getBody().get("data").get("transactions").toString(),
                    new TypeReference<List<Transaction>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketClient.clear();
        return transactions;
    }

    private JsonNode getMessageBody(Map<String, Object> messageBody) {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        ObjectNode body = objectMapper.createObjectNode();
        try {
            body.set("body", objectMapper.valueToTree(messageBody));
            System.out.println("DATA: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

}
