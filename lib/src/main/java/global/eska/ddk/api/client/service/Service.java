package global.eska.ddk.api.client.service;

import global.eska.ddk.api.client.exceptions.ApplicationException;
import global.eska.ddk.api.client.model.*;

import java.util.List;

public interface Service {

    Account getAccount(String address) throws ApplicationException;
    Long getAccountBalance(String address) throws ApplicationException;
    Transaction getTransaction(String id) throws ApplicationException;
    List<Transaction> getTransactions(Filter filter, int limit, int offset, Sort... sort) throws ApplicationException;
    Transaction send(Transaction transaction, String secret) throws ApplicationException;
}
