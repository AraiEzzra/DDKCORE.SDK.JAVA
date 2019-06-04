package global.eska.ddk.keygen.demo;

import global.eska.ddk.api.client.model.*;
import global.eska.ddk.api.client.service.DDKService;
import global.eska.ddk.keygen.account.AccountCreator;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import global.eska.ddk.keygen.sodium.KeyPair;
import global.eska.ddk.keygen.sodium.KeyPairCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class DemoCommandLineRunner implements CommandLineRunner {

    @Value("${greetings-phrase}")
    private String greetingsPhrase;

    private final DDKService ddkService;

    private final PassphraseGenerator passphraseGenerator;

    private final KeyPairCreator keyPairCreator;

    private final AccountCreator accountCreator;
    private final DdkSdkConfiguration ddkSdkConfiguration;

    @Autowired
    public DemoCommandLineRunner(DDKService ddkService, PassphraseGenerator passphraseGenerator,
                                 KeyPairCreator keyPairCreator, AccountCreator accountCreator,
                                 DdkSdkConfiguration ddkSdkConfiguration) {
        this.ddkService = ddkService;
        this.passphraseGenerator = passphraseGenerator;
        this.keyPairCreator = keyPairCreator;
        this.accountCreator = accountCreator;
        this.ddkSdkConfiguration = ddkSdkConfiguration;
    }

    @Override
    public void run(String... args) {
        log.info("{}", greetingsPhrase);

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

        System.out.println("ddkSdkConfiguration" + ddkSdkConfiguration.getUrl());

            getAccount();
            getAccountBalance();
            send();
            getTransaction();
            getTransactions();


    }

    private void send() {
        // tenant garage wonder sorry twin clog orange away dash kitten hospital glimpse
        // fe487d8881111193d8fdb2f4b2ee8de4177f6431496adb643721a29f9af7a4a5
        // 4334772269939713678
        String senderAddress = "4334772269939713678";
        String senderPublicKey = "fe487d8881111193d8fdb2f4b2ee8de4177f6431496adb643721a29f9af7a4a5";
        Long amount = 50000L;
        String recipientAddress = "17840830924249740129";
        String secret = "tenant garage wonder sorry twin clog orange away dash kitten hospital glimpse";

        Transaction<AssetSend> transaction = new Transaction<>();
        transaction.setType(TransactionType.SEND);
        transaction.setSenderAddress(senderAddress);
        transaction.setSenderPublicKey(senderPublicKey);
        AssetSend assetSend = new AssetSend();
        assetSend.setAmount(amount);
        assetSend.setRecipientAddress(recipientAddress);
        transaction.setAsset(assetSend);

        Transaction transactionSend = ddkService.send(transaction, secret);
        System.out.println("TRANSACTION: " + transactionSend.getId());
    }

    private void getTransactions() {
        Filter filter = new Filter(null, "cbb9449abb9672d33fa2eb200b1c8b03db7c6572dfb6e59dc334c0ab82b63ab0", null);
        Sort sort = new Sort("createdAt", SortDirection.ASC);
        List<Transaction> transactions = ddkService.getTransactions(filter, 10, 0, sort);
        System.out.println("TRANSACTIONS: " + transactions);
    }

    private void getAccount() {
        Account account = ddkService.getAccount("4995063339468361088");
        System.out.println("ACCOUNT: " + account);
    }

    private void getAccountBalance() {
        Long balance = ddkService.getAccountBalance("4995063339468361088");
        System.out.println("BALANCE: " + balance);
    }

    private void getTransaction() {
        System.out.println("TRANSACTION: " + ddkService.getTransaction("b3d2a39ae38a36560803da647275855003010a9e80d233edcf13db7c3e17ee8e"));
    }
}
