package global.eska.ddk.api.client.service;

import global.eska.ddk.api.client.model.*;

import java.util.List;

public interface Service {

    Account getAccount(String address);
    Long getAccountBalance(String address);
    Transaction getTransaction(String id);
    List<Transaction> getTransactions(Filter filter, int limit, int offset, Sort... sort);
    Transaction send(Transaction transaction, String secret);
}
