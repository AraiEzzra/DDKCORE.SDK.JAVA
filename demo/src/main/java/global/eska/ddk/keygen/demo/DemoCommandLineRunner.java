package global.eska.ddk.keygen.demo;

import global.eska.ddk.api.client.exceptions.ApplicationException;
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

    @Autowired
    public DemoCommandLineRunner(DDKClient ddkClient, PassphraseGenerator passphraseGenerator,
                                 KeyPairCreator keyPairCreator, AccountCreator accountCreator) {
        this.ddkClient = ddkClient;
        this.passphraseGenerator = passphraseGenerator;
        this.keyPairCreator = keyPairCreator;
        this.accountCreator = accountCreator;
    }

    @Override
    public void run(String... args) {

        String passphrase = passphraseGenerator.createPassphrase();
        log.info("Random passphrase generated successful: {}", passphrase);

        KeyPair keyPair = keyPairCreator.createKeyPair(passphrase);

        log.info("KeyPair generated successful:");
        log.info("public key: {}", keyPair.getPublicKey());
        log.info("public key hex: {}", keyPair.getPublicKeyHex());
        log.info("secret key: {}", keyPair.getSecretKey());
        log.info("secret key hex: {}", keyPair.getSecretKeyHex());

        BigDecimal address = accountCreator.getAddressByPublicKey(keyPair.getPublicKey());

        log.info("DDK address generated successful: {}", address);

        getAccount();
        getAccountBalance();
        send();
        getTransaction();
        getTransactions();


    }

    private void send() {
        String secret = "tenant garage wonder sorry twin clog orange away dash kitten hospital glimpse";
        Transaction<AssetSend> transaction = new Transaction<>();
        transaction.setType(TransactionType.SEND);
        transaction.setSenderAddress("4334772269939713678");
        transaction.setSenderPublicKey("fe487d8881111193d8fdb2f4b2ee8de4177f6431496adb643721a29f9af7a4a5");
        AssetSend assetSend = new AssetSend();
        assetSend.setAmount(50000L);
        assetSend.setRecipientAddress("17840830924249740129");
        transaction.setAsset(assetSend);

        Transaction transactionSend = null;
        try {
            transactionSend = ddkClient.createTransaction(transaction, secret);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        log.info("NEW TRANSACTION ID: " + transactionSend.getId());
    }

    private void getTransactions() {
        Filter filter = new Filter(null, "cbb9449abb9672d33fa2eb200b1c8b03db7c6572dfb6e59dc334c0ab82b63ab0", null);
        Sort sort = new Sort("createdAt", SortDirection.ASC);
        List<Transaction> transactions = null;
        try {
            transactions = ddkClient.getTransactions(filter, 10, 0, sort);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("TRANSACTIONS: " + transactions);
    }

    private void getAccount() {
        Account account = null;
        try {
            account = ddkClient.getAccount("4334772269939713678");
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("ACCOUNT: " + account);
    }

    private void getAccountBalance() {
        Long balance = null;
        try {
            balance = ddkClient.getAccountBalance("4334772269939713678");
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        System.out.println("BALANCE: " + balance);
    }

    private void getTransaction() {
        try {
            System.out.println("TRANSACTION: " + ddkClient.getTransaction("e66d98c54c24b904bdcfdda9edc0a8b39ab21cf34101a7b37d6f1074d4ca25c7"));
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
