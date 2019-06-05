package global.eska.ddk.api.client.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import global.eska.ddk.DDKSocketClientConfiguration;
import global.eska.ddk.api.client.exceptions.ApplicationException;
import global.eska.ddk.api.client.listeners.MessageListener;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.model.socket.ResponseEntity;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.api.client.utils.MessageTypeSerializer;
import global.eska.ddk.api.client.utils.SortUtils;
import global.eska.ddk.api.client.utils.TransactionTypeSerializer;
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

    public DDKClient(String protocol, String host, int port) {
        Gson gson = createGson();
        utils = new Utils(gson);
        Blocker blocker = new Blocker();
        Middleware middleware = new Middleware(blocker, utils);
        MessageListener messageListener = new MessageListener(middleware, gson);
        socketClient = new SocketClient(middleware, messageListener, blocker, new DDKSocketClientConfiguration(protocol, host, port));
    }

    @Override
    public Account getAccount(String address) throws ApplicationException {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("address", address);
        ResponseEntity responseData = socketClient.request(ActionMessageCode.GET_ACCOUNT, rowDataForMessage);
        return utils.convertMapToObject(responseData, Account.class);
    }

    @Override
    public Long getAccountBalance(String address) throws ApplicationException {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("address", address);
        ResponseEntity responseData = socketClient.request(ActionMessageCode.GET_ACCOUNT_BALANCE, rowDataForMessage);
        return utils.convertMapToObject(responseData, Long.class);
    }

    @Override
    public Transaction getTransaction(String id) throws ApplicationException {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("id", id);
        ResponseEntity responseData = socketClient.request(ActionMessageCode.GET_TRANSACTION, rowDataForMessage);
        return utils.convertMapToObject(responseData, Transaction.class);
    }

    @Override
    public List<Transaction> getTransactions(Filter filter, int limit, int offset, Sort... sorts) throws ApplicationException {
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("filter", filter);
        rowDataForMessage.put("limit", limit);
        rowDataForMessage.put("offset", offset);
        rowDataForMessage.put("sort", new SortUtils().getSort(sorts));
        ResponseEntity responseData = socketClient.request(ActionMessageCode.GET_TRANSACTIONS, rowDataForMessage);
        return utils.convertMapTrsListToObj(responseData, new TypeToken<List<Transaction>>() {});
    }

    @Override
    public Transaction createTransaction(Transaction transaction, String secret) throws ApplicationException {
        if (transaction.getType() != TransactionType.SEND) {
            throw new ApplicationException("Transaction type: " + transaction.getType() + " not supported!");
        }
        Map<String, Object> rowDataForMessage = new HashMap<>();
        rowDataForMessage.put("trs", transaction);
        rowDataForMessage.put("secret", secret);
        ResponseEntity responseData = socketClient.request(ActionMessageCode.CREATE_TRANSACTION, rowDataForMessage);
        return utils.convertMapToObject(responseData, Transaction.class);
    }

    private Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageType.class, new MessageTypeSerializer());
        gsonBuilder.registerTypeAdapter(TransactionType.class, new TransactionTypeSerializer());
        Gson gson = gsonBuilder.create();
        return gson;
    }

}
