package global.eska.ddk.api.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.socket.SocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DDKService implements Service {

    private final SocketClient socketClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public DDKService(SocketClient socketClient,
                      ObjectMapper objectMapper) {
        this.socketClient = socketClient;
        this.objectMapper = objectMapper;

    }

    @Override
    public Account getAccount(String address) {
        Map<String, Object> map = new HashMap<>();
        map.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT, getMessageBody(map));
        Account account = null;
        Message response = socketClient.getResponse();
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
        try {
            trs = objectMapper.readValue(response.getBody().get("data").toString(),
                    new TypeReference<Transaction<AssetSend>>() {
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
        for (int i = 0; i < sorts.length; i++) {
            arrSorts[i] = new String[]{sorts[0].getFieldName(), sorts[0].getSortDirection().toString()};
        }

        rowDataForMessage.put("sort", arrSorts);
        socketClient.send(ActionMessageCode.GET_TRANSACTIONS, getMessageBody(rowDataForMessage));
        List<Transaction> transactions = null;
        Message response = socketClient.getResponse();
        try {
            transactions = objectMapper.readValue(response.getBody().get("data").get("transactions").toString(),
                    new TypeReference<List<Transaction>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketClient.clear();
        return transactions;
    }

    @Override
    public Transaction send(String senderAddress, String senderPublicKey, Long amount,
                       String recipientAddress, String secret) {
        Transaction<AssetSend> transaction = new Transaction<>();
        transaction.setType(TransactionType.SEND);
        transaction.setSenderAddress(senderAddress);
        transaction.setSenderPublicKey(senderPublicKey);
        transaction.setAsset(new AssetSend());
        transaction.getAsset().setAmount(amount);
        transaction.getAsset().setRecipientAddress(recipientAddress);

        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("trs", transaction);
        rowDataForMessage.put("secret", secret);

        socketClient.send(ActionMessageCode.CREATE_TRANSACTION, getMessageBody( rowDataForMessage));

        Message response = socketClient.getResponse();
        Transaction transactionResponse = null;
        try {
            transactionResponse = objectMapper.readValue(response.getBody().get("data").toString(), Transaction.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactionResponse;
    }

    private JsonNode getMessageBody(Map<String, Object> messageBody) {

        ObjectNode body = objectMapper.createObjectNode();
        body.set("body", objectMapper.valueToTree(messageBody));
        return body;
    }

}
