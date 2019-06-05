package global.eska.ddk.api.client.service;

import global.eska.ddk.api.client.exceptions.DDKApplicationException;
import global.eska.ddk.api.client.model.*;

import java.util.List;

public interface Client {

    Account getAccount(String address) throws DDKApplicationException;
    Long getAccountBalance(String address) throws DDKApplicationException;
    Transaction getTransaction(String id) throws DDKApplicationException;
    List<Transaction> getTransactions(Filter filter, int limit, int offset, Sort... sort) throws DDKApplicationException;
    Transaction createTransaction(Transaction transaction, String secret) throws DDKApplicationException;
}
