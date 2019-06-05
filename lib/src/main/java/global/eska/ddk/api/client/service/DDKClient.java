package global.eska.ddk.api.client.service;

import com.google.gson.reflect.TypeToken;
import global.eska.ddk.api.client.exceptions.ApplicationException;
import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.model.socket.ResponseEntity;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.api.client.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DDKClient implements Client {

    private final SocketClient socketClient;
    private final Utils utils;

    public DDKClient(SocketClient socketClient, Utils utils) {
        this.socketClient = socketClient;
        this.utils = utils;
    }

    @Override
    public Account getAccount(String address) throws ApplicationException {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT, rowDataForMessage);
        ResponseEntity responseData = socketClient.getResponseData();
        Account account = utils.convertMapToObject(responseData, Account.class);
        socketClient.clear();
        return account;
    }

    @Override
    public Long getAccountBalance(String address) throws ApplicationException {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("address", address);
        socketClient.send(ActionMessageCode.GET_ACCOUNT_BALANCE, rowDataForMessage);
        ResponseEntity responseData = socketClient.getResponseData();
        Long balance = utils.convertMapToObject(responseData, Long.class);
        socketClient.clear();
        return balance;
    }

    @Override
    public Transaction getTransaction(String id) throws ApplicationException {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("id", id);
        socketClient.send(ActionMessageCode.GET_TRANSACTION, rowDataForMessage);
        ResponseEntity response = socketClient.getResponseData();
        Transaction trs = utils.convertMapToObject(response, Transaction.class);
        socketClient.clear();
        return trs;
    }

    @Override
    public List<Transaction> getTransactions(Filter filter, int limit, int offset, Sort... sorts) throws ApplicationException {
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
        ResponseEntity response = socketClient.getResponseData();
        List<Transaction> transactions = utils.convertMapTrsListToObj(response, new TypeToken<List<Transaction>>() {
        });
        socketClient.clear();
        return transactions;
    }

    @Override
    public Transaction createTransaction(Transaction transaction, String secret) throws ApplicationException {
        if (transaction.getType() != TransactionType.SEND) {
            throw new ApplicationException("Transaction type: " + transaction.getType() + " not supported!");
        }
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("trs", transaction);
        rowDataForMessage.put("secret", secret);
        socketClient.send(ActionMessageCode.CREATE_TRANSACTION, rowDataForMessage);
        ResponseEntity response = socketClient.getResponseData();
        return utils.convertMapToObject(response, Transaction.class);
    }

}
