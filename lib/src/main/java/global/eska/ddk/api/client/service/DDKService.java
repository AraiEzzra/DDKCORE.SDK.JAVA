package global.eska.ddk.api.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.api.client.utils.Utils;
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
    private final Utils utils;

    @Autowired
    public DDKService(SocketClient socketClient,
                      ObjectMapper objectMapper, Utils utils) {
        this.socketClient = socketClient;
        this.objectMapper = objectMapper;
        this.utils = utils;
    }

    @Override
    public Account getAccount(String address) {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT, rowDataForMessage);
        Message response = socketClient.getResponse();
        Account account = utils.convertResponseToCorrectObject(response, Account.class);
        System.out.println("getAccount account: " + account);
        socketClient.clear();
        return account;
    }

    @Override
    public Long getAccountBalance(String address) {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT_BALANCE, rowDataForMessage);
        Message response = socketClient.getResponse();
        Long balance = utils.convertResponseToCorrectObject(response, Long.class);
        socketClient.clear();
        return balance;
    }

    @Override
    public Transaction getTransaction(String id) {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("id", id);
        socketClient.send(ActionMessageCode.GET_TRANSACTION, rowDataForMessage);
        Message response = socketClient.getResponse();
        Transaction trs = utils.convertResponseToCorrectObject(response, Transaction.class);
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
        socketClient.send(ActionMessageCode.GET_TRANSACTIONS, rowDataForMessage);
        Message response = socketClient.getResponse();
        List<Transaction> transactions = null;
        Map<String, Object> trsMap = (Map<String,Object>)response.getBody().get("data");
        try {
            transactions = objectMapper.readValue(String.valueOf(objectMapper.valueToTree(trsMap.get("transactions"))),
                    new TypeReference<List<Transaction>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketClient.clear();
        return transactions;
    }

    @Override
    public Transaction send(Transaction transaction, String secret) {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("trs", transaction);
        rowDataForMessage.put("secret", secret);
        socketClient.send(ActionMessageCode.CREATE_TRANSACTION, rowDataForMessage);
        Message response = socketClient.getResponse();
        return utils.convertResponseToCorrectObject(response, Transaction.class);
    }

}
