package global.eska.ddk.keygen.demo;

import global.eska.ddk.api.client.exceptions.DDKApplicationException;
import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.service.DDKClient;
import global.eska.ddk.keygen.account.AccountCreator;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import global.eska.ddk.keygen.sodium.KeyPair;
import global.eska.ddk.keygen.sodium.KeyPairCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class DemoCommandLineRunner implements CommandLineRunner {

    private final DDKClient ddkClient;

    private final PassphraseGenerator passphraseGenerator;

    private final KeyPairCreator keyPairCreator;

    private final AccountCreator accountCreator;


    private String TRANSACTION_ID = "e66d98c54c24b904bdcfdda9edc0a8b39ab21cf34101a7b37d6f1074d4ca25c7";
    private final String BLOCK_ID = "cbb9449abb9672d33fa2eb200b1c8b03db7c6572dfb6e59dc334c0ab82b63ab0";
    private final String RECIPIENT_ADDRESS = "17840830924249740129";
    private final String SENDER_PUBLIC_KEY = "fe487d8881111193d8fdb2f4b2ee8de4177f6431496adb643721a29f9af7a4a5";
    private final String SENDER_ADDRESS = "4334772269939713678";
    private final String SECRET = "tenant garage wonder sorry twin clog orange away dash kitten hospital glimpse";
    private final Long HEIGHT = 1L;
    private String newTransactionId;

    @Autowired
    public DemoCommandLineRunner(DDKClient ddkClient, PassphraseGenerator passphraseGenerator, KeyPairCreator keyPairCreator, AccountCreator accountCreator) {
        this.ddkClient = ddkClient;
        this.passphraseGenerator = passphraseGenerator;
        this.keyPairCreator = keyPairCreator;
        this.accountCreator = accountCreator;
    }

    @Override
    public void run(String... args) {
        // run methods for example
//        createKeyPair();
//        generatePassphrase();
//        createAccount();
//        getAccount();
//        getAccountBalance();
//        send();
//        getTransaction();
//        getTransactions();
//        getTransactionsByHeight();
//        getLastBlock();
//        getBlockByHeight();


    }

    private void createKeyPair(){
        KeyPair keyPair = keyPairCreator.createKeyPair(SECRET);
        System.out.println("KeyPair generated successful:");
        System.out.println("public key: " + keyPair.getPublicKey());
        System.out.println("public key hex: " + keyPair.getPublicKeyHex());
        System.out.println("secret key: " + keyPair.getSecretKey());
        System.out.println("secret key hex: " + keyPair.getSecretKeyHex());
    }

    private void generatePassphrase(){
        String passphrase = passphraseGenerator.createPassphrase();
        System.out.println("Random passphrase generated successful: " + passphrase);
    }

    private void createAccount(){
        BigDecimal address = accountCreator.getAddressByPublicKey(SENDER_PUBLIC_KEY);
        System.out.println("DDK address generated successful: " + address);
    }

    private void send() {
        Transaction<AssetSend> transaction = new Transaction<>();
        transaction.setType(TransactionType.SEND);
        transaction.setSenderAddress(SENDER_ADDRESS);
        transaction.setSenderPublicKey(SENDER_PUBLIC_KEY);
        AssetSend assetSend = new AssetSend();
        assetSend.setAmount(50000L);
        assetSend.setRecipientAddress(RECIPIENT_ADDRESS);
        transaction.setAsset(assetSend);

        Transaction transactionSend = null;
        try {
            transactionSend = ddkClient.createTransaction(transaction, SECRET);
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        newTransactionId = transactionSend.getId();
        System.out.println("NEW TRANSACTION ID: " + transactionSend.getId());
    }

    private void getTransactions() {
        Filter filter = new Filter(null, BLOCK_ID, null);
        Sort sort = new Sort("createdAt", SortDirection.ASC);
        List<Transaction> transactions = null;
        try {
            transactions = ddkClient.getTransactions(filter, 10, 0, sort);
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("TRANSACTIONS: " + transactions);
    }

    private void getTransactionsByHeight() {
        List<Transaction> transactions = null;
        try {
            transactions = ddkClient.getTransactionsByHeight(HEIGHT, 10, 0);
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("TRANSACTIONS: " + transactions);
    }

    private void getAccount() {
        Account account = null;
        try {
            account = ddkClient.getAccount(SENDER_ADDRESS);
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("ACCOUNT: " + account);
    }

    private void getAccountBalance() {
        Long balance = null;
        try {
            balance = ddkClient.getAccountBalance(SENDER_ADDRESS);
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("BALANCE: " + balance);
    }

    private void getTransaction() {
        Transaction transaction = null;
        try {
            transaction = ddkClient.getTransaction(TRANSACTION_ID);
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("TRANSACTION: " + transaction);
    }

    private void getLastBlock() {
        Block block = null;
        try {
            block = ddkClient.getLastBlock();
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("BLOCK: " + block);
    }

    private void getBlockByHeight() {
        Block block = null;
        try {
            block = ddkClient.getBlockByHeight(HEIGHT);
        } catch (DDKApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("BLOCK: " + block);
    }
}
